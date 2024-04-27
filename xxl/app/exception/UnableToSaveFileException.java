package xxl.app.exception;

import pt.tecnico.uilib.menus.CommandException;

public class UnableToSaveFileException extends CommandException {
    private final String _entry;

    /** @param fileNAme */
    public UnableToSaveFileException(String fileName) {
        super("Não foi possivel guardar o ficheiro " + fileName);
        _entry = fileName;
    }

    public final String getInvalidFile() {
        return _entry;
    }
}
