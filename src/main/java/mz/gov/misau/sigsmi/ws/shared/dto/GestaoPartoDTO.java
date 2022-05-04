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
@EqualsAndHashCode(of = "id")
@ToString(exclude = "id")
public class GestaoPartoDTO {

	private Long id;
	private String gestaoPartoId;
	private boolean nadoVivo;
	private boolean partoCesariana;
	private boolean partoComVentose;
	private boolean remocaoManualDaPlaceta;
	private boolean complicacoesHemorragicas;
	private LocalDate dataParto;
	private LocalDate createOn;
	private GravidezDTO gravidez;
}
