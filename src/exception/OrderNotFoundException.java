package exception;

public class OrderNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6271905671039044986L;
	
	public OrderNotFoundException() {
		this("찾으시는 주문 내역이 존재하지 않습니다.");
	}
	public OrderNotFoundException(String message) {
		super(message);
	}
}
