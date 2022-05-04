package mz.gov.misau.sigsmi.ws.shared.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mz.gov.misau.sigsmi.ws.io.model.enumeration.FortanelaAnterior;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RecemNascidoDTO {

	private Long id;
	private String recemNascidoId;
	private String estadoGeral;
	private String dispneia;
	private String coloracao;
	private String icteria;
	private String temperatura;
	private boolean chupaBem;
	private boolean deathFirstWeeky;
	private String estadoCotoUmbilical;
	private boolean irritabilidade;
	private FortanelaAnterior fortenala;
	private boolean temMalFormacao;
	private String malFormacaoNome;
	private LocalDate dataNascimento;
	private LocalDate createOn;
	private GestaoPartoDTO parto;

}
