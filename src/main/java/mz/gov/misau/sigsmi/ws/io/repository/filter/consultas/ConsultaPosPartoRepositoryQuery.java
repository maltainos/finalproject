package mz.gov.misau.sigsmi.ws.io.repository.filter.consultas;

import java.util.List;

import org.springframework.data.domain.Pageable;

import mz.gov.misau.sigsmi.ws.io.model.entity.ConsultaPosPartoEntity;
import mz.gov.misau.sigsmi.ws.io.repository.filter.ConsultaFilter;

public interface ConsultaPosPartoRepositoryQuery {

	public List<ConsultaPosPartoEntity> filtrar(ConsultaFilter filter, Pageable pageable);
	
}
