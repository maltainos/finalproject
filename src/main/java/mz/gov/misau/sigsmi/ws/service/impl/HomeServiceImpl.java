package mz.gov.misau.sigsmi.ws.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mz.gov.misau.sigsmi.ws.io.model.entity.PacienteEntity;
import mz.gov.misau.sigsmi.ws.io.model.entity.RecemNascidoEntity;
import mz.gov.misau.sigsmi.ws.io.model.enumeration.GravidezStatus;
import mz.gov.misau.sigsmi.ws.io.repository.ConsultaPosPartoRepository;
import mz.gov.misau.sigsmi.ws.io.repository.ConsultaPreNatalRepository;
import mz.gov.misau.sigsmi.ws.io.repository.GestaoPartoRepository;
import mz.gov.misau.sigsmi.ws.io.repository.GravidezRepository;
import mz.gov.misau.sigsmi.ws.io.repository.PacienteRepository;
import mz.gov.misau.sigsmi.ws.io.repository.RecemNascidoRepository;
import mz.gov.misau.sigsmi.ws.io.repository.UserRepository;
import mz.gov.misau.sigsmi.ws.service.HomeService;

@Service
public class HomeServiceImpl implements HomeService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private GravidezRepository gravidezRepository;

	@Autowired
	private GestaoPartoRepository gestaoPartoRepository;
	
	@Autowired
	private RecemNascidoRepository recemNascidoRepository;
	
	@Autowired
	private ConsultaPreNatalRepository consultaPreNatalRepository;
	
	@Autowired
	private ConsultaPosPartoRepository consultaPosPartoRepository;
	
	@Override
	public Map<String, Long> getTotalEndponts() {
		
		Map<String, Long> returnValue = new HashMap<>();
		
		Long totalPacientes = pacienteRepository.count();
		returnValue.put("pacientes", totalPacientes);
		
		Long totalUsers = userRepository.count();
		returnValue.put("Users", totalUsers);
		
		Long totalGravidezes = gravidezRepository.count();
		returnValue.put("Gravidezes", totalGravidezes);
		
		Long totalConsultaPN = consultaPreNatalRepository.count();
		returnValue.put("CPN", totalConsultaPN);
		
		Long totalGestaoParto = gestaoPartoRepository.count();
		returnValue.put("Gestao de parto", totalGestaoParto);
		
		Long totalRecemNascidos = recemNascidoRepository.count();
		returnValue.put("Recem Nascidos", totalRecemNascidos);
		
		Long totalConsultaPP = consultaPosPartoRepository.count();
		returnValue.put("CPP", totalConsultaPP);
		
		return returnValue;
	}

	@Override
	public Map<String, Integer> getRecemNascidos() {
		
		Map<String, Integer> returnValue = new HashMap<>();
		
		List<RecemNascidoEntity> recemNascidos = (List<RecemNascidoEntity>) recemNascidoRepository.findAll();
		List<RecemNascidoEntity> deathFirstWeek = new ArrayList<>();
		List<RecemNascidoEntity> noDeathFirstWeek = new ArrayList<>();
		List<RecemNascidoEntity> malFormacao = new ArrayList<>();
		List<RecemNascidoEntity> chupaBem = new ArrayList<>();
		List<RecemNascidoEntity> naoChupaBem = new ArrayList<>();
		List<RecemNascidoEntity> irritabilidae = new ArrayList<>();

		
		recemNascidos.forEach(recemNascido -> {
			if(recemNascido.isDeathFirstWeeky()) {
				deathFirstWeek.add(recemNascido);
			}else {
				noDeathFirstWeek.add(recemNascido);
			}
			
			if(recemNascido.isTemMalFormacao()) 
				malFormacao.add(recemNascido);
			
			if(recemNascido.isChupaBem())
				chupaBem.add(recemNascido);
			else
				naoChupaBem.add(recemNascido);
			
			if(recemNascido.isIrritabilidade())
				irritabilidae.add(recemNascido);

		});
		
		returnValue.put("Total", recemNascidos.size());
		returnValue.put("Vivos 1 Semana", noDeathFirstWeek.size());
		returnValue.put("Mortos 1 Semana", deathFirstWeek.size());
		returnValue.put("Mal Formacao", malFormacao.size());
		returnValue.put("Chupa bem?", chupaBem.size());
		returnValue.put("Irritabilidade", irritabilidae.size());
		returnValue.put("Nao chupa bem?", naoChupaBem.size());
		
		return returnValue;
	}

	@Override
	public Map<String, Integer> getPatients() {
		
		Map<String, Integer> returnValue = new HashMap<>();
		List<PacienteEntity> pacientes = pacienteRepository.findAll();
		List<PacienteEntity> isGravida = new ArrayList<>();
		List<PacienteEntity> isSaudavel = new ArrayList<>();
		List<PacienteEntity> isAborto = new ArrayList<>();
		List<PacienteEntity> isParto = new ArrayList<>();
		List<PacienteEntity> isMenor = new ArrayList<>();
		List<PacienteEntity> isMaior = new ArrayList<>();
		
		pacientes.forEach(paciente -> {
			paciente.getGravidezes().forEach(gravidez -> {
				isGravida.add(paciente);
				if(gravidez.getGravidezStatus().equals(GravidezStatus.SAUDAVEL)) {
					isSaudavel.add(paciente);
				}
				if(gravidez.getGravidezStatus().equals(GravidezStatus.ABORTADO)) {
					isAborto.add(paciente);
				}
				if(gravidez.getGravidezStatus().equals(GravidezStatus.PARTO)) {
					isParto.add(paciente);
				}
			});
			
			if(LocalDate.now().minusYears(paciente.getDataNascimento().getYear()).getYear() >= 18) {
				isMaior.add(paciente);
			}else {
				isMenor.add(paciente);
			}
		});
		
		returnValue.put("Total", pacientes.size());
		returnValue.put("Maiores de Idade", isMaior.size());
		returnValue.put("Menores de Idade", isMenor.size());
		returnValue.put("Gravidas", isGravida.size());
		returnValue.put("Saudavel", isSaudavel.size());
		returnValue.put("Aborto", isAborto.size());
		returnValue.put("Parto", isParto.size());
		
		return returnValue;
	}

	@Override
	public Map<String, Integer> getGravidez() {
		Map<String, Integer> returnValue = new HashMap<>();
		return returnValue;
	}

	//@Override
	//public List<UserEntity> getChartEndpoitsLastWeeky() {
		
		
		//userRepository.getLastWeekReport();
		
		//userRepository.selectMultipleValueAndMapToDTO();
		//userRepository.selectionObject();
		
		//userRepository.selectMultipleValues();
		//userRepository.selectionField();
		
		//return userRepository.countLastWeekCreateUsers();
		//System.out.println(returnValue);
		//return new HashMap<>();
	//}

}
