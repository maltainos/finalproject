package mz.gov.misau.sigsmi.ws.service;

import java.util.List;

import mz.gov.misau.sigsmi.ws.shared.dto.EnderecoDTO;
import mz.gov.misau.sigsmi.ws.shared.dto.PacienteDTO;
import mz.gov.misau.sigsmi.ws.ui.model.response.history.PacienteHistorialClinica;

public interface PacienteService {

	public List<PacienteDTO> search();

	public PacienteDTO create(PacienteDTO patientDTO);

	public EnderecoDTO create(EnderecoDTO enderecoDTO, String pacienteId);

	public PacienteDTO update(PacienteDTO patientDTO, String patientId);

	public EnderecoDTO update(EnderecoDTO enderecoDTO, String pacienteId, String enderecoId);

	public PacienteHistorialClinica getClinicHistory(String pacienteId);
}