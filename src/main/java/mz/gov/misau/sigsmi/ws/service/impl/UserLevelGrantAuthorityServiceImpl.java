package mz.gov.misau.sigsmi.ws.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mz.gov.misau.sigsmi.ws.io.model.entity.UserEntity;
import mz.gov.misau.sigsmi.ws.io.model.entity.UserLevelEntity;
import mz.gov.misau.sigsmi.ws.io.repository.UserLevelRepository;
import mz.gov.misau.sigsmi.ws.service.UserLevelGrantAuthorityService;

@Service
public class UserLevelGrantAuthorityServiceImpl implements UserLevelGrantAuthorityService {

	@Autowired
	private UserLevelRepository userLevelRepository;

	@Override
	public UserEntity grantAuthorities(UserEntity returnValue, List<String> userLevelIds) {

		List<UserLevelEntity> userLevels = new ArrayList<>();

		for (String userLevelId : userLevelIds) {

			Optional<UserLevelEntity> userLevel = userLevelRepository.findByLevelId(userLevelId);
			if (userLevel.isPresent()) {
				userLevels.add(userLevel.get());
			}
		}

		returnValue.setGroups(userLevels);
		return returnValue;
	}

	@Override
	public UserEntity revokeAuthorities(UserEntity returnValue, List<String> userLevelIds) {

		List<UserLevelEntity> userLevels = returnValue.getGroups();

		for (String userLevelId : userLevelIds) {

			Optional<UserLevelEntity> userLevel = userLevelRepository.findByLevelId(userLevelId);

			if (userLevel.isPresent()) {
				userLevels.remove(userLevel.get());
			}
		}

		returnValue.setGroups(userLevels);
		return returnValue;
	}

}
