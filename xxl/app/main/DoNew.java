package xxl.app.main;

import java.io.IOException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.app.exception.UnableToSaveFileException;
import xxl.core.Calculator;
import xxl.core.Spreadsheet;
import xxl.core.exception.MissingFileAssociationException;

/**
 * Open a new file.
 */
class DoNew extends Command<Calculator> {

  DoNew(Calculator receiver) {
    super(Label.NEW, receiver);
    addIntegerField("nLinhas", Message.lines());
    addIntegerField("nColunas", Message.columns());
  }

  @Override
  protected final void execute() throws CommandException {
    if (_receiver.getSpreadsheet() != null && _receiver.getSpreadsheet().getChange() == true) {
      Form f = new Form("gravar");
      boolean resposta = Form.confirm(Message.saveBeforeExit());
      f.parse();
      try {
        if (resposta == true) {
          if (_receiver.getSpreadsheet().getFileName() != null)
            _receiver.save();
          else {
            String filename = Form.requestString(Message.newSaveAs());
            _receiver.saveAs(filename);
            f.parse();
          }
        }
      } catch (IOException | MissingFileAssociationException | ClassNotFoundException e) {
        throw new UnableToSaveFileException(_receiver.getSpreadsheet().getFileName());
      }
    }
    int nLinhas = integerField("nLinhas");
    int nColunas = integerField("nColunas");
    Spreadsheet newSpreadSheet = new Spreadsheet(nLinhas, nColunas);
    _receiver.addSpreadsheet(newSpreadSheet);
  }
}
