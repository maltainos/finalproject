package mz.gov.misau.sigsmi.ws.ui.model.response.history;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mz.gov.misau.sigsmi.ws.io.model.enumeration.AbortoEnumeration;
import mz.gov.misau.sigsmi.ws.io.model.enumeration.GravidezStatus;

@Getter
@Setter
@ToString
public class GravidezRestHistoria {

	private LocalDateTime dataEngravida;
	private LocalDateTime dataParto;
	private GravidezStatus gravidezStatus;
	private boolean enable;
	private AbortoEnumeration aborto;
	private String localParto;
	private LocalDateTime dataRegistro;
	private List<ConsultaPreNatalHistorial> consultasPreNatal;
	private GestaoPartoHistorial gestaoParto;
}
