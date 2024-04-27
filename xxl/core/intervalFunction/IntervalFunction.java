package xxl.core.intervalFunction;

import xxl.core.Function;
import xxl.core.Range;
import xxl.core.exception.UnrecognizedCoordsException;
import xxl.core.Literal;

public abstract class IntervalFunction extends Function {
    private Range _args;
    private Literal _value;

    public IntervalFunction(String function, Range range) {
        super(function);
        _args = range;
        range.subscribe(this);
    }

    public Range getRange() {
        return _args;
    }

    public String asString() {
        return super.toString();
    }

    public void setValue(Literal value) {
        _value = value;
    }

    public Literal value() throws UnrecognizedCoordsException {
        if (_value == null)
            compute();
        return _value;
    }

    public abstract Literal compute() throws UnrecognizedCoordsException;
}
