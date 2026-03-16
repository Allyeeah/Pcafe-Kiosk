package exception;
//해당번호가 없을때 예외처리
public class InvalidMenuException extends RuntimeException {
    public InvalidMenuException(String message) {
        super(message);
    }
}