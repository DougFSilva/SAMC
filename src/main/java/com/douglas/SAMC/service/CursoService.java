package com.douglas.SAMC.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.douglas.SAMC.DTO.CursoDTO;
import com.douglas.SAMC.enums.Periodo;
import com.douglas.SAMC.model.Curso;
import com.douglas.SAMC.model.Turma;
import com.douglas.SAMC.repository.CursoRepository;
import com.douglas.SAMC.repository.TurmaRepository;
import com.douglas.SAMC.service.Exception.ObjectNotFoundException;

@Service
public class CursoService {

	@Autowired
	private CursoRepository repository;

	@Autowired
	private TurmaRepository turmaRepository;
	
	@Autowired
	private AmazonS3 amazonS3;

	@Value("${cursoPhotoLocation}")
	private String cursoPhotoLocation;
	
	@Value("${aws.s3BucketCursos}")
	private String awsS3Bucket;
	
	@Value("${aws.region}")
	private String awsRegion;
	
	@Value("${file.storage}")
	private String fileStorage;

	public Curso create(Curso curso) {
		Curso newCurso = repository.save(curso);
		Turma formando = new Turma(newCurso, "FORMANDO", null, null, null, null, 0, 0, Periodo.FORMANDO);
		Turma evadido = new Turma(newCurso, "EVADIDO", null, null, null, null, 0, 0, Periodo.EVADIDO);
		turmaRepository.save(formando);
		turmaRepository.save(evadido);
		return newCurso;
	}

	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
	}

	public Curso update(Integer id, Curso curso) {
		Curso oldCurso = findById(id);
		oldCurso.setModalidade(curso.getModalidade());
		oldCurso.setAreaTecnologica(curso.getAreaTecnologica());
		oldCurso.setTurma(curso.getTurma());
		return repository.save(oldCurso);

	}

	public List<CursoDTO> findAll() {
		List<Curso> cursos = (List<Curso>) repository.findAll();
		return toListDTO(cursos);
	}

	public Curso findById(Integer id) {
		Optional<Curso> curso = repository.findById(id);
		return curso.orElseThrow(
				() -> new ObjectNotFoundException("Curso com id " + id + " não encontrado na base de dados!"));
	}

	public CursoDTO findByIdDTO(Integer id) {
		Optional<Curso> curso = repository.findById(id);
		if (curso.isPresent()) {
			return toDTO(curso.get());
		}
		throw new ObjectNotFoundException("Curso com id " + id + " não encontrado na base de dados!");
	}

	public Curso findByTurmaId(Integer id) {
		Optional<Curso> curso = repository.findByTurmaIdOrderByModalidade(id);
		return curso.orElseThrow(
				() -> new ObjectNotFoundException("Curso com id " + id + " não encontrado na base de dados!"));

	}

	public Curso findByTurmaCodigo(String codigo) {
		Optional<Curso> curso = repository.findByTurmaCodigo(codigo);
		return curso.orElseThrow(
				() -> new ObjectNotFoundException("Curso com codigo " + codigo + " não encontrado na base de dados!"));

	}

	public Curso findByIdAndTurmaCodigo(Integer id, String codigo) {
		Optional<Curso> curso = repository.findByIdAndTurmaCodigo(id, codigo);
		return curso.orElseThrow(() -> new ObjectNotFoundException("Curso não encontrado na base de dados!"));
	}

	private CursoDTO toDTO(Curso curso) {
		CursoDTO cursoDTO = new CursoDTO(curso);
		if(fileStorage.equals("s3")) {
			cursoDTO.setImagem(getImageS3(curso));
		}else {
			cursoDTO.setImagem(getImage(curso));
		}
		
		return cursoDTO;
	}

	private List<CursoDTO> toListDTO(List<Curso> cursos) {
		List<CursoDTO> cursosDTO = new ArrayList<>();
		cursos.forEach(curso -> {
			CursoDTO cursoDTO = new CursoDTO(curso);
			if(fileStorage.equals("s3")) {
				cursoDTO.setImagem(getImageS3(curso));
			}else {
				cursoDTO.setImagem(getImage(curso));
			}
			
			cursosDTO.add(cursoDTO);
		});
		return cursosDTO;
	}

	@SuppressWarnings("resource")
	private String getImage(Curso curso) {
		String imageName = curso.getId().toString() + ".JPG";
		try {
			File file = new File(this.cursoPhotoLocation + imageName);
			FileInputStream fileInputStream;
			try {
				fileInputStream = new FileInputStream(file);
				byte imageData[] = new byte[(int) file.length()];
				fileInputStream.read(imageData);
				String imageBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageData);
				return imageBase64;
			} catch (Exception e) {
				return null;
			}
		} catch (Exception e) {
			return null;
		}

	}
	
	private String getImageS3(Curso curso) {
		String imageName = curso.getId().toString() + ".JPG";
		
		try {
			S3Object object = amazonS3.getObject(this.awsS3Bucket, imageName);
			try {
				
				byte imageData[] = IOUtils.toByteArray(object.getObjectContent());;
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

}
