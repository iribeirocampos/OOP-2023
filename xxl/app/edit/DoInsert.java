package xxl.app.edit;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.app.exception.InvalidCellRangeException;
import xxl.app.exception.InvalidTypeEntryException;
import xxl.app.exception.UnknownFunctionException;
import xxl.core.Spreadsheet;
import xxl.core.exception.RangeOutOfBounds;
import xxl.core.exception.UnrecognizedEntryException;
import xxl.core.exception.UnrecognizedFunctionException;
import xxl.core.exception.UnrecognizedCoordsException;

/**
 * Class for inserting data.
 */
class DoInsert extends Command<Spreadsheet> {

  DoInsert(Spreadsheet receiver) {
    super(Label.INSERT, receiver);
    addStringField("gama", Message.address());
    addStringField("content", Message.contents());
  }

  @Override
  protected final void execute() throws CommandException {
    String conteudo = stringField("content");
    String gama = stringField("gama");
    try {
      _receiver.insertConteudos(gama, conteudo);
    } catch (RangeOutOfBounds | ArrayIndexOutOfBoundsException | NumberFormatException
        | UnrecognizedCoordsException e) {
      throw new InvalidCellRangeException(gama);
    } catch (UnrecognizedEntryException ee) {
      throw new InvalidTypeEntryException(conteudo);
    } catch (UnrecognizedFunctionException ef) {
      throw new UnknownFunctionException(ef.getFilename());
    }

  }
}
