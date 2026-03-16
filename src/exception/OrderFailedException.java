package exception;

public class OrderFailedException extends RuntimeException {

	private static final long serialVersionUID = -1190246280255944825L;

	public OrderFailedException() {
		this("주문에 실패했습니다. 다시 시도해 주세요.");
	}
	public OrderFailedException(String message) {
		super(message);
	}
}
