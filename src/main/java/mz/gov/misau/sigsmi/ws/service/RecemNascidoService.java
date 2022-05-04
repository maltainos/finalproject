package mz.gov.misau.sigsmi.ws.service;

import java.util.List;

import javax.transaction.Transactional;

import mz.gov.misau.sigsmi.ws.io.model.entity.RecemNascidoEntity;
import mz.gov.misau.sigsmi.ws.shared.dto.RecemNascidoDTO;
import mz.gov.misau.sigsmi.ws.ui.model.response.report.RecemNascidoMonthReport;
import mz.gov.misau.sigsmi.ws.ui.model.response.report.RecemNascidoWeekReport;
import mz.gov.misau.sigsmi.ws.ui.model.response.report.RecemNascidoYearReport;

@Transactional
public interface RecemNascidoService {

	List<RecemNascidoDTO> getRecemNascidos(int page, int limit);
	RecemNascidoDTO create(RecemNascidoEntity recemNascido);
	RecemNascidoDTO isDeathInFirstWeeky(String recemNascidoId, boolean isDeath);
	RecemNascidoMonthReport getLastMonthReport();
	RecemNascidoWeekReport getLastWeekReport();
	List<RecemNascidoYearReport> compareCurrenteYearToPreviousYear();
	void getReportprocedure();

}
