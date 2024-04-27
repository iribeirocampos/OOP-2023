package xxl.app.exception;

import pt.tecnico.uilib.menus.CommandException;

public class InvalidTypeEntryException extends CommandException {
    private final String _entry;

    /** @param range */
    public InvalidTypeEntryException(String entrada) {
        super("A entrada '" + entrada + "' é inválida.");
        _entry = entrada;
    }

    public final String getInvalidRange() {
        return _entry;
    }
}
