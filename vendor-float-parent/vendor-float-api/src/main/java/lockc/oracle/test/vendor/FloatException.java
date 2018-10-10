package lockc.oracle.test.vendor;

/**
 * A generic exception thrown when errors occur with the float service
 *
 * @author Chris
 */
public class FloatException extends Exception {

    public FloatException(String message) {
        super(message);
    }

    public FloatException(String message, Throwable cause) {
        super(message, cause);
    }
}
