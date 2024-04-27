package xxl.core;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cell implements Serializable {
    private int _linha;
    private int _coluna;
    private Content _content;
    private List<Observer> _observers = new ArrayList<Observer>();

    @Serial
    private static final long serialVersionUID = 202308312360L;

    public Cell(int linha, int coluna) {
        _linha = linha;
        _coluna = coluna;
    }

    public void subscribe(Observer observer) {
        _observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        _observers.remove(observer);
    }

    public void notifyCells() {
        for (Observer c : _observers) {
            try {
                c.update();
            } catch (NullPointerException e) {
                // DO nothing
            }
        }
    }

    public void insertConteudo(Content content) {
        _content = content;
        notifyCells();
    }

    public int getLinha() {
        return _linha;
    }

    public int getColuna() {
        return _coluna;
    }

    public Content getConteudo() {
        return _content;
    }

    public void setConteudoNull() {
        _content = null;
        notifyCells();
    }
}