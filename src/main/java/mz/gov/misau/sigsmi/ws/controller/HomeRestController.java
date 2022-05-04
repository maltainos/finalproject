package mz.gov.misau.sigsmi.ws.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import mz.gov.misau.sigsmi.ws.service.impl.HomeServiceImpl;

@RestController
@RequestMapping(path = "/dashboard")
public class HomeRestController {
	
	@Autowired
	private HomeServiceImpl homeService;
	
	@GetMapping(path = "chart-endpoints", produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseStatus( HttpStatus.OK )
	public Map<String, Long> chartEndpoints(){
		
		Map<String, Long> returnValue = homeService.getTotalEndponts();
		
		return returnValue;
	}
	/*
	@GetMapping(path = "chart-endpoints/last-weeky", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseStatus( HttpStatus.OK )
	public List<UserEntity> chartEndpointsLastWeeky(){
		
		//System.out.println(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0));
		return homeService.getChartEndpoitsLastWeeky();
		
		//return returnValue;
	}*/
	
	@GetMapping(path = "chart-kids", produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseStatus( HttpStatus.OK )
	public Map<String, Integer> chartRecemNascido(){
		Map<String, Integer> returnValue = homeService.getRecemNascidos();
		
		return returnValue;
	}
	
	@GetMapping(path = "chart-patients", produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseStatus( HttpStatus.OK )
	public Map<String, Integer> chartPatients(){
		Map<String, Integer> returnValue = homeService.getPatients();
		return returnValue;
	}

}
