package mz.gov.misau.sigsmi.ws.service;

import java.util.List;

import mz.gov.misau.sigsmi.ws.io.model.entity.ConsultaPreNatalEntity;
import mz.gov.misau.sigsmi.ws.io.repository.filter.ConsultaFilter;
import mz.gov.misau.sigsmi.ws.shared.dto.ConsultaPreNatalDTO;

public interface ConsultaPreNatalService {

	// This Endpoit
	public List<ConsultaPreNatalDTO> find(int page, int limit, ConsultaFilter filter);

	public ConsultaPreNatalDTO findByConsultaId(String cpnId);

	public ConsultaPreNatalDTO create(ConsultaPreNatalEntity cpn);
}
