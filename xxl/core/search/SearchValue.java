package xxl.core.search;

import xxl.core.Spreadsheet;
import xxl.core.exception.UnrecognizedCoordsException;

public class SearchValue extends Search {

    public SearchValue(String value, Spreadsheet spreadsheet) {
        super(value, spreadsheet);
    }

    public boolean searchLogic(int rowIndex, int columnIndex) throws UnrecognizedCoordsException {
        String compare = getSpreadsheet().getCell(rowIndex, columnIndex).getConteudo().value().toString();
        if (compare.equals(getValue())) {
            return true;
        }

        return false;

    }
}
