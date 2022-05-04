package mz.gov.misau.sigsmi.ws.controller;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mz.gov.misau.sigsmi.ws.io.repository.filter.ConsultaFilter;
import mz.gov.misau.sigsmi.ws.service.impl.ConsultaPreNatalServiceImpl;
import mz.gov.misau.sigsmi.ws.shared.dto.ConsultaPreNatalDTO;
import mz.gov.misau.sigsmi.ws.ui.model.response.ConsultaPreNatalRest;

@RestController
@RequestMapping(path = "consultas-pre-natais")
public class ConsultaPreNatalRestController {

	@Autowired
	private ConsultaPreNatalServiceImpl cpnService;

	private ModelMapper mapper = new ModelMapper();

	@GetMapping
	//@Secured("ROLE_SELECT_CPN")
	public ResponseEntity<List<ConsultaPreNatalRest>> search(@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "limit", defaultValue = "10") int limit, ConsultaFilter filter) {

		System.out.println(filter);
		List<ConsultaPreNatalDTO> cpnDTO = cpnService.find(page, limit, filter);

		Type typeListCPNRest = new TypeToken<List<ConsultaPreNatalRest>>() {
		}.getType();
		
		List<ConsultaPreNatalRest> returnValue = mapper.map(cpnDTO, typeListCPNRest);
		
		System.out.println(returnValue.size());
		
		//if (returnValue.isEmpty())
			//return ResponseEntity.status(HttpStatus.NO_CONTENT).body(returnValue);

		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}

	@GetMapping(path = "/{cpnId}")
	//@Secured("ROLE_SELECT_CPN")
	public ConsultaPreNatalRest buscar(@PathVariable String cpnId) {

		return new ConsultaPreNatalRest();
	}
	
}
