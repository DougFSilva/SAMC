package com.douglas.SAMC.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.douglas.SAMC.DTO.FuncionarioDTO;
import com.douglas.SAMC.DTO.ImageFORM;
import com.douglas.SAMC.enums.EntradaSaida;
import com.douglas.SAMC.model.Funcionario;
import com.douglas.SAMC.repository.AlunoRepository;
import com.douglas.SAMC.repository.FuncionarioRepository;
import com.douglas.SAMC.service.Exception.DataIntegratyViolationException;
import com.douglas.SAMC.service.Exception.ObjectNotFoundException;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository repository;

	@Autowired
	AlunoRepository alunoRepository;

	@Value("${funcionarioPhotoLocation}")
	private String funcionarioPhotoLocation;

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

	public List<FuncionarioDTO> FindAll() {
		List<Funcionario> funcionarios = (List<Funcionario>) repository.findAll();
		List<FuncionarioDTO> funcionariosDTO = new ArrayList<>();
		funcionarios.forEach(funcionario -> {
			FuncionarioDTO funcionarioDTO = new FuncionarioDTO(funcionario);
			funcionarioDTO.setFoto(getImageBase64(funcionario));
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
			funcionarioDTO.setFoto(getImageBase64(funcionario.get()));
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

	@SuppressWarnings("resource")
	private String getImageBase64(Funcionario funcionario) {
		String imageName = funcionario.getMatricula().toString() + ".JPG";
		try {
			File file = new File(this.funcionarioPhotoLocation + imageName);
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

	public void saveImage(Integer id, ImageFORM imageFORM) {
		BufferedImage image = null;
		Funcionario funcionario = findById(id);
		String base64Image = imageFORM.getBase64Image().split(",")[1];
		byte[] imageByte;

		try {
			imageByte = Base64.getDecoder().decode(base64Image);
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);
			bis.close();
			String imageName = funcionario.getMatricula() + ".JPG";

			ImageIO.write(image, "png", new File(this.funcionarioPhotoLocation + imageName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;

	}

}
