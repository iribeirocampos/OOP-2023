package xxl.core.intervalFunction;

import xxl.core.Literal;
import xxl.core.LiteralError;
import xxl.core.LiteralInteger;
import xxl.core.Range;
import xxl.core.Content;
import xxl.core.exception.AsIntException;
import xxl.core.exception.UnrecognizedCoordsException;
import xxl.core.Cell;

//Class that calls for an interval using the parents method to receive a set of integers and then returnign the average of the integers on said interval
public class Average extends IntervalFunction {

    public Average(Range range) {
        super("AVERAGE" + "(" + range + ")", range);
    }

    public Literal compute() throws UnrecognizedCoordsException {
        int resultado = 0;
        int values = getRange().getCells().length;
        try {
            for (Cell c : getRange().getCells()) {
                resultado += c.getConteudo().value().asInt();
            }
            setValue(new LiteralInteger(resultado / values));
        } catch (AsIntException | UnrecognizedCoordsException | NullPointerException e) {
            setValue(new LiteralError("VALUE"));
        }
        return value();
    }

    public Content copy() {
        return new Average(getRange());
    }
}
