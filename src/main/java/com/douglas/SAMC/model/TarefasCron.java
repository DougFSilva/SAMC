package com.douglas.SAMC.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.douglas.SAMC.DTO.OcorrenciaFORM;
import com.douglas.SAMC.enums.EntradaSaida;
import com.douglas.SAMC.enums.TipoOcorrencia;
import com.douglas.SAMC.repository.AlunoRepository;
import com.douglas.SAMC.service.AlarmService;
import com.douglas.SAMC.service.AlunoService;
import com.douglas.SAMC.service.OcorrenciaService;
import com.douglas.SAMC.service.PontoAlunoService;
import com.douglas.SAMC.service.TurmaService;

@Component
public class TarefasCron {

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private AlunoRepository alunoRepository;

	@Autowired
	private TurmaService turmaService;

	@Autowired
	private PontoAlunoService pontoAlunoService;

	@Autowired
	private OcorrenciaService ocorrenciaService;

	@Autowired
	private AlarmService alarmService;

	private Float faltas;

	private Float aulas;

	private boolean contain;

	@Scheduled(cron = "0 59 23 * * *")
	public void uptadeBloqueio() {
		List<Aluno> alunos = alunoService.findAllByDesbloqueioTemporario(true);
		alunos.forEach(aluno -> {
			aluno.setDesbloqueioTemporario(false);
			alunoRepository.save(aluno);

		});
	}

	@Scheduled(cron = "15 59 23 * * *")
	public void updateEntradaSaida() {
		List<Aluno> alunos = alunoService.findAllByEntradaSaida(EntradaSaida.SAIDA);
		alunos.addAll((List<Aluno>) alunoService.findAllByEntradaSaida(EntradaSaida.ALMOCO_ENTRADA));
		alunos.addAll((List<Aluno>) alunoService.findAllByEntradaSaida(EntradaSaida.ALMOCO_SAIDA));
		alunos.forEach(aluno -> {
			aluno.setEntradaSaida(EntradaSaida.ENTRADA);
			alunoRepository.save(aluno);

		});
	}

	@Scheduled(cron = "0 50 8 * * *")
	public void updateFaltas() {
		Pageable paginacao = PageRequest.of(0, 100000);
		List<Aluno> alunosEmAula = new ArrayList<>();
		List<Aluno> alunosPresentes = new ArrayList<>();

		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime localDateTimeAfter = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(),
				localDateTime.getDayOfMonth(), 0, 0, 0);
		LocalDateTime localDateTimeBefore = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(),
				localDateTime.getDayOfMonth(), 23, 59, 59);

		List<Turma> turmas = turmaService.findAllByAulasDiaAula(localDateTime.toLocalDate());

		turmas.forEach(turma -> {
			List<Aluno> alunos = alunoService.findByTurma(turma.getCodigo(), paginacao);
			alunosEmAula.addAll(alunos);
		});

		List<PontoAluno> pontoAlunos = pontoAlunoService.findByTimestampBetween(localDateTimeAfter,
				localDateTimeBefore);

		pontoAlunos.forEach(ponto -> {
			alunosPresentes.add(ponto.getAluno());
		});

		alunosEmAula.removeAll(alunosPresentes);

		alunosEmAula.forEach(aluno -> {
			OcorrenciaFORM ocorrencia = new OcorrenciaFORM(false, TipoOcorrencia.FALTA, "Falta no dia", true);
			ocorrencia.setRegistrador("sistema");
			ocorrenciaService.create(aluno.getId(), ocorrencia);
			alunoService.updateFaltasConsecutivas(aluno);

		});

	}

	@Scheduled(cron = "0 51 8 * * *")
	public void alarmFaltas() {
		Pageable paginacao = PageRequest.of(0, 100000);
		List<Turma> turmasAtivas = new ArrayList<>();
		List<Turma> turmas = turmaService.findAll();
		turmas.forEach(turma -> {
			if (!turma.getCodigo().equals("EGRESSO") && !turma.getCodigo().equals("EVADIDO")) {
				turmasAtivas.add(turma);
			}
		});
		turmasAtivas.forEach(turma -> {
			List<Aluno> alunos = alunoService.findByTurma(turma.getCodigo(), paginacao);
			if (alunos.size() > 0) {
				this.aulas = (float) turma.getAulas().size();
				alunos.forEach(aluno -> {
					this.faltas = (float) 0;
					List<Ocorrencia> ocorrencias = ocorrenciaService.findAllByAlunoIdSystem(aluno.getId());
					ocorrencias.forEach(ocorrencia -> {
						if (ocorrencia.getTipo() == TipoOcorrencia.FALTA) {
							this.faltas = this.faltas + 1;
						}
					});

					if (this.faltas > 0 && this.aulas > 0) {
						float porcentagemFaltas = (this.faltas / this.aulas) * 100;
						if (porcentagemFaltas > 15) {
							List<Alarm> alarmes = alarmService.findAllByAluno(aluno);
							this.contain = false;
							alarmes.forEach(alarme -> {
								if (alarme.getDescricao().contains((int) porcentagemFaltas + "%")) {
									;
									this.contain = true;
								}
							});
							if (this.contain == false) {
								Alarm alarme = new Alarm(LocalDate.now(), aluno,
										"Aluno tem " + (int) porcentagemFaltas + "% de faltas", true);
								alarmService.create(alarme);
							}

						}
					}
				});
			}

		});

	}

}
