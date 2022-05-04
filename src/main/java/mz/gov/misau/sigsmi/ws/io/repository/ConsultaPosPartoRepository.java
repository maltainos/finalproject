package mz.gov.misau.sigsmi.ws.io.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import mz.gov.misau.sigsmi.ws.io.model.entity.ConsultaPosPartoEntity;
import mz.gov.misau.sigsmi.ws.io.repository.filter.consultas.ConsultaPosPartoRepositoryQuery;

public interface ConsultaPosPartoRepository
		extends PagingAndSortingRepository<ConsultaPosPartoEntity, Long>, ConsultaPosPartoRepositoryQuery {

	public Optional<ConsultaPosPartoEntity> findByConsultaPosPartoId(String cppId);

}
