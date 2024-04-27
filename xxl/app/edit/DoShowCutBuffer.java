package xxl.app.edit;

import pt.tecnico.uilib.menus.Command;
import xxl.core.Spreadsheet;
import xxl.core.Cell;
import xxl.core.Range;

/**
 * Show cut buffer command.
 */
class DoShowCutBuffer extends Command<Spreadsheet> {

  DoShowCutBuffer(Spreadsheet receiver) {
    super(Label.SHOW_CUT_BUFFER, receiver);
  }

  @Override
  protected final void execute() {
    Range range = _receiver.getMemory();
    if (range != null) {
      for (Cell c : range.getCells()) {
        try {
          _display.addLine(
              "" + c.getLinha() + ";" + c.getColuna() + "|" + c.getConteudo().toString());
        } catch (NullPointerException e) {
          _display
              .addLine("" + c.getLinha() + ";" + c.getColuna() + "|");
        }
      }
    }
  }
}
