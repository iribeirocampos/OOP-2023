package xxl.core.exception;

/**
 * Represents an error occurred during the serialization/desserialization
 * process of the apllication's state:
 * - The specified file does not exist
 * - There is an error while processing this file using the serialization
 * mechanism
 * of Java.
 */
public class UnrecognizedFunctionException extends Exception {
    /** The requested function name. */
    private String _function;

    /**
     * @param filename
     */
    public UnrecognizedFunctionException(String function) {
        super("Não foi possivel reconhecer a função: " + function);
        _function = function;
    }

    /**
     * @return the requested Function
     */
    public String getFilename() {
        return _function;
    }
}
