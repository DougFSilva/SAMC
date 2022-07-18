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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.douglas.SAMC.DTO.FuncionarioDTO;
import com.douglas.SAMC.DTO.ImageFORM;
import com.douglas.SAMC.enums.EntradaSaida;
import com.douglas.SAMC.model.Funcionario;
import com.douglas.SAMC.repository.AlunoRepository;
import com.douglas.SAMC.repository.FuncionarioRepository;
import com.douglas.SAMC.service.Exception.DataIntegratyViolationException;
import com.douglas.SAMC.service.Exception.ObjectNotFoundException;
import com.douglas.SAMC.utils.Base64DecodeToMultiPartFile;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository repository;

	@Autowired
	AlunoRepository alunoRepository;
	
	@Autowired
	private AmazonS3 amazonS3;
	
	@Autowired
	private UploadingService uploadingService;


	@Value("${funcionarioPhotoLocation}")
	private String funcionarioPhotoLocation;
	
	@Value("${aws.s3BucketFuncionarios}")
	private String awsS3Bucket;
	
	@Value("${file.storage}")
	private String fileStorage;

	public Funcionario create(Funcionario funcionario) {
		if (repository.findByMatricula(funcionario.getMatricula()).isPresent()) {
			throw new DataIntegratyViolationException(
					"Matrícula " + funcionario.getMatricula() + " já existe na base de dados!");
		}
		;
		if (funcionario.getTag() != null) {
			if (alunoRepository.findByTag(funcionario.getTag()).isPresent()) {
				throw new DataIntegratyViolationException(
						"Tag " + funcionario.getTag() + " já existe na base de dados!");
			}
			;
		}

		return repository.save(funcionario);
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	public Funcionario update(Integer id, Funcionario funcionario) {
		Funcionario OldFuncionario = findById(id);
		OldFuncionario.setNome(funcionario.getNome());
		OldFuncionario.setSexo(funcionario.getSexo());
		OldFuncionario.setRg(funcionario.getRg());
		OldFuncionario.setEmail(funcionario.getEmail());
		OldFuncionario.setTelefone(funcionario.getTelefone());
		OldFuncionario.setCidade(funcionario.getCidade());
		OldFuncionario.setDataNascimento(funcionario.getDataNascimento().toString());
		OldFuncionario.setTag(funcionario.getTag());
		OldFuncionario.setMatricula(funcionario.getMatricula());
		OldFuncionario.setEmpresa(funcionario.getEmpresa());
		return repository.save(OldFuncionario);

	}

	public List<FuncionarioDTO> FindAll(Pageable paginacao) {
		
		Page<Funcionario> funcionarios = (Page<Funcionario>) repository.findAll(paginacao);
		List<FuncionarioDTO> funcionariosDTO = new ArrayList<>();
		funcionarios.forEach(funcionario -> {
			FuncionarioDTO funcionarioDTO = new FuncionarioDTO(funcionario);
			if(fileStorage.equals("s3")) {
				funcionarioDTO.setFoto(getImageS3(funcionario));
			}else {
				funcionarioDTO.setFoto(getImage(funcionario));
			}
			funcionariosDTO.add(funcionarioDTO);
		});

		return funcionariosDTO;
	}

	public Funcionario findById(Integer id) {
		Optional<Funcionario> funcionario = repository.findById(id);
		return funcionario.orElseThrow(
				() -> new ObjectNotFoundException("Funcionário com id " + id + "não encontrado na base de dados"));
	}

	public FuncionarioDTO findByIdDTO(Integer id) {
		Optional<Funcionario> funcionario = repository.findById(id);
		if (funcionario.isPresent()) {
			FuncionarioDTO funcionarioDTO = new FuncionarioDTO(funcionario.get());
			if(fileStorage.equals("s3")) {
				funcionarioDTO.setFoto(getImageS3(funcionario.get()));
			}else {
				funcionarioDTO.setFoto(getImage(funcionario.get()));
			}
			return funcionarioDTO;
		}

		throw new ObjectNotFoundException("Funcionário com id " + id + "não encontrado na base de dados");
	}

	public Funcionario findByTag(Integer tag) {
		Optional<Funcionario> funcionario = repository.findByTag(tag);
		return funcionario.orElseThrow(
				() -> new ObjectNotFoundException("Funcionário com tag " + tag + "não encontrado na base de dados!"));
	}

	public Funcionario findByMatricula(Integer matricula) {
		Optional<Funcionario> funcionario = repository.findByMatricula(matricula);
		return funcionario.orElseThrow(() -> new DataIntegratyViolationException(
				"O funcionário com matricula " + matricula + " já existe na base de dados!"));
	}

	public List<Funcionario> findAllByNomeContaining(String nome) {
		return (List<Funcionario>) repository.findAllByNomeContaining(nome);
	}

	public void updateEntradaSaida(Funcionario funcionario) {
		if (funcionario.getEntradaSaida() != EntradaSaida.LIVRE_ACESSO) {
			switch (funcionario.getEntradaSaida()) {
			case ENTRADA:

				funcionario.setEntradaSaida(EntradaSaida.ALMOCO_SAIDA);
				break;

			case SAIDA:

				funcionario.setEntradaSaida(EntradaSaida.ENTRADA);
				break;

			case ALMOCO_ENTRADA:

				funcionario.setEntradaSaida(EntradaSaida.SAIDA);
				break;

			case ALMOCO_SAIDA:

				funcionario.setEntradaSaida(EntradaSaida.ALMOCO_ENTRADA);
				break;

			case LIVRE_ACESSO:

				break;

			case INEXISTENTE:
				break;

			}
		}
		return;
	}

	private String getImage(Funcionario funcionario) {
		String imageName = funcionario.getMatricula().toString() + ".JPG";
		try {
			File file = new File(this.funcionarioPhotoLocation + imageName);
			FileInputStream fileInputStream;
			try {
				fileInputStream = new FileInputStream(file);
				byte imageData[] = new byte[(int) file.length()];
				fileInputStream.read(imageData);
				String imageBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageData);
				fileInputStream.close();
				return imageBase64;
			} catch (IOException e) {
				return null;
			}
		} catch (Exception e) {
			return null;
		}

	}
	
	private String getImageS3(Funcionario funcionario) {
		String imageName = funcionario.getMatricula().toString() + ".JPG";
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
		Funcionario funcionario = findById(id);

		try {
			MultipartFile file = new Base64DecodeToMultiPartFile(imageFORM.getBase64Image());
			if(fileStorage.equals("s3")) {
				uploadingService.uploadFileS3(file, awsS3Bucket , funcionario.getMatricula() + ".JPG");
			}else {
				uploadingService.uploadFile(file, "Funcionarios", funcionario.getMatricula() + ".JPG");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
