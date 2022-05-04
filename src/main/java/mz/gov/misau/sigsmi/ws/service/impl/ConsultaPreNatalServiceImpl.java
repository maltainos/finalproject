package mz.gov.misau.sigsmi.ws.service.impl;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mz.gov.misau.sigsmi.ws.exception.resource.ConsultaPNPNotFoundException;
import mz.gov.misau.sigsmi.ws.io.model.entity.ConsultaPreNatalEntity;
import mz.gov.misau.sigsmi.ws.io.repository.ConsultaPreNatalRepository;
import mz.gov.misau.sigsmi.ws.io.repository.filter.ConsultaFilter;
import mz.gov.misau.sigsmi.ws.service.ConsultaPreNatalService;
import mz.gov.misau.sigsmi.ws.shared.MyUtils;
import mz.gov.misau.sigsmi.ws.shared.dto.ConsultaPreNatalDTO;

@Service
public class ConsultaPreNatalServiceImpl implements ConsultaPreNatalService {

	@Autowired
	private MyUtils utility;

	private ModelMapper mapper;
	
	@Autowired
	private ConsultaPreNatalRepository cpnRepository;

	//@Autowired
	//private RecemNascidoServiceImpl recemNascidoService;
	
	public ConsultaPreNatalServiceImpl() {
		// TODO Auto-generated constructor stub
		mapper = new ModelMapper();
	}

	@Override
	public ConsultaPreNatalDTO create(ConsultaPreNatalEntity cpn) {

		cpn.setConsultaPreNatalId(utility.generateUrlResource(30));
		cpn.setDataConsulta(LocalDate.now());
		cpn.setCodigoPvt(utility.generateLogin(4));
		cpn = cpnRepository.save(cpn);

		ConsultaPreNatalDTO returnValue = mapper.map(cpn, ConsultaPreNatalDTO.class);
		return returnValue;
	}

	@Override
	public List<ConsultaPreNatalDTO> find(int page, int limit, ConsultaFilter filter) {

		if (page > 0)
			page = page - 1;

		//Pageable pageable = PageRequest.of(page, limit);
		

		List<ConsultaPreNatalEntity> lista = cpnRepository.filtrar(filter);
		
		//Page<ConsultaPreNatalEntity> pageCPN = cpnRepository.findAll(pageable);
		// cpnRepository.

		//List<ConsultaPreNatalEntity> cpns = pageCPN.getContent();

		Type typeListCpnDTO = new TypeToken<List<ConsultaPreNatalDTO>>() {
		}.getType();
		List<ConsultaPreNatalDTO> returnValue = mapper.map(lista, typeListCpnDTO);
		return returnValue;
	}

	@Override
	public ConsultaPreNatalDTO findByConsultaId(String cpnId) {

		Optional<ConsultaPreNatalEntity> cpn = cpnRepository.findByConsultaPreNatalId(cpnId);

		if (!cpn.isPresent())
			throw new ConsultaPNPNotFoundException(ConsultaPNPNotFoundException.class.toString().replace("class", ""));

		ConsultaPreNatalDTO returnValue = mapper.map(cpn.get(), ConsultaPreNatalDTO.class);
		return returnValue;
	}

	/*
	private ConsultaPreNatalEntity findOne(String cpnId) {
		
		Optional<ConsultaPreNatalEntity> returnValue = cpnRepository.findByConsultaPreNatalId(cpnId);
		
		if(!returnValue.isPresent()) 
			throw new ConsultaPNPNotFoundException(
					ConsultaPNPNotFoundException.class.toString().replace("class", ""));
		return returnValue.get();
	}
	*/

}
