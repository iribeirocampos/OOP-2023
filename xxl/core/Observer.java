package xxl.core;

import java.io.Serializable;

import xxl.core.exception.UnrecognizedCoordsException;

public class Observer implements Serializable {
    Content _observerContent;

    public Observer(Content observer) {
        _observerContent = observer;
    }

    public void update() {
        try {
            _observerContent.compute();
        } catch (UnrecognizedCoordsException e) {
            // Do nothing, does not happen
        }
    }

}
