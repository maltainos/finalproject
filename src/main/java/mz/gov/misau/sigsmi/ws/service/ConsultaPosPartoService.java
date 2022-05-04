package mz.gov.misau.sigsmi.ws.service;

import java.util.List;

import mz.gov.misau.sigsmi.ws.io.repository.filter.ConsultaFilter;
import mz.gov.misau.sigsmi.ws.shared.dto.ConsultaPosPartoDTO;

public interface ConsultaPosPartoService {

	public List<ConsultaPosPartoDTO> listar(int page, int limit, ConsultaFilter filtro, String sort);

	public ConsultaPosPartoDTO buscar(String cppId);

	public ConsultaPosPartoDTO create(ConsultaPosPartoDTO cppDTO);

	public ConsultaPosPartoDTO update(ConsultaPosPartoDTO cppDTO, String cppId);

	public void delete(String cppId);

}
