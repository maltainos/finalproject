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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import io.swagger.annotations.ApiOperation;
import mz.gov.misau.sigsmi.ws.exception.resource.UserNameOrEmailExistException;
import mz.gov.misau.sigsmi.ws.exception.resource.UserNotFoundException;
import mz.gov.misau.sigsmi.ws.service.exporter.users.UsersCsvExporter;
import mz.gov.misau.sigsmi.ws.service.exporter.users.UsersExcelExporter;
import mz.gov.misau.sigsmi.ws.service.exporter.users.UsersPdfExporter;
import mz.gov.misau.sigsmi.ws.service.impl.UserServiceImpl;
import mz.gov.misau.sigsmi.ws.shared.dto.UserDTO;
import mz.gov.misau.sigsmi.ws.ui.event.CreateResourceEvent;
import mz.gov.misau.sigsmi.ws.ui.model.request.AuthorityRequestDetailsModel;
import mz.gov.misau.sigsmi.ws.ui.model.request.PasswordResetRequestModel;
import mz.gov.misau.sigsmi.ws.ui.model.request.UserRequestDetailsModel;
import mz.gov.misau.sigsmi.ws.ui.model.response.MensagemErro;
import mz.gov.misau.sigsmi.ws.ui.model.response.OperationStatusModel;
import mz.gov.misau.sigsmi.ws.ui.model.response.RequestOperationName;
import mz.gov.misau.sigsmi.ws.ui.model.response.RequestOperationStatus;
import mz.gov.misau.sigsmi.ws.ui.model.response.RoleRest;
import mz.gov.misau.sigsmi.ws.ui.model.response.UserLevelRest;
import mz.gov.misau.sigsmi.ws.ui.model.response.UserRest;

@RestController
@RequestMapping(path = "/users")
//@Api(value = "WS API Gestao de Usuarios")
@CrossOrigin(maxAge = 10, origins = "*")
public class UserRestController {
	
	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ApplicationEventPublisher publisher;

	private ModelMapper mapper;

	public UserRestController() {
		this.mapper = new ModelMapper();
	}
	
