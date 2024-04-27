package xxl.core;

import xxl.core.exception.UnrecognizedCoordsException;

public class Reference implements Content {
    private int _row;
    private int _column;
    private Spreadsheet _spreadsheet;
    private Literal _value;

    public Reference(int row, int column, Spreadsheet spreadsheet) throws UnrecognizedCoordsException {
        _row = row;
        _column = column;
        _spreadsheet = spreadsheet;
        _spreadsheet.getCell(_row, _column).subscribe(new Observer(this));
    }

    public String toString() {
        try {
            return "" + value().toString() + "=" + _row + ";" + _column;
        } catch (UnrecognizedCoordsException | NullPointerException e) {
            return "#VALUE =" + _row + ";" + _column;
        }
    }

    public String asString() {
        return "" + _row + ";" + _column;
    }

    public Literal value() throws UnrecognizedCoordsException {
        if (_value == null)
            compute();
        return _value;
    }

    public Content copy() {
        return this;
    }

    public Literal compute() throws UnrecognizedCoordsException {
        try {
            _value = _spreadsheet.getCell(_row, _column).getConteudo().value();
        } catch (NullPointerException e) {
            _value = new LiteralError("VALUE");
        }
        return _value;

    }

    public void subscribe(Content observer) {
        try {
            _spreadsheet.getCell(_row, _column).subscribe(new Observer(observer));
        } catch (UnrecognizedCoordsException e) {
            // Do nothing, does not happen
        }
    }
}
