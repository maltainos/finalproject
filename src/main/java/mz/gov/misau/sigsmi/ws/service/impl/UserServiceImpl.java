package mz.gov.misau.sigsmi.ws.service.impl;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import mz.gov.misau.sigsmi.ws.exception.resource.UserNameOrEmailExistException;
import mz.gov.misau.sigsmi.ws.exception.resource.UserNotFoundException;
import mz.gov.misau.sigsmi.ws.io.model.entity.PasswordResetTokenEntity;
import mz.gov.misau.sigsmi.ws.io.model.entity.UserEntity;
import mz.gov.misau.sigsmi.ws.io.repository.PasswordResetTokenRepository;
import mz.gov.misau.sigsmi.ws.io.repository.UserRepository;
import mz.gov.misau.sigsmi.ws.service.UserService;
import mz.gov.misau.sigsmi.ws.shared.MyUtils;
import mz.gov.misau.sigsmi.ws.shared.dto.UserDTO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private MyUtils myUtils;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	UserLevelGrantAuthorityServiceImpl userLevelGrantAuthorityServe;

	private ModelMapper mapper;

	public UserServiceImpl() {
		mapper = new ModelMapper();
	}

	@Override
	public List<UserDTO> findUsers(String sortyBy) {

		Iterable<UserEntity> usersIterable = userRepository.findAll(Sort.by(sortyBy).ascending());

		List<UserEntity> users = new ArrayList<>();
		for (UserEntity iterator : usersIterable) {
			users.add(iterator);
		}

		Type usersTyped = new TypeToken<List<UserDTO>>() {
		}.getType();

		return mapper.map(users, usersTyped);
	}

	@Override
	public UserDTO createUser(UserDTO userDTO) {

		Optional<UserEntity> userFound = userRepository.findByEmail(userDTO.getEmail());
		if (userFound.isPresent())
			throw new UserNameOrEmailExistException(
					UserNameOrEmailExistException.class.toString().replace("class ", ""));

		userDTO.setUserId(myUtils.generateUrlResource(30));
		userDTO.setLogin(myUtils.generateLogin(8));
		userDTO.setCreatedOn(LocalDate.now());
		userDTO.setEmailVerificationToken(MyUtils.generateEmailVerificationToken(userDTO.getUserId()));

		UserEntity userForSave = mapper.map(userDTO, UserEntity.class);
		userForSave.setEncryptedPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

		UserEntity userSaved = userRepository.save(userForSave);

		UserDTO returnValue = mapper.map(userSaved, UserDTO.class);
		//new AmazonSES().verifyEmail(returnValue);
		return returnValue;
	}

	@Override
	public List<UserDTO> findUsers(int page, int limit, String sortBy) {

		if (page > 0)
			page -= page;

		Pageable pageable = PageRequest.of(page, limit);
		Page<UserEntity> users = userRepository.findAll(pageable);

		Type usersTyped = new TypeToken<List<UserDTO>>() {
		}.getType();

		return mapper.map(users.getContent(), usersTyped);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, String userId) {
		Optional<UserEntity> userFound = userRepository.findByUserId(userDTO.getUserId());
		if (!userFound.isPresent())
			throw new UserNotFoundException(UserNotFoundException.class.toString().replace("class ", ""));
		userDTO.setId(userFound.get().getId());
		userDTO.setUpdatedOn(LocalDateTime.now());
		UserEntity updateUser = mapper.map(userDTO, UserEntity.class);
		UserEntity updatedUser = userRepository.save(updateUser);

		UserDTO returnValue = mapper.map(updatedUser, UserDTO.class);
		return returnValue;
	}

	@Override
	public UserDTO findUserByEmail(String email) {

		Optional<UserEntity> findUser = userRepository.findByEmail(email);
		if (!findUser.isPresent())
			throw new UsernameNotFoundException(email);

		return mapper.map(findUser.get(), UserDTO.class);
	}

	@Override
	public UserDTO findUserByUserId(String userId) {

		UserEntity user = findOne(userId);
		UserDTO returnValue = mapper.map(user, UserDTO.class);

		return returnValue;
	}

	private UserEntity findOne(String userId) {

		Optional<UserEntity> findUser = userRepository.findByUserId(userId);
		if (!findUser.isPresent())
			throw new UserNotFoundException("UserNotFoundException");

		//System.out.println("USER..." + findUser.get().toString());
		return findUser.get();
	}

	@Override
	public void deleteUser(String userId) {

		UserEntity deleteUser = findOne(userId);
		userRepository.delete(deleteUser);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserEntity> findValue = userRepository.findByEmail(username);

		if (!findValue.isPresent())
			throw new UserNameOrEmailExistException("UserNameOrEmailExistException");
		UserEntity user = findValue.get();

		final Collection<GrantedAuthority> authorities = new ArrayList<>();

		user.getGroups().forEach(group -> {
			group.getRoles().forEach(role -> {
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
				authorities.add(authority);
			});
		});

		return new User(user.getEmail(), user.getEncryptedPassword(), user.getEmailVerificationStatus(), true, true,
				true, authorities);
	}

	public boolean virifyEmailToken(String token) {
		Optional<UserEntity> user = userRepository.findUserByEmailVerificationToken(token);
		if (user.isPresent()) {
			boolean hasTokenExpired = MyUtils.hasTokenExpired(token);

			if (!hasTokenExpired) {
				UserEntity userVerificationEmail = user.get();
				userVerificationEmail.setEmailVerificationStatus(true);
				userVerificationEmail.setEmailVerificationToken(null);
				userRepository.save(userVerificationEmail);
				return true;
			}
		}
		return false;
	}

	public boolean requestPasswordReset(String email) {
		boolean returnValue = false;
		Optional<UserEntity> user = userRepository.findByEmail(email);

		if (!user.isPresent()) {
			return returnValue;
		}

		String token = new MyUtils().generatePasswordResetToken(user.get().getUserId());
		PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
		passwordResetTokenEntity.setToken(token);
		// passwordResetTokenEntity.setUserDetails(user.get());
		passwordResetTokenRepository.save(passwordResetTokenEntity);

		return !returnValue;
	}

	@Override
	public UserDTO grantAuthorities(String userid, List<String> userLevelIds) {

		UserEntity user = findOne(userid);
		user = userLevelGrantAuthorityServe.grantAuthorities(user, userLevelIds);
		user.setUpdatedOn(LocalDateTime.now());

		user = userRepository.save(user);
		UserDTO returnValue = mapper.map(user, UserDTO.class);

		return returnValue;
	}

	@Override
	public UserDTO rovekeAuthorities(String userId, List<String> userLevelIds) {

		UserEntity user = findOne(userId);

		user = userLevelGrantAuthorityServe.revokeAuthorities(user, userLevelIds);
		user.setUpdatedOn(LocalDateTime.now());

		user = userRepository.save(user);

		UserDTO returnValue = mapper.map(user, UserDTO.class);

		return returnValue;
	}

}
