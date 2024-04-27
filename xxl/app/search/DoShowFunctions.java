package xxl.app.search;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.core.Spreadsheet;
import xxl.core.search.Search;
import xxl.core.search.SearchFunction;
import xxl.core.exception.UnrecognizedCoordsException;
import xxl.app.exception.InvalidCellRangeException;
import xxl.core.Cell;
import java.util.List;

/**
 * Command for searching function names.
 */
class DoShowFunctions extends Command<Spreadsheet> {

  DoShowFunctions(Spreadsheet receiver) {
    super(Label.SEARCH_FUNCTIONS, receiver);
    addStringField("função", Message.searchFunction());
  }

  @Override
  protected final void execute() throws CommandException {
    String nameFunc = stringField("função");
    Search search = new SearchFunction(nameFunc, _receiver);
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
