package xxl.core.intervalFunction;

import xxl.core.Content;
import xxl.core.Literal;
import xxl.core.LiteralError;
import xxl.core.LiteralInteger;
import xxl.core.Range;
import xxl.core.exception.AsIntException;
import xxl.core.exception.UnrecognizedCoordsException;
import xxl.core.Cell;

//Class that calls for an interval using the parents method to receive a set of integers and then returnign the average of the integers on said interval
public class Product extends IntervalFunction {

    public Product(Range range) {
        super("PRODUCT" + "(" + range + ")", range);
    }

    public Literal compute() throws UnrecognizedCoordsException {
        int resultado = 1;
        try {
            for (Cell c : getRange().getCells()) {
                resultado = resultado * c.getConteudo().value().asInt();
            }
            setValue(new LiteralInteger(resultado));
        } catch (AsIntException | UnrecognizedCoordsException | NullPointerException e) {
            setValue(new LiteralError("VALUE"));
        }
        return value();
    }

    public Content copy() {
        return new Product(getRange());
    }
}
