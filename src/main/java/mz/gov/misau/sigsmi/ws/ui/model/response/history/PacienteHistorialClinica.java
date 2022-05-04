package mz.gov.misau.sigsmi.ws.ui.model.response.history;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mz.gov.misau.sigsmi.ws.ui.model.response.ContactoRest;
import mz.gov.misau.sigsmi.ws.ui.model.response.EnderecoRest;
import mz.gov.misau.sigsmi.ws.ui.model.response.EstadoCivilRest;
import mz.gov.misau.sigsmi.ws.ui.model.response.FiliacaoRest;
import mz.gov.misau.sigsmi.ws.ui.model.response.GrupoSanguineoRest;
import mz.gov.misau.sigsmi.ws.ui.model.response.PessoaReferenciaRest;
import mz.gov.misau.sigsmi.ws.ui.model.response.ProfissaoRest;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PacienteHistorialClinica {
	
	private String primeiroNome;
	private String sobreNome;
	private LocalDate dataNascimento;
	private byte anosIdade;
	private FiliacaoRest filiacao;
	private EstadoCivilRest estadoCivil;
	private ProfissaoRest profissao;
	private List<EnderecoRest> enderecos;
	private List<ContactoRest> contactos;
	private PessoaReferenciaRest pessoaReferencia;
	private GrupoSanguineoRest grupoSanguineo;
	private List<GravidezRestHistoria> gravidezes;

}
