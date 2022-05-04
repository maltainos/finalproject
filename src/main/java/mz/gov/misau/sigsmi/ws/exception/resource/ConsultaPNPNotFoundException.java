package mz.gov.misau.sigsmi.ws.exception.resource;

import lombok.Getter;

public class ConsultaPNPNotFoundException extends RuntimeException {

	@Getter
	private String message;
	private static final long serialVersionUID = 1L;

	public ConsultaPNPNotFoundException(String message) {
		super(message);
		this.message = message;
	}
}
