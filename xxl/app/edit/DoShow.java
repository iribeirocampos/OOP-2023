package xxl.app.edit;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.app.exception.InvalidCellRangeException;
import xxl.core.Cell;
import xxl.core.Range;
import xxl.core.Spreadsheet;
import xxl.core.exception.RangeOutOfBounds;
import xxl.core.exception.UnrecognizedCoordsException;

/**
 * Class for searching functions.
 */
class DoShow extends Command<Spreadsheet> {

  DoShow(Spreadsheet receiver) {
    super(Label.SHOW, receiver);
    addStringField("address", Message.address());
  }

  @Override
  protected final void execute() throws CommandException {
    String address = stringField("address");
    try {
      Range range = _receiver.createRange(address);
      for (Cell c : range.getCells()) {
        try {
          _display.addLine(
              "" + c.getLinha() + ";" + c.getColuna() + "|" + c.getConteudo().toString());
        } catch (NullPointerException e) {
          _display
              .addLine("" + c.getLinha() + ";" + c.getColuna() + "|");

        }
      }
    } catch (RangeOutOfBounds | UnrecognizedCoordsException | IndexOutOfBoundsException | NumberFormatException re) {
      throw new InvalidCellRangeException(address);
    }
  }
}