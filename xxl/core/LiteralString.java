package xxl.core;

import xxl.core.exception.AsIntException;

public class LiteralString implements Literal {
    private String _value;

    public LiteralString(String text) {
        _value = text;
    }

    public int asInt() throws AsIntException {
        throw new AsIntException();
    }

    public String asStringLiteral() {
        return "" + _value;
    }

    public String asString() {
        return "" + _value;
    }

    public String toString() {
        return "'" + _value;
    }

    public Literal value() {
        return this;
    }

    public Content copy() {
        return new LiteralString(_value);
    }

    public void subscribe(Content observer) {
        // Do nothing
    }

}
