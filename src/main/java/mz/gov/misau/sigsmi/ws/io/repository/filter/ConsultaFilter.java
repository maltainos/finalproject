package mz.gov.misau.sigsmi.ws.io.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConsultaFilter {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate iniciarEm;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate terminarEm;

	/*
	 * @DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDate partoDe;
	 * 
	 * @DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDate partoAte;
	 */

	public ConsultaFilter() {

		iniciarEm = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
		terminarEm = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().plus(1), 1);
		/*
		 * partoDe = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),
		 * 1); partoAte = LocalDate.of(LocalDate.now().getYear(),
		 * LocalDate.now().getMonth().plus(1), 1);
		 */
	}

}
