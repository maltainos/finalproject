package mz.gov.misau.sigsmi.ws.ui.model.response.report;

import java.math.BigInteger;
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
public class RecemNascidoYearReport {
	
	private int year;
	private List<String> monthName;
	private List<BigInteger> monthData;

}
