package mz.gov.misau.sigsmi.ws.service;

import java.util.Map;

public interface HomeService {
	
	Map<String, Long> getTotalEndponts();
	Map<String, Integer> getRecemNascidos();
	Map<String, Integer> getPatients();
	Map<String, Integer> getGravidez();
	//List<UserEntity> getChartEndpoitsLastWeeky();

}
