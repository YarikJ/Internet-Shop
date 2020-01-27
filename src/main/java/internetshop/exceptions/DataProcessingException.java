package internetshop.exceptions;

public class DataProcessingException extends Exception {
    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataProcessingException(Throwable cause) {
        super(cause);
    }

    public DataProcessingException(String message) {
        super(message);
    }
}
