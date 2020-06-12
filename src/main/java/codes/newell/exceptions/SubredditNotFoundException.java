package codes.newell.exceptions;

public class SubredditNotFoundException extends RuntimeException {
	
	public SubredditNotFoundException(String message, Exception cause) {
		super(message, cause);
	}

	public SubredditNotFoundException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;
}