	@GetMapping
	@Secured("ROLE_SELECT_USER")
	//@ApiOperation(value = "Lista de Utilizadores Com Paginacao e Ordenacao")
	public List<UserRest> searchUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit,
			@RequestParam(value = "sortBy", defaultValue = "firstName") String sortBy) {

		//role.create();

		List<UserDTO> usersDTO = userService.findUsers(page, limit, sortBy);
		Type usersDTOType = new TypeToken<List<UserRest>>() {
		}.getType();
		return mapper.map(usersDTO, usersDTOType);
	}

	@GetMapping(path = "/email/{email}")
	@Secured("ROLE_SELECT_USER")
	//@ApiOperation(value = "Busca Utilizador atraves do Email")
	public UserRest findUserByEmail(@PathVariable String email) {
		UserDTO usersDTO = userService.findUserByEmail(email);
		UserRest returnValue = mapper.map(usersDTO, UserRest.class);

		returnValue.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(UserRestController.class).findUserByUserId(returnValue.getUserId()))
				.withSelfRel());

		for (UserLevelRest levelRest : returnValue.getGroups()) {
			levelRest.add(WebMvcLinkBuilder.linkTo(
					WebMvcLinkBuilder.methodOn(UserLevelRestController.class).findByLevelId(levelRest.getLevelId()))
					.withSelfRel());
			for (RoleRest roleRest : levelRest.getRoles()) {
				roleRest.add(WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder.methodOn(RoleRestController.class).findByRoleId(roleRest.getRoleId()))
						.withSelfRel());
			}
		}

		return returnValue;
	}

	@GetMapping(path = "/{userId}")
	@Secured("ROLE_SELECT_USER")
	//@ApiOperation(value = "Buscar Utilizador atraves do UserId")
	public UserRest findUserByUserId(@PathVariable String userId) {
		
		UserDTO usersDTO = userService.findUserByUserId(userId);
		
		UserRest returnValue = mapper.map(usersDTO, UserRest.class);

		returnValue.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(UserRestController.class).findUserByUserId(returnValue.getUserId()))
				.withSelfRel());

		for (UserLevelRest levelRest : returnValue.getGroups()) {
			levelRest.add(WebMvcLinkBuilder.linkTo(
					WebMvcLinkBuilder.methodOn(UserLevelRestController.class).findByLevelId(levelRest.getLevelId()))
					.withSelfRel());

			for (RoleRest roleRest : levelRest.getRoles()) {
				roleRest.add(WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder.methodOn(RoleRestController.class).findByRoleId(roleRest.getRoleId()))
						.withSelfRel());
			}
		}

		return returnValue;
	}

	@PostMapping
	@Secured("ROLE_CREATE_USER")
	//@ApiOperation(value = "cria um novo utilizador")
	public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserRequestDetailsModel userDetails,
			HttpServletResponse response) {
		
		UserDTO userDTO = mapper.map(userDetails, UserDTO.class);
		userDTO = userService.createUser(userDTO);

		publisher.publishEvent(new CreateResourceEvent(this, response, userDTO.getUserId()));

		UserRest returnValue = mapper.map(userDTO, UserRest.class);
		returnValue.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(UserRestController.class).findUserByUserId(returnValue.getUserId()))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}

	@PostMapping("/{userId}/grant-access")
	@Secured("ROLE_GRANT_AUTHORITY")
	//@ApiOperation(value = "Atribui autoridades a um utilizador")
	public ResponseEntity<UserRest> grantAuthority(@Valid @RequestBody AuthorityRequestDetailsModel authorityRequest,
			@PathVariable String userId) {

		System.out.println(userId);
		System.out.println(authorityRequest.getAuthorities());
		UserDTO userDTO = userService.grantAuthorities(userId, authorityRequest.getAuthorities());
		UserRest returnValue = mapper.map(userDTO, UserRest.class);

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(returnValue);

	}

	@PostMapping("/{userId}/revoke-access")
	@Secured("ROLE_REVOKE_AUTHORITY")
	//@ApiOperation(value = "remove autoridade de um utilizador")
	public ResponseEntity<UserRest> revoketAuthority(@Valid @RequestBody AuthorityRequestDetailsModel authorityRequest,
			@PathVariable String userId) {

		UserDTO userDTO = userService.rovekeAuthorities(userId, authorityRequest.getAuthorities());
		UserRest returnValue = mapper.map(userDTO, UserRest.class);

		return ResponseEntity.status(HttpStatus.LOCKED).body(returnValue);

	}

	@PutMapping(path = "/{userId}")
	@Secured("ROLE_UPDATE_USER")
	//@ApiOperation(value = "atualiza as informacoes de um utilizador")
	public ResponseEntity<UserRest> updateUser(@Valid @RequestBody UserRequestDetailsModel userDetails,
			@PathVariable String userId) {
		UserDTO userDTO = mapper.map(userDetails, UserDTO.class);
		userDTO = userService.updateUser(userDTO, userId);
		UserRest returnValue = mapper.map(userDTO, UserRest.class);

		returnValue.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(UserRestController.class).findUserByUserId(returnValue.getUserId()))
				.withSelfRel());

		for (UserLevelRest levelRest : returnValue.getGroups()) {
			levelRest.add(WebMvcLinkBuilder.linkTo(
					WebMvcLinkBuilder.methodOn(UserLevelRestController.class).findByLevelId(levelRest.getLevelId()))
					.withSelfRel());

			for (RoleRest roleRest : levelRest.getRoles()) {
				roleRest.add(WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder.methodOn(RoleRestController.class).findByRoleId(roleRest.getRoleId()))
						.withSelfRel());
			}
		}

		return ResponseEntity.ok(returnValue);
	}

	@DeleteMapping(path = "/{userId}")
	@Secured("ROLE_DELETE_USER")
	//@ApiOperation(value = "remove um utilizador")
	public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE);
		returnValue.setOperationStatus(RequestOperationStatus.SUCCESS);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping(path = "/email-verification")
	//@ApiOperation(value = "Ativa um utilizador atraves do token recebido por email")
	public ResponseEntity<OperationStatusModel> verifyEmailToken(@RequestParam(value = "token") String token) {
		
		
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.EMAIL_VERIFICATION);
		returnValue.setOperationStatus(RequestOperationStatus.ERROR);
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		
		if (userService.virifyEmailToken(token)) {
			returnValue.setOperationStatus(RequestOperationStatus.SUCCESS);
			status = HttpStatus.ACCEPTED;
		}
		return ResponseEntity.status(status).body(returnValue);
	}

	@PostMapping(path = "/password-reset-request")
	//@ApiOperation(value = "reseta o senha perdida ou esquecida de um utilizador")
	public ResponseEntity<OperationStatusModel> requestReset(
			@RequestBody PasswordResetRequestModel passwordResetModel) {
		
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET);
		returnValue.setOperationStatus(RequestOperationStatus.ERROR);
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;

		if (userService.requestPasswordReset(passwordResetModel.getEmail())) {
			returnValue.setOperationStatus(RequestOperationStatus.SUCCESS);
			status = HttpStatus.ACCEPTED;
		}
		return ResponseEntity.status(status).body(returnValue);
	}

	@GetMapping(path = "/export/csv")
	@Secured("ROLE_EXPORT_USER_CSV")
	//@ApiOperation(value = "exporta documento em formato CSV")
	public void exportToCSV(HttpServletResponse response) throws IOException {

		List<UserDTO> usersDTO = userService.findUsers("firstName");
		UsersCsvExporter exporter = new UsersCsvExporter();

		Type usersDTOType = new TypeToken<List<UserRest>>() {
		}.getType();
		List<UserRest> userExport = mapper.map(usersDTO, usersDTOType);

		exporter.export(userExport, response);

	}

	@GetMapping(path = "/export/pdf")
	@Secured("ROLE_EXPORT_USER_PDF")
	//@ApiOperation(value = "exporta documento em formato PDF")
	public void exportToPDF(HttpServletResponse response) throws IOException {

		List<UserDTO> usersDTO = userService.findUsers("firstName");
		UsersPdfExporter exporter = new UsersPdfExporter();

		Type usersDTOType = new TypeToken<List<UserRest>>() {
		}.getType();
		List<UserRest> userExport = mapper.map(usersDTO, usersDTOType);

		exporter.export(userExport, response);
	}

	@GetMapping(path = "/export/excel")
	@Secured("ROLE_EXPORT_USER_EXCEL")
	//@ApiOperation(value = "exporta documento em formato EXCEL")
	public void exportToEXCEL(HttpServletResponse response) throws IOException {

		List<UserDTO> usersDTO = userService.findUsers("firstName");
		UsersExcelExporter exporter = new UsersExcelExporter();

		Type usersDTOType = new TypeToken<List<UserRest>>() {
		}.getType();
		List<UserRest> userExport = mapper.map(usersDTO, usersDTOType);

		exporter.export(userExport, response);
	}

	// Exception que podem apenas serem lancadas por este controlador
	@ExceptionHandler({ UserNameOrEmailExistException.class })
	public ResponseEntity<MensagemErro> handleUserNameOrEmailException(UserNameOrEmailExistException ex) {
		MensagemErro error = new MensagemErro(HttpStatus.BAD_REQUEST.value(),
				messageSource.getMessage("username.invalido", null, LocaleContextHolder.getLocale()),
				LocalDateTime.now(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler({ UserNotFoundException.class })
	public ResponseEntity<MensagemErro> handleUserNotFoundException(UserNotFoundException ex) {
		MensagemErro error = new MensagemErro(HttpStatus.NOT_FOUND.value(),
				messageSource.getMessage("user.user-not-found", null, LocaleContextHolder.getLocale()),
				LocalDateTime.now(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

}
