package mz.gov.misau.sigsmi.ws.io.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "gestao_partos")
public class GestaoDePartoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 30)
	private String gestaoPartoId;

	@Column(nullable = false, columnDefinition = "Boolean default true")
	private boolean nadoVivo;

	@Column(nullable = false, columnDefinition = "Boolean default false")
	private boolean partoCesariana;

	@Column(nullable = false, columnDefinition = "Boolean default false")
	private boolean partoComVentose;

	@Column(nullable = false, columnDefinition = "Boolean default false")
	private boolean remocaoManualDaPlaceta;

	@Column(nullable = false, columnDefinition = "Boolean default false")
	private boolean complicacoesHemorragicas;

	@Column(nullable = false)
	private LocalDate dataParto;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createOn;

	@OneToOne
	@JoinColumn(name = "gravidez_id")
	private GravidezEntity gravidez;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parto")
	private List<RecemNascidoEntity> recemNascidos;
}
