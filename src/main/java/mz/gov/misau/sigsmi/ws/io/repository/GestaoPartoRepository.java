package mz.gov.misau.sigsmi.ws.io.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import mz.gov.misau.sigsmi.ws.io.model.entity.GestaoDePartoEntity;

@Repository
public interface GestaoPartoRepository extends PagingAndSortingRepository<GestaoDePartoEntity, Long> {

	public Optional<GestaoDePartoEntity> findByGestaoPartoId(String gestaoPartoId);

}
