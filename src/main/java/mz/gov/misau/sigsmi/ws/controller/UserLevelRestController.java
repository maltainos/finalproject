package mz.gov.misau.sigsmi.ws.controller;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mz.gov.misau.sigsmi.ws.exception.resource.UserGroupDuplicateException;
import mz.gov.misau.sigsmi.ws.exception.resource.UserGroupNotFoundException;
import mz.gov.misau.sigsmi.ws.service.impl.UserLevelServiceImpl;
import mz.gov.misau.sigsmi.ws.shared.dto.UserLevelDTO;
import mz.gov.misau.sigsmi.ws.ui.model.request.UserLevelRequestDetailsModel;
import mz.gov.misau.sigsmi.ws.ui.model.response.MensagemErro;
import mz.gov.misau.sigsmi.ws.ui.model.response.OperationStatusModel;
import mz.gov.misau.sigsmi.ws.ui.model.response.RequestOperationName;
import mz.gov.misau.sigsmi.ws.ui.model.response.RequestOperationStatus;
import mz.gov.misau.sigsmi.ws.ui.model.response.UserLevelRest;

@RestController
@RequestMapping(path = "/access-level")
public class UserLevelRestController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserLevelServiceImpl userLevelService;

	private ModelMapper mapper;

	public UserLevelRestController() {
		this.mapper = new ModelMapper();
	}
	
	@GetMapping
	public List<UserLevelRest> search() {
		List<UserLevelDTO> usersLevelDTO = userLevelService.usersLevels();
		Type userLevelType = new TypeToken<List<UserLevelRest>>() {
		}.getType();
		List<UserLevelRest> returnValue = mapper.map(usersLevelDTO, userLevelType);
		return returnValue;
	}

	@GetMapping(path = "/{levelId}")
	public ResponseEntity<UserLevelRest> findByLevelId(@PathVariable String levelId) {

		UserLevelDTO userLevelDTO = userLevelService.findByLevelId(levelId);
		UserLevelRest returnValue = mapper.map(userLevelDTO, UserLevelRest.class);

		returnValue.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(UserLevelRestController.class).findByLevelId(levelId))
				.withSelfRel());
		return ResponseEntity.ok(returnValue);
	}

	@PostMapping
	@Secured("ROLE_CREATE_LEVEL")
	public UserLevelRest create(@Valid @RequestBody UserLevelRequestDetailsModel userLevelRequest) {

		UserLevelDTO userLevelDTO = mapper.map(userLevelRequest, UserLevelDTO.class);

		userLevelDTO = userLevelService.create(userLevelDTO);

		
		UserLevelRest returnValue = mapper.map(userLevelDTO, UserLevelRest.class);
		 returnValue.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
				 UserLevelRestController.class).findByLevelId(returnValue.getLevelId())).
		 withSelfRel());
		
		 return returnValue;

		//return null;
	}

	@DeleteMapping(path = "/{levelId}")
	@Secured("ROLE_DELETE_LEVEL")
	public ResponseEntity<OperationStatusModel> delete(@PathVariable String levelId) {

		userLevelService.delete(levelId);

		OperationStatusModel returnValue = new OperationStatusModel(RequestOperationName.DELETE,
				RequestOperationStatus.SUCCESS);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(returnValue);
	}

	@ExceptionHandler({ UserGroupNotFoundException.class })
	public ResponseEntity<?> handleUserGroupNotFoundException(UserGroupNotFoundException ex) {

		MensagemErro error = new MensagemErro(HttpStatus.NOT_FOUND.value(),
				messageSource.getMessage("recurso.recurso-nao-encontrado", null, LocaleContextHolder.getLocale()),
				LocalDateTime.now(), ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler({ UserGroupDuplicateException.class })
	public ResponseEntity<?> handleUserGroupDuplicateException(UserGroupDuplicateException ex) {

		MensagemErro error = new MensagemErro(HttpStatus.BAD_REQUEST.value(),
				messageSource.getMessage("recurso.recurso-dublicado", null, LocaleContextHolder.getLocale()),
				LocalDateTime.now(), ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
