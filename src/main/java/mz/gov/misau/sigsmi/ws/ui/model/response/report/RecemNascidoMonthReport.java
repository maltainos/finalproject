package mz.gov.misau.sigsmi.ws.ui.model.response.report;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RecemNascidoMonthReport {
	
	private String monthName;
	private List<Integer> day;
	private List<Long> dailyData;
}
