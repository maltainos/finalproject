package mz.gov.misau.sigsmi.ws.io.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Entity
@Table(name = "consulta_pos_parto")
public class ConsultaPosPartoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(updatable = false)
	private String codigoPvt;

	@Column(nullable = false, unique = true, length = 30)
	private String consultaPosPartoId;

	@Column(columnDefinition = "text", updatable = false)
	private String observacao;

	@Column(columnDefinition = "text", updatable = false)
	private String recomendacao;

	@Column(nullable = false)
	private float pesoMae;

	private LocalDate dataConsulta;

	private boolean tomouVitaminaA;

	private boolean tomouSalFerroso;

	@ManyToOne
	@JoinColumn(name = "gravidez_id")
	private GravidezEntity gravidez;
}
