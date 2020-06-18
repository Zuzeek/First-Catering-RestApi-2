package ag.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super("Employee card not found, please register card");
	}
	
	public UserNotFoundException(String message) {
		super(message);
	}
}

