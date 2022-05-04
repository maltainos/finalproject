package mz.gov.misau.sigsmi.ws.shared.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
@ToString
@EqualsAndHashCode
public class UserLevelDTO {

	private Long id;
	private String levelId;
	private String levelName;
	private List<String> roleConfig;
	private List<RoleDTO> roles;
	private LocalDate createdOn;
	private LocalDateTime updatedOn;
}
