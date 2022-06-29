package com.douglas.SAMC.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.douglas.SAMC.DTO.AlunoDTO;
import com.douglas.SAMC.DTO.AlunoFORM;
import com.douglas.SAMC.DTO.ImageFORM;
import com.douglas.SAMC.enums.AlunoStatus;
import com.douglas.SAMC.enums.EntradaSaida;
import com.douglas.SAMC.enums.Periodo;
import com.douglas.SAMC.model.Alarm;
import com.douglas.SAMC.model.Aluno;
import com.douglas.SAMC.model.Turma;
import com.douglas.SAMC.repository.AlunoRepository;
import com.douglas.SAMC.service.Exception.DataIntegratyViolationException;
import com.douglas.SAMC.service.Exception.ObjectNotEmptyException;
import com.douglas.SAMC.service.Exception.ObjectNotFoundException;
import com.douglas.SAMC.utils.Base64DecodeToMultiPartFile;

@Service
public class AlunoService {

	@Autowired
	private AlunoRepository repository;

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private TurmaService turmaService;

	@Autowired
	private UploadingService uploadingService;

	@Autowired
	private AlarmService alarmService;

	@Value("${alunoPhotoLocation}")
	private String alunoPhotoLocation;

	@Value("${aws.s3BucketAlunos}")
	private String awsS3Bucket;
	
	@Value("${file.storage}")
	private String fileStorage;

	public Aluno create(AlunoFORM alunoFORM) {
		if (repository.findByMatricula(alunoFORM.getMatricula()).isPresent()) {
			throw new DataIntegratyViolationException("Matrícula já cadastrada na base de dados!");
		}
		if (alunoFORM.getTag() != null) {
			if (repository.findByTag(alunoFORM.getTag()).isPresent()) {
				throw new DataIntegratyViolationException(
						"Tag " + alunoFORM.getTag() + " já cadastrada na base de dados!");
			}
		}

		Aluno aluno = fromFORM(alunoFORM);
		return repository.save(aluno);
	}

	public List<Aluno> createAll(List<AlunoFORM> alunosFORM) {
		List<Aluno> alunos = new ArrayList<>();
		alunosFORM.forEach(alunoFORM -> {
			try {
				create(alunoFORM);
				alunos.add(fromFORM(alunoFORM));
			} catch (Exception e) {
				System.out.println("Aluno " + alunoFORM.getNome() + " já existe na base de dados");
			}
		});
		return alunos;

	}

