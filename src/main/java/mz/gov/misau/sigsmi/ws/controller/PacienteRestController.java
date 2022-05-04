package mz.gov.misau.sigsmi.ws.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import mz.gov.misau.sigsmi.ws.exception.resource.EnderecoNotFoundException;
import mz.gov.misau.sigsmi.ws.exception.resource.PacienteNotFoundException;
import mz.gov.misau.sigsmi.ws.service.exporter.patients.PacientePdfExporter;
import mz.gov.misau.sigsmi.ws.service.impl.PacienteServiceImpl;
import mz.gov.misau.sigsmi.ws.shared.dto.EnderecoDTO;
import mz.gov.misau.sigsmi.ws.shared.dto.GravidezDTO;
import mz.gov.misau.sigsmi.ws.shared.dto.PacienteDTO;
import mz.gov.misau.sigsmi.ws.ui.model.request.EnderecoRequestDetailsModel;
import mz.gov.misau.sigsmi.ws.ui.model.request.GravidezRequestDetailsModel;
import mz.gov.misau.sigsmi.ws.ui.model.request.PacienteRequestDetailsModel;
import mz.gov.misau.sigsmi.ws.ui.model.response.EnderecoRest;
import mz.gov.misau.sigsmi.ws.ui.model.response.GravidezRest;
import mz.gov.misau.sigsmi.ws.ui.model.response.MensagemErro;
import mz.gov.misau.sigsmi.ws.ui.model.response.PacienteRest;
import mz.gov.misau.sigsmi.ws.ui.model.response.history.PacienteHistorialClinica;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping(path = "/pacientes")
public class PacienteRestController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	public PacienteServiceImpl pacienteService;

	private ModelMapper mapper;
	
	public PacienteRestController() {
		mapper = new ModelMapper();
	}

	@GetMapping
	@Secured("ROLE_SELECT_PATIENT")
	public List<PacienteRest> search() {

		List<PacienteDTO> pacientes = pacienteService.search();
		Type pacientesRest = new TypeToken<List<PacienteRest>>() {
		}.getType();

		return mapper.map(pacientes, pacientesRest);
	}

	@GetMapping(path = "/{pacienteId}")
	@Secured("ROLE_SELECT_PATIENT")
	public ResponseEntity<PacienteRest> findById(@PathVariable String pacienteId) {

		PacienteDTO pacienteDTO = pacienteService.findPaciente(pacienteId);
		PacienteRest returnValue = mapper.map(pacienteDTO, PacienteRest.class);

		return ResponseEntity.ok(returnValue);
	}

	@PostMapping
	@Secured("ROLE_CREATE_PATIENT")
	public ResponseEntity<PacienteRest> create(@RequestBody PacienteRequestDetailsModel pacienteRequest,
			HttpServletResponse response) {

		System.out.println(pacienteRequest.toString());
		
		ModelMapper mapper = new ModelMapper();
		PacienteDTO pacienteDTO = mapper.map(pacienteRequest, PacienteDTO.class);
		pacienteDTO = pacienteService.create(pacienteDTO);

		PacienteRest returnValue = mapper.map(pacienteDTO, PacienteRest.class);
		returnValue.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(PacienteRestController.class).findById(returnValue.getPacienteId()))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}

	@GetMapping(path = "/{pacienteId}/enderecos")
	@Secured("ROLE_SELECT_PATIENT_DETAILS")
	public List<EnderecoRest> searchAddresses(@PathVariable String pacienteId) {

		List<EnderecoDTO> enderecos = pacienteService.searchAddresses(pacienteId);
		Type enderecoTypeList = new TypeToken<List<EnderecoRest>>() {
		}.getType();

		return mapper.map(enderecos, enderecoTypeList);
	}

	@PostMapping(path = "/{pacienteId}/enderecos")
	@Secured("ROLE_CREATE_PATIENT_DETAILS")
	public ResponseEntity<EnderecoRest> create(@Valid @RequestBody EnderecoRequestDetailsModel enderecoRequest,
			@PathVariable String pacienteId, HttpServletResponse response) {

		EnderecoDTO enderecoDTO = mapper.map(enderecoRequest, EnderecoDTO.class);
		enderecoDTO = pacienteService.create(enderecoDTO, pacienteId);
		EnderecoRest returnValue = mapper.map(enderecoDTO, EnderecoRest.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}

	@PutMapping(path = "/{pacienteId}")
	@Secured("ROLE_UPDATE_PATIENT")
	public ResponseEntity<PacienteRest> update(@Valid @RequestBody PacienteRequestDetailsModel pacienteRequest,
			@PathVariable String pacienteId) {

		PacienteDTO pacienteDTO = mapper.map(pacienteRequest, PacienteDTO.class);
		pacienteDTO = pacienteService.update(pacienteDTO, pacienteId);
		PacienteRest returnValue = mapper.map(pacienteDTO, PacienteRest.class);

		return ResponseEntity.ok(returnValue);
	}

	@PutMapping(path = "/{pacienteId}/enderecos/{enderecoId}")
	@Secured("ROLE_UPDATE_DETAILS")
	public ResponseEntity<EnderecoRest> update(@Valid @RequestBody EnderecoRequestDetailsModel enderecoRequest,
			@PathVariable String pacienteId, @PathVariable String enderecoId) {

		EnderecoDTO enderecoDTO = mapper.map(enderecoRequest, EnderecoDTO.class);
		enderecoDTO = pacienteService.update(enderecoDTO, pacienteId, enderecoId);
		EnderecoRest returnValue = mapper.map(enderecoDTO, EnderecoRest.class);

		return ResponseEntity.ok(returnValue);
	}

	@GetMapping(path = "/{pacienteId}/gravidezes")
	@Secured("ROLE_SELECT_PREGNANCY")
	public ResponseEntity<List<GravidezRest>> create(@PathVariable String pacienteId) {

		List<GravidezDTO> gravidezesDTO = pacienteService.findGravidezByPaciente(pacienteId);
		Type typeListGravidezRest = new TypeToken<List<GravidezRest>>() {
		}.getType();
		List<GravidezRest> returnValue = mapper.map(gravidezesDTO, typeListGravidezRest);

		return ResponseEntity.ok(returnValue);
	}

	@PostMapping(path = "/{pacienteId}/gravidezes")
	//@Secured("ROLE_CREATE_PREGNANCY")
	public ResponseEntity<GravidezRest> createGravidez(@Valid @RequestBody GravidezRequestDetailsModel gravidezRequest,
			@PathVariable String pacienteId, HttpServletResponse response) {

		System.out.println(gravidezRequest.toString());
		
		GravidezDTO gravidezDTO = mapper.map(gravidezRequest, GravidezDTO.class);
		gravidezDTO = pacienteService.create(gravidezDTO, pacienteId);
		GravidezRest returnValue = mapper.map(gravidezDTO, GravidezRest.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}
	
	@GetMapping(path = "/{pacienteId}/historial-clinica")
	//@Secured("ROLE_VIEW_CLINIC_HISTORY")
	public ResponseEntity<PacienteHistorialClinica> viewClinicHistory(@PathVariable String pacienteId){
		
		PacienteHistorialClinica historiaClinica = pacienteService.getClinicHistory(pacienteId);
		
		return ResponseEntity.status(HttpStatus.OK).body(historiaClinica);
	}

	@GetMapping(path = "/{pacienteId}/historial-clinica/export")
	//@Secured("ROLE_VIEW_CLINIC_HISTORY")
	@ResponseStatus(HttpStatus.OK)
	public void exportClinicHistory(@PathVariable String pacienteId, HttpServletResponse response) throws IOException, JRException{
		
		PacienteHistorialClinica historialClinica = pacienteService.getClinicHistory(pacienteId);
		
		System.out.println(historialClinica.getPrimeiroNome());
		PacientePdfExporter exporter = new PacientePdfExporter();
		byte[] returnValue = exporter.export(historialClinica);
		
		System.out.println(returnValue.toString());
		//return returnValue;
	}

	@ExceptionHandler({ PacienteNotFoundException.class })
	public ResponseEntity<MensagemErro> handlePacienteNotFoundException(PacienteNotFoundException ex) {

		MensagemErro error = new MensagemErro(HttpStatus.NOT_FOUND.value(),
				messageSource.getMessage("recurso.recurso-nao-encontrado", null, LocaleContextHolder.getLocale()),
				LocalDateTime.now(), ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler({ EnderecoNotFoundException.class })
	public ResponseEntity<MensagemErro> handleEnderecoNotFoundException(EnderecoNotFoundException ex) {

		MensagemErro error = new MensagemErro(HttpStatus.NOT_FOUND.value(),
				messageSource.getMessage("recurso.recurso-nao-encontrado", null, LocaleContextHolder.getLocale()),
				LocalDateTime.now(), ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

}
