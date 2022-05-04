package mz.gov.misau.sigsmi.ws.ui.model.response.history;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GestaoPartoHistorial {

	private boolean nadoVivo;
	private boolean partoCesariana;
	private boolean partoComVentose;
	private boolean remocaoManualDaPlaceta;
	private boolean complicacoesHemorragicas;
	private LocalDate dataParto;
}
