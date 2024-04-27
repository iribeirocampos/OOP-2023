package xxl.core.search;

import xxl.core.Spreadsheet;
import xxl.core.Function;
import xxl.core.exception.UnrecognizedCoordsException;

public class SearchFunction extends Search {

    public SearchFunction(String value, Spreadsheet spreadsheet) {
        super(value, spreadsheet);
    }

    public boolean searchLogic(int rowIndex, int columnIndex) throws UnrecognizedCoordsException {
        // String compare = getSpreadsheet().getCell(rowIndex,
        // columnIndex).getConteudo().toString();
        try {
            Function function = (Function) getSpreadsheet().getCell(rowIndex, columnIndex).getConteudo();
            if (function.getClass().getSimpleName().toUpperCase().contains(getValue().toUpperCase())) {
                return true;
            }
        } catch (ClassCastException e) {
            // Do nothing
        }

        return false;

    }
}
