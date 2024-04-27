package xxl.core;

import java.io.Serializable;

public class CutBuffer implements Serializable {
    private Range _memory;

    public Range getMemory() {
        return _memory;
    }

    public void addMemory(Range memory) {
        _memory = memory;
    }
}
