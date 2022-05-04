package mz.gov.misau.sigsmi.ws.shared.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConsultaPosPartoDTO {

	private Long id;
	private String codigoPvt;
	private String consultaPosPartoId;
	private String observacao;
	private String recomendacao;
	private float pesoMae;
	private LocalDate dataConsulta;
	private boolean tomouVitaminaA;
	private boolean tomouSalFerroso;
	private GravidezDTO gravidez;

}
