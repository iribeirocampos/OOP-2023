package xxl.core.binary;

import xxl.core.Literal;
import xxl.core.LiteralError;
import xxl.core.LiteralInteger;
import xxl.core.exception.AsIntException;
import xxl.core.exception.UnrecognizedCoordsException;
import xxl.core.Content;

public class Sub extends BinaryFunction {

    public Sub(Content arg1, Content arg2) {
        super("SUB" + "(" + arg1.asString() + "," + arg2.asString() + ")", arg1, arg2);

    }

    public Literal compute() throws UnrecognizedCoordsException {
        try {
            setValue(new LiteralInteger(getArg1().value().asInt() - getArg2().value().asInt()));
        } catch (AsIntException | NullPointerException e) {
            setValue(new LiteralError("VALUE"));
        }
        return value();
    }

    public Content copy() {
        return new Sub(getArg1(), getArg2());
    }

}
