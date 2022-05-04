package mz.gov.misau.sigsmi.ws.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mz.gov.misau.sigsmi.ws.service.impl.GestaoPartoServiceImpl;
import mz.gov.misau.sigsmi.ws.shared.dto.RecemNascidoDTO;
import mz.gov.misau.sigsmi.ws.ui.model.request.RecemNascidoRequestDetailsModel;
import mz.gov.misau.sigsmi.ws.ui.model.response.RecemNascidoRest;

@RestController
@RequestMapping(path = "partos")
public class GestaoPartoController {

	@Autowired
	private GestaoPartoServiceImpl gestaoPartoService;

	private final ModelMapper MAPPER = new ModelMapper();

	@PostMapping(path = "/{partoId}/recem-nascido")
	@Secured("ROLE_CREATE_KIDS")
	public ResponseEntity<RecemNascidoRest> storeRecemNascido(@PathVariable String partoId,
			@Valid @RequestBody RecemNascidoRequestDetailsModel recemNascidoRequest) {

		RecemNascidoDTO recemNascidoDTO = MAPPER.map(recemNascidoRequest, RecemNascidoDTO.class);
		recemNascidoDTO = gestaoPartoService.storeRecemNascido(partoId, recemNascidoDTO);
		RecemNascidoRest returnValue = MAPPER.map(recemNascidoDTO, RecemNascidoRest.class);

		return ResponseEntity.ok(returnValue);
	}

}
