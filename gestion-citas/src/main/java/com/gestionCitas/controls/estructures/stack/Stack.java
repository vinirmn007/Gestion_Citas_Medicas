package com.gestionCitas.controls.estructures.stack;

public class Stack <E> {
    private StackOperation<E> stackOperation;

    public Stack(Integer cant) {
        this.stackOperation = new StackOperation<>(cant);
    }

    public void push(E info) throws Exception {
        stackOperation.push(info);
    }

    public Integer getSize() {
        return stackOperation.getSize();
    }

    public void clear() {
        stackOperation.reset();
    }

    public Integer getTop() {
        return stackOperation.getTop();
    }

    public E pop() throws Exception {
        return stackOperation.pop();
    }

    public E peek() throws Exception {
        return stackOperation.peek();
    }

    public Boolean isEmpty() {
        return stackOperation.isEmpty();
    }

    public String toString() {
        return stackOperation.toString();
    }
}
