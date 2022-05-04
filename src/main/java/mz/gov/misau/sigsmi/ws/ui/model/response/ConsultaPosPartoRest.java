package mz.gov.misau.sigsmi.ws.ui.model.response;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mz.gov.misau.sigsmi.ws.shared.dto.GravidezDTO;

@Getter
@Setter
@ToString
public class ConsultaPosPartoRest extends RepresentationModel<ConsultaPosPartoRest> {

	private String codigoPvt;
	private String consultaPosPartoId;
	private String observacao;
	private String recomendacao;
	private float pesoMae;
	private LocalDateTime dataConsulta;
	private boolean tomouVitaminaA;
	private boolean tomouSalFerroso;
	private GravidezDTO gravidez;

}
