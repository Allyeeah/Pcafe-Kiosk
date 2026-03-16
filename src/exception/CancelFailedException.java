package exception;

public class CancelFailedException extends RuntimeException {

	private static final long serialVersionUID = 2857420034662706300L;

	public CancelFailedException() {
		this("주문 취소에 실패했습니다. 다시 시도해 주세요.");
	}
	public CancelFailedException(String message) {
		super(message);
	}
}
