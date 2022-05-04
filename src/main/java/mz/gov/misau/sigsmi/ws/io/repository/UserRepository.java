package mz.gov.misau.sigsmi.ws.io.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import mz.gov.misau.sigsmi.ws.io.model.entity.UserEntity;
import mz.gov.misau.sigsmi.ws.io.repository.filter.user.UserRepositoryQuery;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>, UserRepositoryQuery {

	Optional<UserEntity> findByEmail(String email);
	Optional<UserEntity> findByUserId(String userId);
	Optional<UserEntity> findUserByEmailVerificationToken(String token);

}
