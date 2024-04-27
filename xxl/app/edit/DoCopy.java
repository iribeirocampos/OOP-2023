package xxl.app.edit;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.app.exception.InvalidCellRangeException;
import xxl.core.Range;
import xxl.core.Spreadsheet;
import xxl.core.exception.RangeOutOfBounds;
import xxl.core.exception.UnrecognizedCoordsException;

/**
 * Copy command.
 */
class DoCopy extends Command<Spreadsheet> {

  DoCopy(Spreadsheet receiver) {
    super(Label.COPY, receiver);
    addStringField("address", Message.address());
  }

  @Override
  protected final void execute() throws CommandException {
    String address = stringField("address");
    try {
      Range range = _receiver.createRange(address);
      _receiver.copy(range);
    } catch (RangeOutOfBounds | UnrecognizedCoordsException e) {
      throw new InvalidCellRangeException(address);
    }
  }
}
