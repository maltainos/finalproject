package mz.gov.misau.sigsmi.ws.ui.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConsultaPPRequestDetailsModel {

	private String observacao;
	private String recomendacao;

	@NotBlank
	private float pesoMae;

	private boolean tomouVitaminaA;
	private boolean tomouSalFerroso;

}
