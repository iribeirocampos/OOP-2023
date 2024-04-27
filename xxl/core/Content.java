package xxl.core;

import java.io.Serializable;

import xxl.core.exception.UnrecognizedCoordsException;

public interface Content extends Serializable {

    public abstract String toString();

    public abstract String asString();

    public abstract Content copy();

    public abstract void subscribe(Content observer);

    public abstract Literal value() throws UnrecognizedCoordsException;

    public abstract Content compute() throws UnrecognizedCoordsException;
}
