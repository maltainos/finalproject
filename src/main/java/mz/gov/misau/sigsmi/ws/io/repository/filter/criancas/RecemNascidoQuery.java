package mz.gov.misau.sigsmi.ws.io.repository.filter.criancas;

import java.time.LocalDate;

import mz.gov.misau.sigsmi.ws.ui.model.response.report.RecemNascidoMonthReport;
import mz.gov.misau.sigsmi.ws.ui.model.response.report.RecemNascidoWeekReport;
import mz.gov.misau.sigsmi.ws.ui.model.response.report.RecemNascidoYearReport;

public interface RecemNascidoQuery {

	RecemNascidoWeekReport getLastWeekReport();
	RecemNascidoMonthReport getLastMonthReport();
	RecemNascidoYearReport getLastYearReport(LocalDate firstYear,LocalDate lastYear);
}
