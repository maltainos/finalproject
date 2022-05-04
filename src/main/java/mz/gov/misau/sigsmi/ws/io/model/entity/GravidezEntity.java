
package mz.gov.misau.sigsmi.ws.io.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "gravidezes")
public class GravidezEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 30)
	private String gravidezId;

	private LocalDate dataEngravida;

	@Column(nullable = false)
	private LocalDate dataParto;

	@Enumerated(EnumType.STRING)
	private GravidezStatus gravidezStatus;

	@Column(nullable = false, columnDefinition = "Boolean Default false")
	private boolean enable = false;

	@Enumerated(EnumType.STRING)
	private AbortoEnumeration aborto = AbortoEnumeration.SEM_ABORTO;

	private String localParto;

	@Column(nullable = false)
	private LocalDate dataRegistro;
	private LocalDateTime dataAtualizacao;

	@ManyToOne
	@JoinColumn(name = "paciente_id")
	private PacienteEntity paciente;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "gravidez")
	private List<ConsultaPreNatalEntity> consultasPreNatal;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "gravidez")
	private GestaoDePartoEntity gestaoParto;
}
