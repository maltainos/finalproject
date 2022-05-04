package mz.gov.misau.sigsmi.ws.service;

import java.util.List;

import mz.gov.misau.sigsmi.ws.io.model.entity.UserEntity;

public interface UserLevelGrantAuthorityService {

	public UserEntity grantAuthorities(UserEntity user, List<String> userLevelId);

	public UserEntity revokeAuthorities(UserEntity user, List<String> userLevelId);

}
