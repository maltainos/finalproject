package mz.gov.misau.sigsmi.ws.ui.model.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

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
public class ConsultaPreNatalRest extends RepresentationModel<ConsultaPreNatalRest> {

	private String codigoPvt;
	private String consultaPreNatalId;
	private String observacoes;
	private String recomendacoes;
	private Float pesoMae;
	private LocalDateTime dataConsulta;
	private LocalDate dataProvavelDeParto;
	private String gravidezId;

}
