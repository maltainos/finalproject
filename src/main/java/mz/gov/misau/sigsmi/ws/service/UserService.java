package mz.gov.misau.sigsmi.ws.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import mz.gov.misau.sigsmi.ws.shared.dto.UserDTO;

public interface UserService extends UserDetailsService {

	public List<UserDTO> findUsers(String sortBy);

	public List<UserDTO> findUsers(int page, int limit, String sortBy);

	public void deleteUser(String userId);

	public UserDTO createUser(UserDTO userDTO);

	public UserDTO findUserByEmail(String email);

	public UserDTO findUserByUserId(String userId);

	public UserDTO updateUser(UserDTO userDTO, String userId);

	public UserDTO grantAuthorities(String userid, List<String> userLevelIds);

	public UserDTO rovekeAuthorities(String userid, List<String> userLevelIds);
}
