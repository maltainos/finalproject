package mz.gov.misau.sigsmi.ws.ui.model.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mz.gov.misau.sigsmi.ws.io.model.enumeration.FortanelaAnterior;

@Getter
@Setter
@ToString
public class RecemNascidoRequestDetailsModel {

	@NotBlank
	@Size(min = 3, max = 100)
	private String estadoGeral;

	@NotBlank
	@Size(min = 3, max = 100)
	private String dispneia;

	@NotBlank
	@Size(min = 3, max = 20)
	private String coloracao;

	@NotBlank
	@Size(min = 3, max = 100)
	private String icteria;

	@NotBlank
	@Size(min = 2, max = 6)
	private String temperatura;

	private boolean chupaBem = true;
	
	private boolean deathFirstWeeky = false;

	@NotBlank
	@Size(min = 15, max = 100)
	private String estadoCotoUmbilical;

	private boolean irritabilidade = false;

	@NotBlank
	private FortanelaAnterior fortenala;

	private boolean temMalFormacao = false;
	private String malFormacaoNome;

	@NotBlank
	@Size(min = 10, message = "Data no formato YYYY-MM-DD")
	private LocalDate dataNascimento;

}
