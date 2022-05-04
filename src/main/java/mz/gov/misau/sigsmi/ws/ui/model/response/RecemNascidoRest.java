package mz.gov.misau.sigsmi.ws.ui.model.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mz.gov.misau.sigsmi.ws.io.model.enumeration.FortanelaAnterior;

@Getter
@Setter
@ToString
public class RecemNascidoRest extends RepresentationModel<RecemNascidoRest> {

	private String recemNascidoId;
	private String estadoGeral;
	private String dispneia;
	private String coloracao;
	private String icteria;
	private String temperatura;
	private boolean chupaBem;
	private boolean nadoVivo;
	private boolean deathFirstWeeky;
	private String estadoCotoUmbilical;
	private boolean irritabilidade;
	private FortanelaAnterior fortenala;
	private boolean temMalFormacao;
	private String malFormacaoNome;
	private LocalDate dataNascimento;
	private LocalDateTime createOn;
	private String gestaoPartoId;
}
