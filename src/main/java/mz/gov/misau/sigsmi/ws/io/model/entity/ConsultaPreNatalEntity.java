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
@Table(name = "consultas_pre_natais")
public class ConsultaPreNatalEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(updatable = false)
	private String codigoPvt;

	@Column(nullable = false, unique = true, length = 30)
	private String consultaPreNatalId;

	@Column(columnDefinition = "text", updatable = false)
	private String observacoes;

	@Column(columnDefinition = "text", updatable = false)
	private String recomendacoes;

	private Float pesoMae;

	@Column(nullable = false)
	private LocalDate dataConsulta;

	private LocalDate dataProvavelDeParto;

	@ManyToOne
	@JoinColumn(name = "gravidez_id")
	private GravidezEntity gravidez;
	
	//toString

}
