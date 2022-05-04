package mz.gov.misau.sigsmi.ws.ui.model.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ConsultaPNRequestDetailsModel {

	private String codigoPvt;
	private String observacoes;
	private String recomendacoes;
	private Float pesoMae;
	private LocalDate dataProvavelDeParto;

}
