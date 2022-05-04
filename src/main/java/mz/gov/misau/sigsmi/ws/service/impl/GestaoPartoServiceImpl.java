package mz.gov.misau.sigsmi.ws.service.impl;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mz.gov.misau.sigsmi.ws.exception.resource.GestaoDePartoNotFoundException;
import mz.gov.misau.sigsmi.ws.exception.resource.PartoDeneidException;
import mz.gov.misau.sigsmi.ws.io.model.entity.GestaoDePartoEntity;
import mz.gov.misau.sigsmi.ws.io.model.entity.GravidezEntity;
import mz.gov.misau.sigsmi.ws.io.model.entity.RecemNascidoEntity;
import mz.gov.misau.sigsmi.ws.io.repository.GestaoPartoRepository;
import mz.gov.misau.sigsmi.ws.io.repository.GravidezRepository;
import mz.gov.misau.sigsmi.ws.io.repository.filter.GestaoPartoFilter;
import mz.gov.misau.sigsmi.ws.service.GestaoPartoService;
import mz.gov.misau.sigsmi.ws.shared.MyUtils;
import mz.gov.misau.sigsmi.ws.shared.dto.GestaoPartoDTO;
import mz.gov.misau.sigsmi.ws.shared.dto.RecemNascidoDTO;

@Service
public class GestaoPartoServiceImpl implements GestaoPartoService {

	@Autowired
	private MyUtils utility;

	private final ModelMapper MAPPER = new ModelMapper();

	@Autowired
	private GravidezRepository gravidezRepository;

	@Autowired
	private RecemNascidoServiceImpl recemNascidoService;

	@Autowired
	private GestaoPartoRepository gestaoPartoRepository;

	@Override
	public List<GestaoPartoDTO> find(int page, int limit, GestaoPartoFilter partoFilter) {

		if (page > 0)
			page = page - 1;
		Pageable pageable = PageRequest.of(page, limit);

		Page<GestaoDePartoEntity> pageParto = gestaoPartoRepository.findAll(pageable);

		List<GestaoDePartoEntity> partos = pageParto.getContent();

		Type typedListPartoDTO = new TypeToken<List<GestaoPartoDTO>>() {
		}.getType();

		List<GestaoPartoDTO> returnValue = MAPPER.map(partos, typedListPartoDTO);
		return returnValue;
	}

	@Override
	public GestaoPartoDTO findByGestaoPartoId(String gestaoPartoId) {

		GestaoDePartoEntity parto = findOne(gestaoPartoId);
		GestaoPartoDTO returnValue = MAPPER.map(parto, GestaoPartoDTO.class);
		return returnValue;
	}

	private GestaoDePartoEntity findOne(String gestaoPartoId) {

		Optional<GestaoDePartoEntity> parto = gestaoPartoRepository.findByGestaoPartoId(gestaoPartoId);
		if (!parto.isPresent())
			throw new GestaoDePartoNotFoundException(
					GestaoDePartoNotFoundException.class.toString().replace("class", ""));
		return parto.get();
	}

	@Override
	public GestaoPartoDTO create(GestaoPartoDTO gestaoPartoDTO) {

		if (gestaoPartoDTO.getGravidez().isEnable())
			throw new PartoDeneidException(PartoDeneidException.class.toString().replace("class", ""));

		gestaoPartoDTO.setGestaoPartoId(utility.generateUrlResource(30));
		gestaoPartoDTO.setCreateOn(LocalDate.now());

		GestaoDePartoEntity parto = MAPPER.map(gestaoPartoDTO, GestaoDePartoEntity.class);
		parto = gestaoPartoRepository.save(parto);

		GravidezEntity gravidez = gravidezRepository.isDone(true, parto.getGravidez().getGravidezId());
		parto.setGravidez(gravidez);

		GestaoPartoDTO returnValue = MAPPER.map(parto, GestaoPartoDTO.class);
		return returnValue;
	}

	@Override
	public RecemNascidoDTO storeRecemNascido(String partoId, RecemNascidoDTO recemNascidoDTO) {

		GestaoDePartoEntity parto = findOne(partoId);
		RecemNascidoEntity recemNascido = MAPPER.map(recemNascidoDTO, RecemNascidoEntity.class);
		recemNascido.setParto(parto);

		RecemNascidoDTO returnValue = recemNascidoService.create(recemNascido);
		return returnValue;
	}

}
