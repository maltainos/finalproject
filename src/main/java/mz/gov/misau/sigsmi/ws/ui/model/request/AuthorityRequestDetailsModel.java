package mz.gov.misau.sigsmi.ws.ui.model.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorityRequestDetailsModel {

	@NotNull
	private List<String> authorities;

}
