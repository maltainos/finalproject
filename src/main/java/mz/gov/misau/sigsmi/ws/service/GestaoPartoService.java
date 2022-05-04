package mz.gov.misau.sigsmi.ws.service;

import java.util.List;

import javax.transaction.Transactional;

import mz.gov.misau.sigsmi.ws.io.repository.filter.GestaoPartoFilter;
import mz.gov.misau.sigsmi.ws.shared.dto.GestaoPartoDTO;
import mz.gov.misau.sigsmi.ws.shared.dto.RecemNascidoDTO;

@Transactional
public interface GestaoPartoService {

	public GestaoPartoDTO create(GestaoPartoDTO gestaoPartoDTO);

	public GestaoPartoDTO findByGestaoPartoId(String gestaoPartoId);

	public List<GestaoPartoDTO> find(int page, int limit, GestaoPartoFilter partoFilter);

	public RecemNascidoDTO storeRecemNascido(String partoId, RecemNascidoDTO recemNascidoDTO);
}
