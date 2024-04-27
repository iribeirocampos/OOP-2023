package xxl.core.search;

import xxl.core.Spreadsheet;
import xxl.core.exception.UnrecognizedCoordsException;

import java.util.ArrayList;
import java.util.List;

import xxl.core.Cell;

public abstract class Search {
    private String _valueSearch;
    private Spreadsheet _spreadsheet;

    public Search(String value, Spreadsheet spreadsheet) {
        _valueSearch = value;
        _spreadsheet = spreadsheet;
    }

    public List<Cell> spreadsheetIterate() throws UnrecognizedCoordsException {
        List<Cell> resultado = new ArrayList<Cell>();
        for (int rowIndex = 1; rowIndex < _spreadsheet.getNumberRows() + 1; rowIndex++) {
            for (int columnIndex = 1; columnIndex < _spreadsheet.getNumberColumns() + 1; columnIndex++) {
                if (_spreadsheet.getCell(rowIndex, columnIndex).getConteudo() != null)
                    if (searchLogic(rowIndex, columnIndex))
                        resultado.add(_spreadsheet.getCell(rowIndex, columnIndex));
            }
        }
        return resultado;
    }

    public String getValue() {
        return _valueSearch;
    }

    public Spreadsheet getSpreadsheet() {
        return _spreadsheet;
    }

    public abstract boolean searchLogic(int row, int column) throws UnrecognizedCoordsException;
}
