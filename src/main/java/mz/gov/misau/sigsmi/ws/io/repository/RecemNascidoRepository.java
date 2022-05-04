package mz.gov.misau.sigsmi.ws.io.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import mz.gov.misau.sigsmi.ws.io.model.entity.RecemNascidoEntity;
import mz.gov.misau.sigsmi.ws.io.repository.filter.criancas.RecemNascidoQuery;

@Repository
@EnableJpaRepositories
public interface RecemNascidoRepository extends PagingAndSortingRepository<RecemNascidoEntity, Long>, RecemNascidoQuery {

	public Optional<RecemNascidoEntity> findByRecemNascidoId(String recemNascidoId);

	@Query("UPDATE RecemNascidoEntity rn SET rn.deathFirstWeeky = ?2 WHERE rn.recemNascidoId = ?1")
	@Modifying
	public RecemNascidoEntity isDeathInFirstWeeky(String recemNascidoId, boolean isDeath);

}
