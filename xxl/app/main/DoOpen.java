package xxl.app.main;

import xxl.app.exception.FileOpenFailedException;
import xxl.app.exception.UnableToSaveFileException;
import xxl.core.exception.MissingFileAssociationException;
import xxl.core.exception.UnavailableFileException;
import xxl.core.exception.UnrecognizedEntryException;

import java.io.IOException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.core.Calculator;

/**
 * Open existing file.
 */
class DoOpen extends Command<Calculator> {

  DoOpen(Calculator receiver) {
    super(Label.OPEN, receiver);
    addStringField("nome", Message.openFile());
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
    String name = stringField("nome");
    try {
      super._receiver.load(name);
    } catch (UnavailableFileException | UnrecognizedEntryException e) {
      throw new FileOpenFailedException(e);
    }

  }
}
