package exception;

public class UpdateUserInfoException extends RuntimeException{
	 public UpdateUserInfoException() {
	        super("사용자 정보 수정에 실패했습니다. 다시 시도해 주세요.");
	    }

	   
	    public UpdateUserInfoException(String message) {
	        super(message);
	    }
}
