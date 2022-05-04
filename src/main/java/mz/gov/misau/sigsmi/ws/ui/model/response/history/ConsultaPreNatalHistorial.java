package mz.gov.misau.sigsmi.ws.ui.model.response.history;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConsultaPreNatalHistorial {

	private String codigoPvt;
	private String observacoes;
	private String recomendacoes;
	private Float pesoMae;
	private LocalDateTime dataConsulta;
	private LocalDate dataProvavelDeParto;
}
