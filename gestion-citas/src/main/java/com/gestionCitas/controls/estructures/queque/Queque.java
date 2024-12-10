package com.gestionCitas.controls.estructures.queque;

public class Queque <E> {
    private QuequeOperation<E> quequeOperation;

    public Queque(Integer cant) {
        quequeOperation = new QuequeOperation<>(cant);
    }

    public void queque(E info) throws Exception {
        quequeOperation.queque(info);
    }

    public E dequeque() throws Exception {
        return quequeOperation.dequeque();
    }

    public Integer getSize() {
        return quequeOperation.getSize();
    }

    public void clear() {
        quequeOperation.reset();
    }

    public Integer getTop() {
        return quequeOperation.getTop();
    }

    public void setTop(Integer top) {
        quequeOperation.setTop(top);
    }
}
