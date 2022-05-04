package mz.gov.misau.sigsmi.ws.ui.model.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mz.gov.misau.sigsmi.ws.io.model.enumeration.AbortoEnumeration;
import mz.gov.misau.sigsmi.ws.io.model.enumeration.GravidezStatus;

@Getter
@Setter
@ToString
public class GravidezRest extends RepresentationModel<GravidezRest> {

	private String gravidezId;
	private LocalDate dataEngravida;
	private LocalDate dataParto;
	private GravidezStatus gravidezStatus;
	private AbortoEnumeration aborto;
	private String localParto;
	private LocalDate dataRegistro;
	private LocalDateTime dataAtualizacao;
	private String pacienteId;

}
