package ltd.beihu.leaf.core.snowflake.exception;

public class ClockGoBackException extends RuntimeException {
    public ClockGoBackException(String message) {
        super(message);
    }
}
