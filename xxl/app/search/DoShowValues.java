package xxl.app.search;

import java.util.List;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.app.exception.InvalidCellRangeException;
import xxl.core.Cell;
import xxl.core.Spreadsheet;
import xxl.core.exception.UnrecognizedCoordsException;
import xxl.core.search.Search;
import xxl.core.search.SearchValue;

/**
 * Command for searching content values.
 */
class DoShowValues extends Command<Spreadsheet> {

  DoShowValues(Spreadsheet receiver) {
    super(Label.SEARCH_VALUES, receiver);
    addStringField("valor", Message.searchValue());
  }

  @Override
  protected final void execute() throws CommandException {
    String nameFunc = stringField("valor");
    Search search = new SearchValue(nameFunc, _receiver);
    try {
      List<Cell> resultado = search.spreadsheetIterate();
      for (Cell c : resultado) {
        _display.addLine(
            "" + c.getLinha() + ";" + c.getColuna() + " | " + c.getConteudo().toString());
      }

    } catch (UnrecognizedCoordsException e) {
      throw new InvalidCellRangeException(nameFunc);
    }
  }
}
