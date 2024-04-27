package xxl.app.main;

import java.io.IOException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.app.exception.UnableToSaveFileException;
import xxl.core.Calculator;
import xxl.core.exception.MissingFileAssociationException;

/**
 * Save to file under current name (if unnamed, query for name).
 */
class DoSave extends Command<Calculator> {

  DoSave(Calculator receiver) {
    super(Label.SAVE, receiver, xxl -> xxl.getSpreadsheet() != null);
  }

  @Override
  protected final void execute() throws CommandException {
    if (_receiver.getSpreadsheet().getChange() == true)
      try {
        if (_receiver.getSpreadsheet().getFileName() != null) {
          super._receiver.save();
        } else {
          Form f = new Form("filename");
          String resposta = Form.requestString(Message.newSaveAs());
          f.parse();
          _receiver.saveAs(resposta);
        }
      } catch (IOException | MissingFileAssociationException | ClassNotFoundException e) {
        throw new UnableToSaveFileException(_receiver.getSpreadsheet().getFileName());
      }
  }
}
