package exception;

public class SearchWrongException extends Exception{
	private static final long serialVersionUID = 1L;
	public SearchWrongException() {}
	public SearchWrongException(String message) {
		super(message);
	}
}
