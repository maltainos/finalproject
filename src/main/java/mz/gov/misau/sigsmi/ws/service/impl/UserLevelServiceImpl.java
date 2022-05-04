package mz.gov.misau.sigsmi.ws.service.impl;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mz.gov.misau.sigsmi.ws.exception.resource.UserGroupDuplicateException;
import mz.gov.misau.sigsmi.ws.exception.resource.UserGroupNotFoundException;
import mz.gov.misau.sigsmi.ws.io.model.entity.RoleEntity;
import mz.gov.misau.sigsmi.ws.io.model.entity.UserLevelEntity;
import mz.gov.misau.sigsmi.ws.io.repository.RoleRepository;
import mz.gov.misau.sigsmi.ws.io.repository.UserLevelRepository;
import mz.gov.misau.sigsmi.ws.service.UserLevelService;
import mz.gov.misau.sigsmi.ws.shared.MyUtils;
import mz.gov.misau.sigsmi.ws.shared.dto.UserLevelDTO;

@Service
public class UserLevelServiceImpl implements UserLevelService {

	@Autowired
	private MyUtils myUtils;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserLevelRepository userLevelRepository;

	private final static ModelMapper MAPPER = new ModelMapper();

	@Override
	public List<UserLevelDTO> usersLevels() {
		List<UserLevelEntity> usersLevels = userLevelRepository.findAll();
		Type userLevelType = new TypeToken<List<UserLevelDTO>>() {
		}.getType();
		List<UserLevelDTO> returnValue = MAPPER.map(usersLevels, userLevelType);
		return returnValue;
	}

	@Override
	public UserLevelDTO create(UserLevelDTO userLevelDTO) {

		userLevelDTO.setLevelId(myUtils.generateUrlResource(30));
		userLevelDTO.setCreatedOn(LocalDate.now());

		UserLevelEntity userLevelStore = MAPPER.map(userLevelDTO, UserLevelEntity.class);

		List<RoleEntity> roles = new ArrayList<>();

		for (String roleDTO : userLevelDTO.getRoleConfig()) {

			Optional<RoleEntity> foundedRole = roleRepository.findByRoleId(roleDTO);
			if (foundedRole.isPresent())
				roles.add(foundedRole.get());
		}
		userLevelStore.setRoles(roles);
		
		Optional<UserLevelEntity> findValue = userLevelRepository.findByLevelName(userLevelStore.getLevelName());

		if(findValue.isPresent()) {
			throw new UserGroupDuplicateException(UserGroupDuplicateException.class.toString().replace("class", ""));
		}
		
		UserLevelEntity userLevelEntity = userLevelRepository.save(userLevelStore);

		return MAPPER.map(userLevelEntity, UserLevelDTO.class);
	}

	@Override
	public UserLevelDTO update(UserLevelDTO userLevelDTo, String levelId) {

		return null;
	}

	@Override
	public void delete(String levelId) {

		UserLevelEntity userLevel = findOne(levelId);
		userLevelRepository.delete(userLevel);

	}

	public UserLevelDTO findByLevelId(String levelId) {

		UserLevelEntity findLevel = findOne(levelId);

		return MAPPER.map(findLevel, UserLevelDTO.class);
	}

	private UserLevelEntity findOne(String levelId) {

		Optional<UserLevelEntity> findLevel = userLevelRepository.findByLevelId(levelId);
		if (!findLevel.isPresent())
			throw new UserGroupNotFoundException(UserGroupNotFoundException.class.toString().replace("class", ""));

		return findLevel.get();
	}

}
