package com.gestionCitas.controls.estructures.stack;

import com.gestionCitas.controls.estructures.exception.ListEmptyException;
import com.gestionCitas.controls.estructures.exception.OverFlowException;
import com.gestionCitas.controls.estructures.list.LinkedList;

public class StackOperation <E> extends LinkedList {
    private Integer top;
    
    public  StackOperation(Integer _top) {
        this.top = _top;
    }

    public Boolean verify() {
        return getSize().intValue() <= top.intValue();
    }

    public void push(E info) throws Exception{
        if (verify()) {
            add(info, 0);
        } else {
            throw new OverFlowException("Pila llena");
        }
    }

    public E pop() throws Exception {
        if (isEmpty()) {
            throw new ListEmptyException("Pila vacía en pop");
        } else {
            return (E) deleteFirst();
        }
    }

    public E peek() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("La pila esta vacia en peek");
        } else {
            return (E) get(0);
        }
    }


    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }
}
