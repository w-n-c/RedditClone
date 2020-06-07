package codes.newell.exceptions;

public class SpringRedditException extends RuntimeException {
	
	public SpringRedditException(String message, Exception cause) {
		super(message, cause);
	}

	public SpringRedditException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;
}
