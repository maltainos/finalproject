package mz.gov.misau.sigsmi.ws.ui.model.response;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserLevelRest extends RepresentationModel<UserLevelRest> {

	private Long id;
	private String levelId;
	private String levelName;
	private List<RoleRest> roles;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;

	@Override
	public String toString() {
		return levelName;
	}

}
