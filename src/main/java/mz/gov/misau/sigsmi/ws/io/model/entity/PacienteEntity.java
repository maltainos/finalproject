package mz.gov.misau.sigsmi.ws.io.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mz.gov.misau.sigsmi.ws.io.model.embeddable.Filiacao;
import mz.gov.misau.sigsmi.ws.io.model.embeddable.PessoaReferenciaPaciente;
import mz.gov.misau.sigsmi.ws.io.model.embeddable.ProfissaoPaciente;
import mz.gov.misau.sigsmi.ws.io.model.enumeration.EstadoCivil;
import mz.gov.misau.sigsmi.ws.io.model.enumeration.GrupoSanguineo;

@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "pacientes")
@ToString
public class PacienteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 30, unique = true, name = "paciente_id")
	private String pacienteId;

	@Column(nullable = false, length = 30, unique = true)
	private String primeiroNome;

	@Column(nullable = false, length = 30, unique = true)
	private String sobreNome;

	private LocalDate dataNascimento;
	private byte anosIdade;

	@Embedded
	private Filiacao filiacao;

	@Enumerated(EnumType.STRING)
	private EstadoCivil estadoCivil;

	@Embedded
	private ProfissaoPaciente profissao;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente")
	private List<EnderecoEntity> enderecos;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente")
	private List<ContactoEntity> contactos;

	@Embedded
	private PessoaReferenciaPaciente pessoaReferencia;

	@Enumerated(EnumType.STRING)
	private GrupoSanguineo grupoSanguineo;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente")
	private List<GravidezEntity> gravidezes;

	// @Temporal(TemporalType.TIMESTAMP)
	private LocalDate criadoEm;

	// @Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime atualizadoEm;

}
