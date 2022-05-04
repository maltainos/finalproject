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
public class GestaoPartoRest extends RepresentationModel<GestaoPartoRest> {

	private String gestaoPartoId;
	private boolean nadoVivo;
	private boolean partoCesariana;
	private boolean partoComVentose;
	private boolean remocaoManualDaPlaceta;
	private boolean complicacoesHemorragicas;
	private LocalDate dataParto;
	private LocalDateTime createOn;

}
