package xxl.core;

import xxl.core.exception.AsIntException;

public class LiteralError implements Literal {
    private String _value;

    public LiteralError(String text) {
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
        return "#" + _value;
    }

    public Literal value() {
        return this;
    }

    public Content copy() {
        return new LiteralError(_value);
    }

    public void subscribe(Content observer) {
        // Do nothing
    }

}
