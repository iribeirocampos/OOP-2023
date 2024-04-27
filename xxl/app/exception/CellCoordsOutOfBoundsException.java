package xxl.app.exception;

import pt.tecnico.uilib.menus.CommandException;

/** Exception when coordenates are given don´t exist. */
public class CellCoordsOutOfBoundsException extends CommandException {
    private final int _row;
    private final int _colunm;

    /** @param row */
    /** @param column */
    public CellCoordsOutOfBoundsException(int row, int column) {
        super("As coordenadas '" + row + ":" + column + "' são inválidas.");
        _row = row;
        _colunm = column;
    }

    public final int getRow() {
        return _row;
    }

    public final int getColumn() {
        return _colunm;
    }

}