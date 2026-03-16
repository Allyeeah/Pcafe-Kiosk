package exception;

public class CategoryException extends RuntimeException{

    public CategoryException() {
        super("카테고리 추가 실패했습니다. 다시 시도해 주세요.");
    }


    public CategoryException(String message) {
        super(message);
    }
}
