package xxl.core;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String _name;
    private List<Spreadsheet> _spreadsheets = new ArrayList<Spreadsheet>();

    @Serial
    private static final long serialVersionUID = 202308312365L;

    public User(String nome, Spreadsheet spreadsheet) {
        _name = nome;
        if (_spreadsheets.contains(spreadsheet)) {
            // Do nothing
        } else {
            _spreadsheets.add(spreadsheet);
        }
    }

    public void addSpreadSheet(Spreadsheet spreadsheet) {
        _spreadsheets.add(spreadsheet);
    }

    public String getName() {
        return _name;
    }

}
