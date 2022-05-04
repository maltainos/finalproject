package mz.gov.misau.sigsmi.ws.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mz.gov.misau.sigsmi.ws.exception.resource.ConsultaPNPNotFoundException;
import mz.gov.misau.sigsmi.ws.io.model.entity.ConsultaPosPartoEntity;
import mz.gov.misau.sigsmi.ws.io.repository.ConsultaPosPartoRepository;
import mz.gov.misau.sigsmi.ws.io.repository.filter.ConsultaFilter;
import mz.gov.misau.sigsmi.ws.service.ConsultaPosPartoService;
import mz.gov.misau.sigsmi.ws.shared.dto.ConsultaPosPartoDTO;

@Service
public class ConsultaPosPartoServiceImpl implements ConsultaPosPartoService {

	@Autowired
	private ConsultaPosPartoRepository cppRepository;

	private ModelMapper mapper;

	public ConsultaPosPartoServiceImpl() {
		mapper = new ModelMapper();
	}

	private ConsultaPosPartoEntity findOne(String cppId) {

		Optional<ConsultaPosPartoEntity> returnValue = cppRepository.findByConsultaPosPartoId(cppId);

		if (!returnValue.isPresent())
			throw new ConsultaPNPNotFoundException(ConsultaPNPNotFoundException.class.toString().replace("class", ""));
		return returnValue.get();
	}

	@Override
	public List<ConsultaPosPartoDTO> listar(int page, int limit, ConsultaFilter filtro, String sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConsultaPosPartoDTO buscar(String cppId) {

		ConsultaPosPartoEntity cpp = findOne(cppId);
		ConsultaPosPartoDTO returnValue = mapper.map(cpp, ConsultaPosPartoDTO.class);

		return returnValue;
	}

	@Override
	public ConsultaPosPartoDTO update(ConsultaPosPartoDTO cppDTO, String cppId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String cppId) {

		ConsultaPosPartoEntity cpp = findOne(cppId);
		cppRepository.delete(cpp);
	}

	@Override
	public ConsultaPosPartoDTO create(ConsultaPosPartoDTO cppDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
