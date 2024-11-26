package com.gestionCitas.controls.estructures.list;

public class Node<E> {
    private E info; 
    private Node<E> next;

    public Node(E info) {
        this.info = info;
        this.next = null;
    }

    public Node(E info, Node<E> next) {
        this.info = info;
        this.next = next;
    }

    public E getInfo() {
        return this.info;
    }

    public void setInfo(E info) {
        this.info = info;
    }
 
    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Nodo: ");
        try {
            sb.append(this.info);
        } catch (Exception e) {
            sb.append(e.getMessage());
        }

        return sb.toString();
    }
}