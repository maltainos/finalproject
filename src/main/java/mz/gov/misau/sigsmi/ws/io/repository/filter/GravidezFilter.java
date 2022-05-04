package mz.gov.misau.sigsmi.ws.io.repository.filter;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GravidezFilter {

	private LocalDateTime dataRegistro;
	private LocalDateTime dataProvavelParto;

}
