package xxl.core.binary;

import xxl.core.Literal;
import xxl.core.LiteralError;
import xxl.core.LiteralInteger;
import xxl.core.exception.AsIntException;
import xxl.core.exception.UnrecognizedCoordsException;
import xxl.core.Content;

//Class that receives 2 arguments, named _arg1, _arg2, that receives the content of both arguments and adds them together if they are literal numbers, throwing an exception if any of the arguments arent literals
public class Add extends BinaryFunction {

    // Method that receives both arguments, calling the parent method from
    // BinaryFunction class and using the toString() function to split the contents
    // of the arguments
    public Add(Content arg1, Content arg2) {
        super("ADD" + "(" + arg1.asString() + "," + arg2.asString() + ")", arg1, arg2);

    }

    public Literal compute() throws UnrecognizedCoordsException {
        try {
            setValue(new LiteralInteger(getArg1().value().asInt() + getArg2().value().asInt()));
        } catch (AsIntException | NullPointerException e) {
            setValue(new LiteralError("VALUE"));
        }
        return value();
    }

    public Content copy() {
        return new Add(getArg1(), getArg2());
    }

}
