package mz.gov.misau.sigsmi.ws.service.exporter.kids;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class KidsPdfExporter {
	
	public void exporter(HttpServletResponse response) {
		try {
			
			String filePath = "../sigsmi.misau/src/main/resources/First_report.jrxml";
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			
			Student student1 = new Student(1, "Jonh", "Doe", "Macurungo", "Beira");
			Student student2 = new Student(1, "Jonhson", "Doe", "Macurungo", "Sofala");
			
			parameters.put("studentName", "Jonh Doe");
			
			List<Student> listStudent = new ArrayList<Student>();
			listStudent.add(student2);
			listStudent.add(student1);
			
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listStudent);
			
			JasperReport report =  JasperCompileManager.compileReport(filePath);
			
			@SuppressWarnings("unused")
			JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);
			
			//JasperExportManager.exportReportToPdfFile(print, filePath);
			
			//byte[] byteArray = JasperExportManager.exportReportToPdf(print);
			 
			
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
			String timestamp = dateFormatter.format(new Date());
			String fileName = "students" + timestamp + ".pdf";

			//HttpHeaders headers = new HttpHeaders();
			
		//	headers.setContentType(MediaType.APPLICATION_PDF);
			//headers.setContentDispositionFormData("attachment", fileName);
			response.setContentType(MediaType.APPLICATION_PDF.getType());
			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename="+fileName;
			
			//response.
			response.addHeader(headerKey, headerValue);
			
			System.out.println("Report Create");
			 
		}catch(Exception ex) {
			System.out.println("Exception While creating report "+ex.getMessage());
		}
	}

}
