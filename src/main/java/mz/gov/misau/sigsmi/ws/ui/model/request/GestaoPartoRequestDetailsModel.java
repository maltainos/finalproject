package mz.gov.misau.sigsmi.ws.ui.model.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

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
public class GestaoPartoRequestDetailsModel {

	@NotBlank
	private boolean nadoVivo;

	@NotBlank
	private boolean partoCesariana;

	@NotBlank
	private boolean partoComVentose;

	@NotBlank
	private boolean remocaoManualDaPlaceta;

	@NotBlank
	private boolean complicacoesHemorragicas;

	@NotBlank
	private LocalDate dataParto;
}
