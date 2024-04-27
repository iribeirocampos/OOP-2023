package xxl.core;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xxl.core.exception.RangeOutOfBounds;
import xxl.core.exception.UnrecognizedCoordsException;
import xxl.core.exception.UnrecognizedEntryException;
import xxl.core.exception.UnrecognizedFunctionException;

/**
 * Class representing a spreadsheet.
 */
public class Spreadsheet implements Serializable {
  private Cell[][] _celulas;
  private boolean _changed = true;
  private String _filename;
  private CutBuffer _cutBuffer;
  private List<User> _users = new ArrayList<User>();
  private User _activeUser = new User("root", this);

  @Serial
  private static final long serialVersionUID = 202308312359L;

  public Spreadsheet(int linhas, int colunas) {
    _celulas = new Cell[linhas][colunas];
    for (int i = 0; i < linhas; i++) {
      for (int j = 0; j < colunas; j++) {
        _celulas[i][j] = new Cell(i + 1, j + 1);
      }
    }
    _cutBuffer = new CutBuffer();
    if (_users.contains(_activeUser)) {
      // Do nothing
    } else {
      _users.add(_activeUser);
    }
  }

  public int getNumberRows() {
    return _celulas.length;
  }

  public int getNumberColumns() {
    return _celulas[0].length;
  }

  public void changeName(String name) {
    _filename = name;
  }

  public String getFileName() {
    return _filename;
  }

  public Cell getCell(int linha, int coluna) throws UnrecognizedCoordsException {
    try {
      return _celulas[linha - 1][coluna - 1];
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new UnrecognizedCoordsException();
    }
  }

  public void deleteCells(Range cellsToDelete) throws UnrecognizedCoordsException {
    for (Cell c : cellsToDelete.getCells()) {
      getCell(c.getLinha(), c.getColuna()).setConteudoNull();
    }
  }

  public void unChange() {
    _changed = false;
  }

  public void Change() {
    _changed = true;
  }

  public boolean getChange() {
    return _changed;
  }

  private List<Integer> parseCoords(String rangeDescription) {
    List<Integer> data = new ArrayList<Integer>();
    String[] raw = rangeDescription.split(":");
    for (String s : raw) {
      String[] coordsData = s.split(";");
      for (String s1 : coordsData) {
        data.add(Integer.parseInt(s1));
      }
    }
    return data;
  }

  public Range createRange(String rangeDescription)
      throws UnrecognizedCoordsException, RangeOutOfBounds {
    List<Integer> coords = parseCoords(rangeDescription);
    List<Cell> celulas = new ArrayList<Cell>();
    if (coords.size() == 2) {
      return new Range(getCell(coords.get(0), coords.get(1)));
    } else if (coords.get(0) == coords.get(2) || coords.get(1) == coords.get(3)) {
      for (int rowIndex = coords.get(0); rowIndex <= coords.get(2); rowIndex++) {
        for (int columnIndex = coords.get(1); columnIndex <= coords.get(3); columnIndex++) {
          celulas.add(getCell(rowIndex, columnIndex));
        }
      }

    } else {
      throw new RangeOutOfBounds();
    }
    return new Range(celulas.toArray(new Cell[celulas.size()]));
  }

  /**
   * Insert specified content in specified address.
   *
   * @param row                  the row of the cell to change
   *                             param column the column of the cell to change
   * @param contentSpecification the specification in a string format of the
   *                             content to put
   *                             in the specified cell.
   */
  public void insertContent(int row, int column, String contentSpecification)
      throws UnrecognizedEntryException, RangeOutOfBounds, UnrecognizedFunctionException, UnrecognizedCoordsException {
    Cell activeCell = _celulas[row - 1][column - 1];
    Parser parser = new Parser(this);
    Content conteudo = parser.parseContent(contentSpecification);
    activeCell.insertConteudo(conteudo);
    _changed = true;
  }

  public void insertConteudos(String destination, String conteudo)
      throws RangeOutOfBounds, UnrecognizedFunctionException, UnrecognizedEntryException, UnrecognizedCoordsException,
      ArrayIndexOutOfBoundsException {
    if (destination.contains(":")) {
      String[] arguments = destination.split(":");
      String[] initialCoords = arguments[0].split(";");
      String[] finalCoords = arguments[1].split(";");
      int numRows = Integer.parseInt(finalCoords[0]) - Integer.parseInt(initialCoords[0]) + 1;
      int numColumns = Integer.parseInt(finalCoords[1]) - Integer.parseInt(initialCoords[1]) + 1;
      for (int row = Integer.parseInt(initialCoords[0]); row < Integer.parseInt(initialCoords[0]) + numRows; row++) {
        for (int column = Integer.parseInt(initialCoords[1]); column < Integer.parseInt(initialCoords[1])
            + numColumns; column++) {
          insertContent(row, column, conteudo);
        }
      }
    } else {
      String[] coords = destination.split(";");
      insertContent(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), conteudo);
    }
  }

  public void cut(Range cellsToCut) throws UnrecognizedCoordsException {
    _cutBuffer.addMemory(cellsToCut.deepCopy());
    deleteCells(cellsToCut);
  }

  public void copy(Range cellsToCopy) {
    _cutBuffer.addMemory(cellsToCopy.deepCopy());
  }

  public void paste(String destination) throws UnrecognizedCoordsException {
    if (_cutBuffer.getMemory() != null) {
      if (destination.contains(":")) {
        String[] arguments = destination.split(":");
        String[] initialCoords = arguments[0].split(";");
        String[] finalCoords = arguments[1].split(";");
        int numRows = Integer.parseInt(finalCoords[0]) - Integer.parseInt(initialCoords[0]) + 1;
        int numColumns = Integer.parseInt(finalCoords[1]) - Integer.parseInt(initialCoords[1]) + 1;
        if (_cutBuffer.getMemory().getCells().length == 1) {
          for (int row = 0; row < numRows; row++) {
            for (int column = 0; column < numColumns; column++) {
              getCell(row, column).insertConteudo(_cutBuffer.getMemory().getCells()[0].getConteudo());
            }
          }
        } else {
          if (_cutBuffer.getMemory().getCells().length == numRows
              || _cutBuffer.getMemory().getCells().length == numColumns) {
            int index = 0;
            for (int row = Integer.parseInt(initialCoords[0]); row < Integer.parseInt(initialCoords[0])
                + numRows; row++) {
              for (int column = Integer.parseInt(initialCoords[1]); column < Integer
                  .parseInt(initialCoords[1])
                  + numColumns; column++) {
                try {
                  getCell(row, column)
                      .insertConteudo(_cutBuffer.getMemory().getCells()[index].getConteudo().copy());
                } catch (NullPointerException e) {
                  getCell(row, column).insertConteudo(null);
                }
                index++;
              }
            }
          } else {
            // Do nothing
          }
        }
      } else {
        String[] initialCoords = destination.split(";");
        int initialLine = Integer.parseInt(initialCoords[0]) - 1;
        int initialColumn = Integer.parseInt(initialCoords[1]) - 1;
        try {
          for (Cell c : _cutBuffer.getMemory().getCells()) {
            getCell(c.getLinha() + initialLine,
                c.getColuna() + initialColumn)
                .insertConteudo(c.getConteudo().copy());
            ;
          }
        } catch (UnrecognizedCoordsException e) {
          // Do nothing
        }
      }
    }
  }

  public Range getMemory() {
    return _cutBuffer.getMemory();
  }
}