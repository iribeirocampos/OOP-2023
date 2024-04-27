package xxl.core;

import java.io.Serializable;

public class Range implements Serializable {
    private Cell[] _range;

    public Range(Cell[] range) {
        _range = range;
    }

    public void subscribe(Content observer) {
        for (Cell c : _range) {
            c.subscribe(new Observer(observer));
        }
    }

    public Range(Cell range) {
        _range = new Cell[1];
        _range[0] = range;
    }

    public Cell[] getCells() {
        return _range;
    }

    public String toString() {
        return "" + _range[0].getLinha() + ";" + _range[0].getColuna() + ":" + _range[_range.length - 1].getLinha()
                + ";" + _range[_range.length - 1].getColuna();
    }

    private int[] adjustCoords() {
        int rowAdjust = getCells()[0].getLinha() - 1;
        int colunmAdjust = getCells()[0].getColuna() - 1;
        return new int[] { rowAdjust, colunmAdjust };
    }

    public Range deepCopy() {
        Cell[] celulas = new Cell[_range.length];
        int[] adjust = adjustCoords();
        int index = 0;
        for (Cell c : _range) {
            celulas[index] = new Cell(c.getLinha() - adjust[0], c.getColuna() - adjust[1]);
            try {
                celulas[index].insertConteudo(c.getConteudo().copy());
            } catch (NullPointerException e) {
                // Do nothing
            }
            index++;
        }
        return new Range(celulas);
    }
}
