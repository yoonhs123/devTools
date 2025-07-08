package yoon.hyeon.sang.exception;

// API 예외 클래스
public class ApiException extends RuntimeException {

    public ApiException(String message, Throwable cause) {}
    public ApiException(String message) { super(message); }

    /// Content-Type 설정이 잘못됨
    public static class InvalidContentType extends ApiException {
        public InvalidContentType(String message) {
            super(message);
        }
        public InvalidContentType(Throwable cause) {
            super("Content-Type이 잘못되었습니다", cause);
        }
        public InvalidContentType(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
