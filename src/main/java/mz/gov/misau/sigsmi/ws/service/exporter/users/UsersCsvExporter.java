package mz.gov.misau.sigsmi.ws.service.exporter.users;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import mz.gov.misau.sigsmi.ws.service.exporter.AbstractExporter;
import mz.gov.misau.sigsmi.ws.ui.model.response.UserRest;

public class UsersCsvExporter extends AbstractExporter {

	public void export(List<UserRest> users, HttpServletResponse response) throws IOException {

		super.setResponseHeader(response, "text/csv", ".csv", "users_");

		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

		String[] csvHeader = { "E-mail", "Nome", "Apelido", "Niveis Acesso", "Ativo", "Criado Em", "Actualizado Em" };
		String[] fieldMapping = { "email", "firstName", "lastName", "groups", "status", "createdOn", "updatedOn" };
		csvWriter.writeHeader(csvHeader);

		for (UserRest user : users)
			csvWriter.write(user, fieldMapping);

		csvWriter.close();
	}

}
