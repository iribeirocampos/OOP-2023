package xxl.core.binary;

import xxl.core.Content;
import xxl.core.Function;
import xxl.core.Literal;
import xxl.core.exception.UnrecognizedCoordsException;

//BynaryFunction is a class that receives a String and uses the method from the parent class Function to evaluate what type of function it is and processes it acordingly
public abstract class BinaryFunction extends Function {
    private Content _arg1;
    private Content _arg2;
    private Literal _value;

    // Method that calls the Parent class method to receive a type of function
    public BinaryFunction(String function, Content arg1, Content arg2) {
        super(function);
        _arg1 = arg1;
        _arg2 = arg2;
        arg1.subscribe(this);
        arg2.subscribe(this);
    }

    public Content getArg1() {
        return _arg1;
    }

    public Content getArg2() {
        return _arg2;
    }

    // Method that calls the function asString and returns the result value as a
    // String
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
