package mz.gov.misau.sigsmi.ws.ui.model.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class OperationStatusModel {

	private RequestOperationName operationName;
	private RequestOperationStatus operationStatus;
}
