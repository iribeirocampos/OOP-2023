package xxl.core.intervalFunction;

import xxl.core.Literal;
import xxl.core.Content;
import xxl.core.LiteralString;
import xxl.core.Range;
import xxl.core.exception.UnrecognizedCoordsException;
import xxl.core.Cell;

public class Concat extends IntervalFunction {
    public Concat(Range range) {
        super("CONCAT" + "(" + range + ")", range);
    }

    public Literal compute() throws UnrecognizedCoordsException {
        String resultado = "";
        for (Cell c : getRange().getCells()) {
            try {
                if (c.getConteudo().toString().charAt(0) == '\'') {
                    resultado = resultado + c.getConteudo().value().asString();
                }
            } catch (NullPointerException | UnrecognizedCoordsException e) {
                // do nothing
            }
        }
        setValue(new LiteralString(resultado));
        return value();
    }

    public Content copy() {
        return new Concat(getRange());
    }
}
