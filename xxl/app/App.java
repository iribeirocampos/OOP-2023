package xxl.app;

import pt.tecnico.uilib.Dialog;
import xxl.core.exception.ImportFileException;
import xxl.core.exception.RangeOutOfBounds;
import xxl.core.exception.UnavailableFileException;
import xxl.core.exception.UnrecognizedEntryException;
import xxl.core.exception.UnrecognizedCoordsException;

// import java.io.IOException;

/**
 * Class that represents the spreadsheet's textual interface.
 */
public class App {

  public static void main(String[] args) {
    try (var ui = Dialog.UI) {
      var receiver = new xxl.core.Calculator();
      String datafile = System.getProperty("import");
      if (datafile != null) {
        try {
          receiver.importFile(datafile);
        } catch (ImportFileException | UnrecognizedEntryException | UnavailableFileException | RangeOutOfBounds
            | UnrecognizedCoordsException e) {
          e.printStackTrace();
        }
      }

      (new xxl.app.main.Menu(receiver)).open();
    }
  }
}
