package mz.gov.misau.sigsmi.ws.service;

import java.util.List;

import javax.transaction.Transactional;

import mz.gov.misau.sigsmi.ws.io.model.entity.PacienteEntity;
import mz.gov.misau.sigsmi.ws.shared.dto.ConsultaPreNatalDTO;
import mz.gov.misau.sigsmi.ws.shared.dto.GravidezDTO;

@Transactional
public interface GravidezService {

	// This Endpoint
	public List<GravidezDTO> findAll(int page, int limit);

	public GravidezDTO findByGravidezId(String gravidezId);

	public GravidezDTO create(GravidezDTO gravidezDTO);

	public GravidezDTO update(GravidezDTO gravidezDTO, String gravidezId);

	public List<GravidezDTO> findGravidezByPaciente(PacienteEntity paciente);

	// Relaction Endpoint
	public ConsultaPreNatalDTO doingConsulta(String gravidezId, ConsultaPreNatalDTO cpnDTO);
}
