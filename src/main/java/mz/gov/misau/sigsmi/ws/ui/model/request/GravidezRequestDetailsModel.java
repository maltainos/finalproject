package mz.gov.misau.sigsmi.ws.ui.model.request;

import java.time.LocalDate;

import javax.validation.constraints.Size;

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
public class GravidezRequestDetailsModel {

	private LocalDate dataEngravida;
	private LocalDate dataParto;
	private GravidezStatus gravidezStatus = GravidezStatus.SAUDAVEL;
	private AbortoEnumeration aborto = AbortoEnumeration.SEM_ABORTO;

	@Size(min = 6, max = 75)
	private String localParto;

}
