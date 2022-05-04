package mz.gov.misau.sigsmi.ws.ui.model.response;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RoleRest extends RepresentationModel<RoleRest> {

	private String roleId;
	private String roleName;
	private String roleDescription;

	@Override
	public String toString() {
		return roleName;
	}

}
