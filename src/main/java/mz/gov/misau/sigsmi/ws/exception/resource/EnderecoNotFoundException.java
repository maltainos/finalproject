package mz.gov.misau.sigsmi.ws.exception.resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;

	public EnderecoNotFoundException(String message) {
		super();
		this.message = message;
	}
}
