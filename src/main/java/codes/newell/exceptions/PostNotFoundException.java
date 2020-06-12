package codes.newell.exceptions;

public class PostNotFoundException extends RuntimeException {

	public PostNotFoundException(String message, Exception cause) {
		super(message, cause);
	}

	public PostNotFoundException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;
}
