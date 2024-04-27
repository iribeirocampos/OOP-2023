package xxl.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileInputStream;

import xxl.core.exception.ImportFileException;
import xxl.core.exception.MissingFileAssociationException;
import xxl.core.exception.RangeOutOfBounds;
import xxl.core.exception.UnavailableFileException;
import xxl.core.exception.UnrecognizedCoordsException;
import xxl.core.exception.UnrecognizedEntryException;
import xxl.core.exception.UnrecognizedFunctionException;

/**
 * Class representing a spreadsheet application.
 */
public class Calculator {
  /** The current spreadsheet. */
  private Spreadsheet _spreadsheet;

  /**
   * Return the current spreadsheet.
   *
   * @returns the current spreadsheet of this application. This reference can be
   *          null.
   */
  public final Spreadsheet getSpreadsheet() {
    return _spreadsheet;
  }

  /**
   * Add a spreadsheet to Calculator.
   *
   */

  public void addSpreadsheet(Spreadsheet spreadsheet) {
    _spreadsheet = spreadsheet;
  }

  /**
   * Saves the serialized application's state into the file associated to the
   * current network.
   *
   * @throws FileNotFoundException           if for some reason the file cannot be
   *                                         created or opened.
   * @throws MissingFileAssociationException if the current network does not have
   *                                         a file.
   * @throws IOException                     if there is some error while
   *                                         serializing the state of the network
   *                                         to disk.
   */
  public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
    try (ObjectOutputStream obOut = new ObjectOutputStream(
        new DeflaterOutputStream(new FileOutputStream(_spreadsheet.getFileName())))) {
      obOut.writeObject(_spreadsheet);
      _spreadsheet.unChange();
    }

  }

  /**
   * Saves the serialized application's state into the specified file. The current
   * network is
   * associated to this file.
   *
   * @param filename the name of the file.
   * @throws FileNotFoundException           if for some reason the file cannot be
   *                                         created or opened.
   * @throws MissingFileAssociationException if the current network does not have
   *                                         a file.
   * @throws IOException                     if there is some error while
   *                                         serializing the state of the network
   *                                         to disk.
   */
  public void saveAs(String filename)
      throws FileNotFoundException, MissingFileAssociationException, IOException, ClassNotFoundException {
    _spreadsheet.changeName(filename);
    save();
  }

  /**
   * @param filename name of the file containing the serialized application's
   *                 state
   *                 to load.
   * @throws UnavailableFileException if the specified file does not exist or
   *                                  there is
   *                                  an error while processing this file.
   */
  public void load(String filename) throws UnavailableFileException, UnrecognizedEntryException {
    try (ObjectInputStream obIn = new ObjectInputStream(new InflaterInputStream(new FileInputStream(filename)))) {
      Object anObject = obIn.readObject();
      _spreadsheet = (Spreadsheet) anObject;
      _spreadsheet.changeName(filename);
    } catch (IOException | ClassNotFoundException e) {
      throw new UnavailableFileException(filename);
    }
  }

  /**
   * Read text input file and create domain entities.
   *
   * @param filename name of the text input file
   * @throws ImportFileException
   */
  public void importFile(String filename)
      throws ImportFileException, UnrecognizedEntryException, UnavailableFileException, UnrecognizedCoordsException,
      RangeOutOfBounds {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      Parser parser = new Parser(new Spreadsheet(0, 0));
      _spreadsheet = parser.parseFile(filename);
    } catch (IOException | UnrecognizedFunctionException e) {
      throw new UnavailableFileException(e.getMessage());
    }
  }
}
