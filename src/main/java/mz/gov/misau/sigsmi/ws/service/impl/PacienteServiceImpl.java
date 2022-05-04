package mz.gov.misau.sigsmi.ws.service.impl;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mz.gov.misau.sigsmi.ws.exception.resource.DenidOperationAndInvalidResourceException;
import mz.gov.misau.sigsmi.ws.exception.resource.PacienteNotFoundException;
import mz.gov.misau.sigsmi.ws.io.model.entity.ContactoEntity;
import mz.gov.misau.sigsmi.ws.io.model.entity.EnderecoEntity;
import mz.gov.misau.sigsmi.ws.io.model.entity.PacienteEntity;
import mz.gov.misau.sigsmi.ws.io.repository.PacienteRepository;
import mz.gov.misau.sigsmi.ws.service.EnderecoService;
import mz.gov.misau.sigsmi.ws.service.PacienteService;
import mz.gov.misau.sigsmi.ws.shared.MyUtils;
import mz.gov.misau.sigsmi.ws.shared.dto.EnderecoDTO;
import mz.gov.misau.sigsmi.ws.shared.dto.GravidezDTO;
import mz.gov.misau.sigsmi.ws.shared.dto.PacienteDTO;
import mz.gov.misau.sigsmi.ws.ui.model.response.history.PacienteHistorialClinica;

@Service
public class PacienteServiceImpl implements PacienteService {

	@Autowired
	private MyUtils myUtils;

	@Autowired
	private EnderecoService enderecoService;

	@Autowired
	private GravidezServiceImpl gravidezService;

	@Autowired
	private PacienteRepository pacienteRepository;

	private final ModelMapper mapper = new ModelMapper();

	@Override
	public List<PacienteDTO> search() {
		
		List<PacienteEntity> pacientes = pacienteRepository.findAll();
		Type pacienteTypeList = new TypeToken<List<PacienteDTO>>() {
		}.getType();
		return mapper.map(pacientes, pacienteTypeList);
	}

	@Override
	public PacienteDTO create(PacienteDTO pacienteDTO) {

		pacienteDTO.setPacienteId(myUtils.generateUrlResource(30));
		pacienteDTO.setCriadoEm(LocalDate.now());

		PacienteEntity paciente = mapper.map(pacienteDTO, PacienteEntity.class);
		
		if(!paciente.getContactos().equals(null)) {
			for (ContactoEntity contacto : paciente.getContactos()) {
				contacto.setContactoId(myUtils.generateUrlResource(30));
				contacto.setPaciente(paciente);
			}
		}

		if(!paciente.getEnderecos().equals(null)) {
			for (EnderecoEntity endereco : paciente.getEnderecos()) {
				endereco.setEnderecoId(myUtils.generateUrlResource(30));
				endereco.setPaciente(paciente);
			}
		}

		paciente = pacienteRepository.save(paciente);

		return mapper.map(paciente, PacienteDTO.class);
	}

	@Override
	public PacienteDTO update(PacienteDTO pacienteDTO, String pacienteId) {

		PacienteEntity foundPaciente = findByPacienteId(pacienteId);
		PacienteEntity paciente = mapper.map(pacienteDTO, PacienteEntity.class);
		paciente.setId(foundPaciente.getId());
		paciente.setPacienteId(pacienteId);
		paciente.setCriadoEm(foundPaciente.getCriadoEm());
		paciente.setAtualizadoEm(LocalDateTime.now());

		for (EnderecoEntity endereco : paciente.getEnderecos()) {
			endereco.setPaciente(paciente);
		}

		for (ContactoEntity contacto : paciente.getContactos()) {
			contacto.setPaciente(paciente);
		}
		
		paciente = pacienteRepository.save(paciente);

		return mapper.map(paciente, PacienteDTO.class);
	}

	@Override
	public EnderecoDTO create(EnderecoDTO enderecoDTO, String pacienteId) {

		PacienteDTO pacienteDTO = mapper.map(findByPacienteId(pacienteId), PacienteDTO.class);
		enderecoDTO.setPaciente(pacienteDTO);
		return enderecoService.create(enderecoDTO);
	}

	public List<EnderecoDTO> searchAddresses(String pacienteId) {
		List<EnderecoDTO> enderecos = enderecoService.foundAll(findByPacienteId(pacienteId));
		return enderecos;
	}

	public PacienteDTO findPaciente(String pacienteId) {
		return mapper.map(findByPacienteId(pacienteId), PacienteDTO.class);
	}

	private PacienteEntity findByPacienteId(String pacienteId) {
		Optional<PacienteEntity> foundPaciente = pacienteRepository.findByPacienteId(pacienteId);
		if (!foundPaciente.isPresent())
			throw new PacienteNotFoundException(PacienteNotFoundException.class.toString().replace("class ", ""));
		return foundPaciente.get();
	}

	private PacienteEntity operationPacienteId(String pacienteId) {
		Optional<PacienteEntity> foundPaciente = pacienteRepository.findByPacienteId(pacienteId);
		if (!foundPaciente.isPresent())
			throw new DenidOperationAndInvalidResourceException(
					DenidOperationAndInvalidResourceException.class.toString().replace("class ", ""));
		return foundPaciente.get();
	}

	@Override
	public EnderecoDTO update(EnderecoDTO enderecoDTO, String pacienteId, String enderecoId) {
		PacienteEntity paciente = operationPacienteId(pacienteId);
		enderecoDTO.setPaciente(mapper.map(paciente, PacienteDTO.class));
		EnderecoDTO returnValue = enderecoService.update(enderecoDTO, enderecoId);
		return returnValue;
	}

	public GravidezDTO create(GravidezDTO gravidezDTO, String pacienteId) {
		PacienteDTO pacienteDTO = mapper.map(findByPacienteId(pacienteId), PacienteDTO.class);
		gravidezDTO.setPaciente(pacienteDTO);
		return gravidezService.create(gravidezDTO);
	}

	public List<GravidezDTO> findGravidezByPaciente(String pacienteId) {

		PacienteEntity paciente = findByPacienteId(pacienteId);
		return gravidezService.findGravidezByPaciente(paciente);
	}

	@Override
	public PacienteHistorialClinica getClinicHistory(String pacienteId) {
		
		PacienteEntity paciente = findByPacienteId(pacienteId);
		
		PacienteHistorialClinica returnValue = mapper.map(paciente, PacienteHistorialClinica.class);
		return returnValue;
	}

}
