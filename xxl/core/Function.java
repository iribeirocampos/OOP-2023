package xxl.core;

import xxl.core.exception.UnrecognizedCoordsException;

public abstract class Function implements Content {
    private String _functionStr;

    public Function(String string) {
        _functionStr = string;
    }

    public String toString() {
        try {
            return "" + compute() + "=" + _functionStr;
        } catch (UnrecognizedCoordsException e) {
            return "#VALUE =" + _functionStr;
        }
    }

    public abstract Literal compute() throws UnrecognizedCoordsException;

    public void subscribe(Content observer) {
        // Do nothing
    }
}
