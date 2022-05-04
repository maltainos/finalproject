package mz.gov.misau.sigsmi.ws.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mz.gov.misau.sigsmi.ws.io.repository.filter.ConsultaFilter;
import mz.gov.misau.sigsmi.ws.service.impl.ConsultaPosPartoServiceImpl;
import mz.gov.misau.sigsmi.ws.shared.dto.ConsultaPosPartoDTO;
import mz.gov.misau.sigsmi.ws.ui.event.CreateResourceEvent;
import mz.gov.misau.sigsmi.ws.ui.model.request.ConsultaPPRequestDetailsModel;
import mz.gov.misau.sigsmi.ws.ui.model.response.ConsultaPosPartoRest;

@RestController
@RequestMapping(path = "consultas-pos-parto")
public class ConsultaPosPartoRestController {

	private ApplicationEventPublisher publisher;

	@Autowired
	private ConsultaPosPartoServiceImpl cppService;

	private ModelMapper mapper;

	public ConsultaPosPartoRestController() {
		mapper = new ModelMapper();
	}

	@GetMapping
	@Secured("ROLE_SELECT_CPP")
	public List<ConsultaPosPartoRest> listar(@RequestParam(value = "page") int page,
			@RequestParam(value = "limit") int limit, ConsultaFilter filter, String sort) {

		cppService.listar(page, limit, filter, sort);

		return new ArrayList<>();
	}

	@GetMapping(path = "/{cppId}")
	@Secured("ROLE_SELECT_CPP")
	public ResponseEntity<ConsultaPosPartoRest> buscar(@PathVariable String cppId) {

		ConsultaPosPartoDTO cppDTO = cppService.buscar(cppId);
		ConsultaPosPartoRest returnValue = mapper.map(cppDTO, ConsultaPosPartoRest.class);

		return ResponseEntity.ok(returnValue);
	}

	@PostMapping
	@Secured("ROLE_CREATE_CPP")
	public ResponseEntity<ConsultaPosPartoRest> create(@Valid @RequestBody ConsultaPPRequestDetailsModel cppRequest,
			HttpServletResponse response) {

		ConsultaPosPartoDTO cppDTO = mapper.map(cppRequest, ConsultaPosPartoDTO.class);

		cppDTO = cppService.create(cppDTO);

		ConsultaPosPartoRest returnValue = mapper.map(cppDTO, ConsultaPosPartoRest.class);

		publisher.publishEvent(new CreateResourceEvent(this, response, returnValue.getConsultaPosPartoId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}

}
