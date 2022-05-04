package mz.gov.misau.sigsmi.ws.io.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import mz.gov.misau.sigsmi.ws.io.model.entity.ConsultaPreNatalEntity;
import mz.gov.misau.sigsmi.ws.io.repository.filter.consultas.ConsultaPreNatalRepositoryQuery;

@Repository
public interface ConsultaPreNatalRepository
		extends PagingAndSortingRepository<ConsultaPreNatalEntity, Long>, ConsultaPreNatalRepositoryQuery {

	public Optional<ConsultaPreNatalEntity> findByConsultaPreNatalId(String cpnId);

}
