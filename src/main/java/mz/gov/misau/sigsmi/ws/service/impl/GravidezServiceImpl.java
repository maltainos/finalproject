package mz.gov.misau.sigsmi.ws.service.impl;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mz.gov.misau.sigsmi.ws.exception.resource.GravidezNotFoundException;
import mz.gov.misau.sigsmi.ws.io.model.entity.ConsultaPreNatalEntity;
import mz.gov.misau.sigsmi.ws.io.model.entity.GravidezEntity;
import mz.gov.misau.sigsmi.ws.io.model.entity.PacienteEntity;
import mz.gov.misau.sigsmi.ws.io.repository.GravidezRepository;
import mz.gov.misau.sigsmi.ws.service.GravidezService;
import mz.gov.misau.sigsmi.ws.shared.MyUtils;
import mz.gov.misau.sigsmi.ws.shared.dto.ConsultaPreNatalDTO;
import mz.gov.misau.sigsmi.ws.shared.dto.GravidezDTO;

@Service
@Transactional
public class GravidezServiceImpl implements GravidezService {

	@Autowired
	private MyUtils myUtils;

	@Autowired
	private ConsultaPreNatalServiceImpl cpnService;

	@Autowired
	private GravidezRepository gravidezRepository;

	private final ModelMapper MAPPER = new ModelMapper();

	@Override
	public List<GravidezDTO> findAll(int page, int limit) {

		if (page > 0)
			page = page - 1;

		Pageable pageable = PageRequest.of(page, limit);
		Page<GravidezEntity> pageGravidezes = gravidezRepository.findAll(pageable);
		List<GravidezEntity> gravidezes = pageGravidezes.getContent();

		Type typeListGravidezDTO = new TypeToken<List<GravidezDTO>>() {
		}.getType();
		List<GravidezDTO> returnValue = MAPPER.map(gravidezes, typeListGravidezDTO);

		return returnValue;
	}

	@Override
	public GravidezDTO create(GravidezDTO gravidezDTO) {
		
		System.out.println(gravidezDTO);

		gravidezDTO.setGravidezId(myUtils.generateUrlResource(30));
		gravidezDTO.setDataRegistro(LocalDate.now());

		GravidezEntity gravidez = MAPPER.map(gravidezDTO, GravidezEntity.class);
		gravidez = gravidezRepository.save(gravidez);

		GravidezDTO returnValue = MAPPER.map(gravidez, GravidezDTO.class);
		return returnValue;
	}

	@Override
	public List<GravidezDTO> findGravidezByPaciente(PacienteEntity paciente) {
		List<GravidezEntity> gravidezes = gravidezRepository.findByPaciente(paciente);

		Type typeListGravidezDTO = new TypeToken<List<GravidezDTO>>() {
		}.getType();

		List<GravidezDTO> returnValue = MAPPER.map(gravidezes, typeListGravidezDTO);
		return returnValue;
	}

	private GravidezEntity findGravidez(String gravidezId) {

		Optional<GravidezEntity> gravidez = gravidezRepository.findByGravidezId(gravidezId);
		if (!gravidez.isPresent())
			throw new GravidezNotFoundException(GravidezNotFoundException.class.toString());
		return gravidez.get();
	}

	@Override
	public GravidezDTO findByGravidezId(String gravidezId) {

		GravidezEntity gravidez = findGravidez(gravidezId);
		GravidezDTO returnValue = MAPPER.map(gravidez, GravidezDTO.class);

		return returnValue;
	}

	@Override
	public GravidezDTO update(GravidezDTO gravidezDTO, String gravidezId) {

		GravidezEntity gravidez = findGravidez(gravidezId);
		if (!gravidez.isEnable()) {

			gravidez.setDataParto(gravidezDTO.getDataParto());
			gravidez.setDataAtualizacao(LocalDateTime.now());
			gravidez.setAborto(gravidezDTO.getAborto());
			gravidez.setLocalParto(gravidezDTO.getLocalParto());
			gravidez.setGravidezStatus(gravidezDTO.getGravidezStatus());
			gravidez = gravidezRepository.save(gravidez);
		}
		GravidezDTO returnValue = MAPPER.map(gravidez, GravidezDTO.class);
		return returnValue;
	}

	@Override
	public ConsultaPreNatalDTO doingConsulta(String gravidezId, ConsultaPreNatalDTO cpnDTO) {

		GravidezEntity gravidez = findGravidez(gravidezId);
		ConsultaPreNatalEntity cpn = MAPPER.map(cpnDTO, ConsultaPreNatalEntity.class);
		cpn.setGravidez(gravidez);
		ConsultaPreNatalDTO returnValue = cpnService.create(cpn);

		return returnValue;
	}

}
