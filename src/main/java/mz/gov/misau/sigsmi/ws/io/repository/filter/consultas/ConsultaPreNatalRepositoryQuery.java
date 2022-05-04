package mz.gov.misau.sigsmi.ws.io.repository.filter.consultas;

import java.util.List;

import mz.gov.misau.sigsmi.ws.io.model.entity.ConsultaPreNatalEntity;
import mz.gov.misau.sigsmi.ws.io.repository.filter.ConsultaFilter;

public interface ConsultaPreNatalRepositoryQuery {

	public List<ConsultaPreNatalEntity> filtrar(ConsultaFilter filter);
	public List<Integer> countDeathInTheFirstWeek(ConsultaFilter filter);

}
