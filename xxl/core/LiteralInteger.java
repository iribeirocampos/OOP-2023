package xxl.core;

import xxl.core.exception.AsStringException;

public class LiteralInteger implements Literal {
    private int _value;

    public LiteralInteger(int number) {
        _value = number;
    }

    public int asInt() {
        return _value;
    }

    public String asString() {
        return "" + _value;
    }

    public String asStringLiteral() throws AsStringException {
        throw new AsStringException();
    }

    public String toString() {
        return "" + _value;
    }

    public Literal value() {
        return this;
    }

    public Content copy() {
        return new LiteralInteger(_value);
    }

    public void subscribe(Content observer) {
        // Do nothing
    }
}