	public Aluno save(Aluno aluno) {
		return repository.save(aluno);
	}

	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
	}

	public Aluno update(Integer id, @Valid AlunoFORM alunoFORM) {
		Aluno aluno = findById(id);
		aluno.setNome(alunoFORM.getNome());
		aluno.setSexo(alunoFORM.getSexo());
		aluno.setRg(alunoFORM.getRg());
		aluno.setEmail(alunoFORM.getEmail());
		aluno.setTelefone(alunoFORM.getTelefone());
		aluno.setCidade(alunoFORM.getCidade());
		aluno.setDataNascimento(alunoFORM.getDataNascimento().toString());
		aluno.setTag(alunoFORM.getTag());
		aluno.setMatricula(alunoFORM.getMatricula());
		if (!alunoFORM.getTurma().equals("EGRESSO") && !alunoFORM.getTurma().equals("EVADIDO")) {
			Turma turma = turmaService.findByCodigo(alunoFORM.getTurma());
			aluno.setTurma(turma);
		}
		aluno.setNumeroTurma(alunoFORM.getNumeroTurma());
		if (alunoFORM.getTurma().equals("EGRESSO")) {
			aluno.setStatus(AlunoStatus.EGRESSO);
		} else if (alunoFORM.getTurma().equals("EVADIDO")) {
			aluno.setStatus(AlunoStatus.EVADIDO);
		} else {
			aluno.setStatus(AlunoStatus.ATIVO);
		}
		aluno.setDataMatricula(alunoFORM.getDataMatricula().toString());
		aluno.setEmpresa(alunoFORM.getEmpresa());
		aluno.setTermoInternet(alunoFORM.isTermoInternet());
		aluno.setInternetLiberada(alunoFORM.isInternetLiberada());
		return repository.save(aluno);
	}

	public Aluno updateTag(Integer id, Integer tag) {
		Aluno aluno = findById(id);
		if (tag == 0) {
			aluno.setTag(null);
		} else {
			aluno.setTag(tag);
		}
		return repository.save(aluno);
	}

	public AlunoDTO updateEntradaSaida(Integer id, EntradaSaida entradaSaida) {
		Aluno aluno = findById(id);
		aluno.setEntradaSaida(entradaSaida);
		AlunoDTO alunoDTO = new AlunoDTO(aluno);
		repository.save(aluno);
		return alunoDTO;
	}

	public AlunoDTO updateDesbloqueioTemporario(Integer id, boolean desbloqueioTemporario) {
		Optional<Aluno> aluno = repository.findById(id);
		aluno.get().setDesbloqueioTemporario(desbloqueioTemporario);
		AlunoDTO alunoDTO = new AlunoDTO(aluno.get());
		repository.save(aluno.get());
		return alunoDTO;
	}

	public void updateEntradaSaida(Aluno aluno) {
		switch (aluno.getEntradaSaida()) {
		case ENTRADA:

			if (aluno.getTurma().getPeriodo() != Periodo.INTEGRAL) {
				aluno.setEntradaSaida(EntradaSaida.SAIDA);
				break;
			}
			aluno.setEntradaSaida(EntradaSaida.ALMOCO_SAIDA);
			break;

		case SAIDA:

			aluno.setEntradaSaida(EntradaSaida.ENTRADA);
			break;

		case ALMOCO_ENTRADA:

			aluno.setEntradaSaida(EntradaSaida.SAIDA);
			break;

		case ALMOCO_SAIDA:

			aluno.setEntradaSaida(EntradaSaida.ALMOCO_ENTRADA);
			break;

		case LIVRE_ACESSO:

			break;

		case INEXISTENTE:
			break;

		}
	}

	public Aluno updateStatus(Integer id, AlunoStatus status) {
		Aluno aluno = findById(id);
		if (status == AlunoStatus.EGRESSO) {
			Turma turma = turmaService.findByCodigoAndCursoid("EGRESSO", aluno.getTurma().getCurso().getId());
			aluno.setTurma(turma);

		} else if (status == AlunoStatus.EVADIDO) {
			Turma turma = turmaService.findByCodigoAndCursoid("EVADIDO", aluno.getTurma().getCurso().getId());
			aluno.setTurma(turma);
		}
		aluno.setStatus(status);
		aluno.setTag(null);

		return repository.save(aluno);
	}

	public AlunoDTO updatetermoInternet(Integer id, boolean termoInternet) {
		Aluno aluno = findById(id);
		aluno.setTermoInternet(termoInternet);
		repository.save(aluno);
		return new AlunoDTO(aluno);
	}

	public AlunoDTO updateInternetLiberada(Integer id, boolean internetLiberada) {
		Aluno aluno = findById(id);
		aluno.setInternetLiberada(internetLiberada);
		repository.save(aluno);
		return new AlunoDTO(aluno);
	}

	public List<AlunoDTO> updateAllInternetLiberada(List<Aluno> alunos, boolean internetLiberada) {
		alunos.forEach(aluno -> {
			aluno.setInternetLiberada(internetLiberada);
		});
		repository.saveAll(alunos);
		return toDTO(alunos);
	}

	public void updateFaltasConsecutivas(Aluno aluno) {
		if (aluno.getFaltasConsecutivas() == null) {
			aluno.setFaltasConsecutivas(0);
		}
		aluno.setFaltasConsecutivas(aluno.getFaltasConsecutivas() + 1);
		if (aluno.getFaltasConsecutivas() >= 3) {
			Alarm alarm = new Alarm(LocalDate.now(), aluno,
					"Aluno com " + aluno.getFaltasConsecutivas() + " faltas consecutivas", true);
			alarmService.create(alarm);
		}
		repository.save(aluno);

	}

	public List<AlunoDTO> findAll() {
		List<Aluno> alunos = (List<Aluno>) repository.findAll();
		return toDTO(alunos);
	}

	public Aluno findById(Integer id) {
		Optional<Aluno> aluno = repository.findById(id);
		return aluno.orElseThrow(() -> new ObjectNotFoundException("Aluno com id" + id + " não encontrado!"));
	}

	public AlunoDTO findByIdDTO(Integer id) {
		Optional<Aluno> aluno = repository.findById(id);
		if (aluno.isEmpty()) {
			throw new ObjectNotFoundException("Aluno com id" + id + " não encontrado!");
		}
		AlunoDTO alunoDTO = new AlunoDTO(aluno.get());
		
		if(fileStorage.equals("s3")) {
			alunoDTO.setFoto(getImageS3(aluno.get()));
		}else {
			alunoDTO.setFoto(getImage(aluno.get()));
		}

		return alunoDTO;
	}

	public AlunoDTO findByTag(Integer tag) {
		Optional<Aluno> aluno = repository.findByTag(tag);
		if (aluno.isPresent()) {
			AlunoDTO alunoDTO = new AlunoDTO(aluno.get());
			return alunoDTO;
		}
		throw new ObjectNotFoundException("Aluno com tag " + tag + " não encontrado!");
	}

	public Aluno findByMatricula(Integer matricula) {
		Optional<Aluno> aluno = repository.findByMatricula(matricula);
		return aluno.orElseThrow(
				() -> new ObjectNotFoundException("Aluno com matricula " + matricula + " não encontrado!"));
	}

	public List<Aluno> findAllByNomeContaining(String nome) {
		return (List<Aluno>) repository.findAllByNomeContaining(nome);
	}

	public List<Aluno> findByTurma(String codigo) {
		Turma turma = turmaService.findByCodigo(codigo);
		return (List<Aluno>) repository.findAllByTurmaOrderByNome(turma);
	}

	public List<AlunoDTO> findByTurmaDTO(@Valid Integer id) {
		Turma turma = turmaService.findById(id);
		List<Aluno> alunos = repository.findAllByTurmaOrderByNome(turma);
		return toDTO(alunos);
	}

	public List<AlunoDTO> findByTurmaAndStatus(String codigo, AlunoStatus status) {
		Turma turma = turmaService.findByCodigo(codigo);
		List<Aluno> alunos = repository.findByTurmaAndStatusOrderByNome(turma, status);
		return toDTO(alunos);
	}

	public List<AlunoDTO> findAllByStatus(AlunoStatus status) {
		List<Aluno> alunos = repository.findAllByStatusOrderByNome(status);
		return toDTO(alunos);
	}

	public List<AlunoDTO> findAllByStatusLazy(AlunoStatus status) {
		List<Aluno> alunos = repository.findAllByStatusOrderByNome(status);
		return toDTOLAzy(alunos);
	}

	public List<Aluno> findAllByDesbloqueioTemporario(boolean resolvido) {
		return (List<Aluno>) repository.findAllByDesbloqueioTemporarioOrderByNome(resolvido);
	}

	public List<Aluno> findAllByEntradaSaida(EntradaSaida entradaSaida) {
		return (List<Aluno>) repository.findAllByEntradaSaidaOrderByNome(entradaSaida);
	}

	public List<AlunoDTO> findByCurso(Integer curso_id) {
		List<Aluno> alunos = repository.findAllByTurmaCursoIdOrderByNome(curso_id);
		return toDTO(alunos);
	}

	public List<AlunoDTO> findByCursoAndStatusOrderByNome(Integer curso_id, AlunoStatus status) {
		List<Aluno> alunos = repository.findAllByTurmaCursoIdAndStatusOrderByNome(curso_id, status);
		return toDTO(alunos);
	}

	private Aluno fromFORM(AlunoFORM alunoFORM) {
		Turma turma = turmaService.findByCodigo(alunoFORM.getTurma());
		Aluno aluno = new Aluno(alunoFORM.getNome(), alunoFORM.getSexo(), alunoFORM.getRg(), alunoFORM.getEmail(),
				alunoFORM.getTelefone(), alunoFORM.getCidade(), alunoFORM.getDataNascimento().toString(),
				alunoFORM.getTag(), alunoFORM.getMatricula(), turma, alunoFORM.getNumeroTurma(),
				alunoFORM.getDataMatricula().toString(), alunoFORM.getEmpresa());
		aluno.setStatus(AlunoStatus.ATIVO);

		return aluno;
	}

	private List<AlunoDTO> toDTO(List<Aluno> alunos) {
		List<AlunoDTO> alunosDTO = new ArrayList<>();
		alunos.forEach(aluno -> {
			AlunoDTO alunoDTO = new AlunoDTO(aluno);
			if(fileStorage.equals("s3")) {
				alunoDTO.setFoto(getImageS3(aluno));
			}else {
				alunoDTO.setFoto(getImage(aluno));
			}
			alunosDTO.add(alunoDTO);
		});
		return alunosDTO;
	}

	private List<AlunoDTO> toDTOLAzy(List<Aluno> alunos) {
		List<AlunoDTO> alunosDTO = new ArrayList<>();
		alunos.forEach(aluno -> {
			AlunoDTO alunoDTO = new AlunoDTO(aluno);
			alunosDTO.add(alunoDTO);
		});
		return alunosDTO;
	}

	public List<Aluno> move(Integer turmaAtual_id, Integer turmaDestino_id) {
		Turma turma = turmaService.findById(turmaDestino_id);
		if (turma.getCodigo().equals("EGRESSO")) {
			List<Aluno> alunos = repository.findAllByTurmaIdOrderByNome(turmaAtual_id);
			alunos.forEach(aluno -> aluno.setTurma(turma));
			repository.saveAll(alunos);
			return alunos;
		}
		List<Aluno> alunosTurmaDestino = repository.findAllByTurmaOrderByNome(turma);
		if (alunosTurmaDestino.size() > 0) {
			throw new ObjectNotEmptyException("A turma " + turma.getCodigo() + " contém alunos!");
		}

		List<Aluno> alunos = repository.findAllByTurmaIdOrderByNome(turmaAtual_id);

		alunos.forEach(aluno -> aluno.setTurma(turma));
		repository.saveAll(alunos);
		return alunos;
	}

	@SuppressWarnings("resource")
	private String getImage(Aluno aluno) {
		String imageName = aluno.getMatricula().toString() + ".JPG";
		try {
			File file = new File(this.alunoPhotoLocation + imageName);
			FileInputStream fileInputStream;
			try {
				fileInputStream = new FileInputStream(file);
				byte imageData[] = new byte[(int) file.length()];
				fileInputStream.read(imageData);
				String imageBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageData);
				return imageBase64;
			} catch (IOException e) {
				return null;
			}
		} catch (Exception e) {
			return null;
		}

	}

	private String getImageS3(Aluno aluno) {
		String imageName = aluno.getMatricula().toString() + ".JPG";
		try {
			S3Object object = amazonS3.getObject(this.awsS3Bucket, imageName);
			try {

				byte imageData[] = IOUtils.toByteArray(object.getObjectContent());
				String imageBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageData);
				return imageBase64;
			} catch (IOException e) {
				System.out.println(e.getMessage());
				return null;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	public void saveImage(Integer id, ImageFORM imageFORM) {
		Aluno aluno = findById(id);

		try {
			MultipartFile file = new Base64DecodeToMultiPartFile(imageFORM.getBase64Image());
			if(fileStorage.equals("s3")) {
				uploadingService.uploadFileS3(file, awsS3Bucket , aluno.getMatricula() + ".JPG");
			}else {
				uploadingService.uploadFile(file, "Alunos", aluno.getMatricula() + ".JPG");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
