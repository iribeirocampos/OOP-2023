package xxl.core;

import xxl.core.exception.AsIntException;
import xxl.core.exception.AsStringException;
import xxl.core.exception.UnrecognizedCoordsException;

public interface Literal extends Content {

    public abstract int asInt() throws AsIntException;

    public String asString();

    public abstract String asStringLiteral() throws AsStringException;

    public default Literal compute() throws UnrecognizedCoordsException {
        return this;
    }
}
