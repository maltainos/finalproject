package mz.gov.misau.sigsmi.ws.shared.dto;

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
public class ConsultaPreNatalDTO {

	private Long id;
	private String codigoPvt;
	private String consultaPreNatalId;
	private String observacoes;
	private String recomendacoes;
	private Float pesoMae;
	private LocalDate dataConsulta;
	private LocalDate dataProvavelDeParto;
	private GravidezDTO gravidez;

}
