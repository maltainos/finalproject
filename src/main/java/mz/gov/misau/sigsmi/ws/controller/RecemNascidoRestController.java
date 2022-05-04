package mz.gov.misau.sigsmi.ws.controller;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mz.gov.misau.sigsmi.ws.service.impl.RecemNascidoServiceImpl;
import mz.gov.misau.sigsmi.ws.shared.dto.RecemNascidoDTO;
import mz.gov.misau.sigsmi.ws.ui.model.request.RecemNascidoRequestDetailsModel;
import mz.gov.misau.sigsmi.ws.ui.model.response.RecemNascidoRest;
import mz.gov.misau.sigsmi.ws.ui.model.response.report.RecemNascidoMonthReport;
import mz.gov.misau.sigsmi.ws.ui.model.response.report.RecemNascidoWeekReport;
import mz.gov.misau.sigsmi.ws.ui.model.response.report.RecemNascidoYearReport;

@RestController
@RequestMapping(path = "/recem-nascidos")
public class RecemNascidoRestController {
	
	@Autowired
	private RecemNascidoServiceImpl recemNascidoService;
	
	private ModelMapper mapper = new ModelMapper();

	@GetMapping
	@Secured("ROLE_SELECT_KIDS")
	public List<RecemNascidoRest> getRecemNascidos(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name= "limit", defaultValue = "10") int limit) {
		
		List<RecemNascidoDTO> recemNascidosDTO = recemNascidoService.getRecemNascidos(page, limit);
		
		Type listTyped = new TypeToken<List<RecemNascidoRest>>() {}.getType();
		
		List<RecemNascidoRest> returnValue = mapper.map(recemNascidosDTO, listTyped);
		
		return returnValue;
	}

	@GetMapping(path = "/{recemNascidoId}")
	@Secured("ROLE_SELECT_KIDS")
	public RecemNascidoRest getRecemNascidos(@PathVariable String recemNascidoId) {
		return new RecemNascidoRest();
	}

	@PutMapping(path = "/{recemNascidoId}")
	@Secured("ROLE_UPDATE_KIDS")
	public RecemNascidoRest update(@PathVariable String recemNascidoId,
			@RequestBody RecemNascidoRequestDetailsModel recemNascido) {
		return new RecemNascidoRest();
	}
	
	@GetMapping(path = "/report/week", produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public RecemNascidoWeekReport getLastWeekReport(){
		
		RecemNascidoWeekReport returnValue = recemNascidoService.getLastWeekReport();
		
		return returnValue;
	}
	
	@GetMapping(path = "/test")
	public void getReport(){
		recemNascidoService.getReportprocedure();
	}
	
	@GetMapping(path = "/report/month", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public RecemNascidoMonthReport getLastMonthReport(){
		
		RecemNascidoMonthReport returnValue = recemNascidoService.getLastMonthReport();
		
		return returnValue;
	}
	
	@GetMapping(path = "/report/year", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<RecemNascidoYearReport> getLastYearReport(){
		
		List<RecemNascidoYearReport> returnValue = recemNascidoService.compareCurrenteYearToPreviousYear();
		
		return returnValue;
	}

}
