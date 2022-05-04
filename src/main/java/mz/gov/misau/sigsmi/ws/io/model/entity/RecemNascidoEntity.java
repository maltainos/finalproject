package mz.gov.misau.sigsmi.ws.io.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mz.gov.misau.sigsmi.ws.io.model.enumeration.FortanelaAnterior;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "recem_nascido")
@NamedStoredProcedureQueries(

		{@NamedStoredProcedureQuery(name="getRecemNascidoAnualProcedure", procedureName="recemNascidoAnual", 
		parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "currentYearBegin", type = LocalDate.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "currentYearEnd", type = LocalDate.class)}) 
		})
public class RecemNascidoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 30)
	private String recemNascidoId;

	@Column(nullable = false)
	private boolean deathFirstWeeky;

	@Column(nullable = false, length = 500)
	private String estadoGeral;
	private String dispneia;

	@Column(nullable = false)
	private String coloracao;
	private String icteria;

	@Column(nullable = false)
	private String temperatura;

	@Column(nullable = false, columnDefinition = "Boolean default true")
	private boolean chupaBem;

	@Column(nullable = false, length = 255)
	private String estadoCotoUmbilical;

	@Column(nullable = false, columnDefinition = "Boolean default false")
	private boolean irritabilidade;

	@Enumerated(EnumType.STRING)
	private FortanelaAnterior fortenala;

	@Column(nullable = false, columnDefinition = "Boolean default false")
	private boolean temMalFormacao = false;

	private String malFormacaoNome;

	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;

	@Column(nullable = false, updatable = false)
	private LocalDate createOn;

	@ManyToOne
	@JoinColumn(name = "parto_id")
	private GestaoDePartoEntity parto;
}
