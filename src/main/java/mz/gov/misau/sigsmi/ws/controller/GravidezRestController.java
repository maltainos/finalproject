package mz.gov.misau.sigsmi.ws.controller;

import java.lang.reflect.Type;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mz.gov.misau.sigsmi.ws.io.repository.filter.GravidezFilter;
import mz.gov.misau.sigsmi.ws.service.impl.GestaoPartoServiceImpl;
import mz.gov.misau.sigsmi.ws.service.impl.GravidezServiceImpl;
import mz.gov.misau.sigsmi.ws.shared.dto.ConsultaPreNatalDTO;
import mz.gov.misau.sigsmi.ws.shared.dto.GestaoPartoDTO;
import mz.gov.misau.sigsmi.ws.shared.dto.GravidezDTO;
import mz.gov.misau.sigsmi.ws.ui.model.request.ConsultaPNRequestDetailsModel;
import mz.gov.misau.sigsmi.ws.ui.model.request.GestaoPartoRequestDetailsModel;
import mz.gov.misau.sigsmi.ws.ui.model.request.GravidezRequestDetailsModel;
import mz.gov.misau.sigsmi.ws.ui.model.response.ConsultaPreNatalRest;
import mz.gov.misau.sigsmi.ws.ui.model.response.GravidezRest;

@RestController
@RequestMapping(path = "gravidezes")
public class GravidezRestController {

	@Autowired
	private GravidezServiceImpl gravidezService;

	private final ModelMapper MAPPER = new ModelMapper();

	private GestaoPartoServiceImpl gestaoPartoService;

	@GetMapping
	//@Secured("ROLE_SELECT_PREGNANCY")
	public ResponseEntity<List<GravidezRest>> search(@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "limit", defaultValue = "10") int limit, GravidezFilter gravidezFilter) {

		List<GravidezDTO> gravidezDTO = gravidezService.findAll(page, limit);

		Type typeListGtavidezRest = new TypeToken<List<GravidezRest>>() {
		}.getType();

		List<GravidezRest> returnValue = MAPPER.map(gravidezDTO, typeListGtavidezRest);

		if (returnValue.isEmpty())
			return ResponseEntity.noContent().build();

		return ResponseEntity.ok(returnValue);
	}

	@GetMapping(path = "/{gravidezId}")
	//@Secured("ROLE_SELECT_PREGNANCY")
	public ResponseEntity<GravidezRest> findById(@PathVariable String gravidezId) {

		GravidezDTO gravidezDTO = gravidezService.findByGravidezId(gravidezId);
		GravidezRest returnValue = MAPPER.map(gravidezDTO, GravidezRest.class);
		
		System.out.println(returnValue);

		return ResponseEntity.ok(returnValue);
	}

	@PutMapping(path = "/{gravidezId}")
	//@Secured("ROLE_UPDATE_PREGNANCY")
	public ResponseEntity<?> update(@PathVariable String gravidezId,
			@Valid @RequestBody GravidezRequestDetailsModel gravidezRequest) {

		GravidezDTO gravidezDTO = MAPPER.map(gravidezRequest, GravidezDTO.class);
		gravidezDTO = gravidezService.update(gravidezDTO, gravidezId);

		if (gravidezDTO.isEnable()) {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Esta gravidez nao pode ser actualizada!");
		}
		GravidezRest returnValue = MAPPER.map(gravidezDTO, GravidezRest.class);
		return ResponseEntity.ok(returnValue);
	}

	@PostMapping(path = "/{gravidezId}/cpn")
	@Secured("ROLE_CREATE_CPN")
	public ResponseEntity<ConsultaPreNatalRest> doingConsulta(@PathVariable String gravidezId,
			@Valid @RequestBody ConsultaPNRequestDetailsModel consultaRequest) {

		ConsultaPreNatalDTO cpnDTO = MAPPER.map(consultaRequest, ConsultaPreNatalDTO.class);

		cpnDTO = gravidezService.doingConsulta(gravidezId, cpnDTO);

		ConsultaPreNatalRest returnValue = MAPPER.map(cpnDTO, ConsultaPreNatalRest.class);
		return ResponseEntity.ok(returnValue);
	}

	@PostMapping(path = "/{gravidezId}/gestao-parto")
	@Secured("ROLE_CREATE_GP")
	public ResponseEntity<?> doingParto(@PathVariable String gravidezId,
			@Valid @RequestBody GestaoPartoRequestDetailsModel gestaoPartoRequest) {

		GestaoPartoDTO returnValue = MAPPER.map(gestaoPartoRequest, GestaoPartoDTO.class);
		returnValue = gestaoPartoService.create(returnValue);

		return ResponseEntity.ok(returnValue);
	}
}
