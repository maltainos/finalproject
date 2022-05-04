package mz.gov.misau.sigsmi.ws.shared.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mz.gov.misau.sigsmi.ws.io.model.enumeration.AbortoEnumeration;
import mz.gov.misau.sigsmi.ws.io.model.enumeration.GravidezStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class GravidezDTO {

	private Long id;
	private String gravidezId;
	private boolean enable;
	private LocalDate dataEngravida;
	private LocalDate dataParto;
	private GravidezStatus gravidezStatus;
	private AbortoEnumeration aborto = AbortoEnumeration.SEM_ABORTO;
	private String localParto;
	private LocalDate dataRegistro;
	private LocalDate dataAtualizacao;
	private List<ConsultaPreNatalDTO> consultasPreNatal;
	private PacienteDTO paciente;

}
