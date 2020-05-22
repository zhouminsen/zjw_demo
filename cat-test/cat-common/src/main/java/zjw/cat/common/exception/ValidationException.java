package zjw.cat.common.exception;

/**
 * <p>
 * 自定义异常
 * </p>
 *
 * @author clive
 * @since 2019-11-24 14:30
 */
public class ValidationException extends RuntimeException {


    private static final long serialVersionUID = 4020598274591037073L;

    public ValidationException(String msg) {
        super(msg);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
