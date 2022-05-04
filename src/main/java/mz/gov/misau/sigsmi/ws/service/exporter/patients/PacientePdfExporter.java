package mz.gov.misau.sigsmi.ws.service.exporter.patients;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import mz.gov.misau.sigsmi.ws.ui.model.response.history.PacienteHistorialClinica;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class PacientePdfExporter {

	public byte[] export(PacienteHistorialClinica historialClinica) {

		try {
			String pathJRXML = new String("../sigsmi.misau/src/main/resources/First_report.jrxml");

			Map<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("nomeCompleto",
			historialClinica.getPrimeiroNome()+" "+historialClinica.getSobreNome());
			parameters.put("dataNascimento", historialClinica.getDataNascimento());
			parameters.put("idade", historialClinica.getAnosIdade()+ " Anos");
			parameters.put("telefone", historialClinica.getContactos().size() > 0 ?
			historialClinica.getContactos().get(0).getNumeroTelefone() : "");
			parameters.put("morada", historialClinica.getEnderecos().size() > 0 ?
		 	historialClinica.getEnderecos().get(0).getBairro() : "");
		 	parameters.put("pessoaEmergencia",
			historialClinica.getPessoaReferencia().getNomePessoaReferencia());
			parameters.put("telefoneEmergencia",
			historialClinica.getPessoaReferencia().getTelefoneEmergencia());
			
			// List<?> list = new ArrayList<>();
			// JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

			JasperReport report = JasperCompileManager.compileReport(pathJRXML);

			JasperPrint print = JasperFillManager.fillReport(report, parameters);
			
			LocalDate data = LocalDate.now();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			
			String fileName = "pregnancy-history"+ dateFormat.format(data) + ".pdf";

			JasperExportManager.exportReportToPdfFile(print, "C:\\Users\\Maltainos-PC\\Documents"+fileName);

			byte[] returnValue = JasperExportManager.exportReportToPdf(print);

			System.out.println("PDF CREATE...s");

			// KidsPdfExporter exporterKidsPdfExporter = new KidsPdfExporter();

			// exporterKidsPdfExporter.exporter(response);

			return returnValue;

		} catch (JRException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
		// return returnValue;
	}

}
