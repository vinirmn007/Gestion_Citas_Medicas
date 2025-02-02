package com.gestionCitas.controls.estructures.list;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.gestionCitas.controls.estructures.exception.ListEmptyException;
import com.gestionCitas.models.Cuenta;

public class LinkedList<E> {
    private Node<E> header;
    private Node<E> last;
    private Integer size;

    public LinkedList() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    public Boolean isEmpty() {
        return (this.header == null || this.size == 0);
    }

    public Integer getSize() {
        return this.size;
    }

    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    // METODOS PARA AGREGAR ELEMENTOS
    private void addHeader(E dato) {
        Node<E> help;
        if (isEmpty()) {
            help = new Node<>(dato);
            header = help;
            last = help;
        } else {
            Node<E> healpHeader = this.header;
            help = new Node<>(dato, healpHeader);
            this.header = help;
        }
        this.size++;
    }

    private void addLast(E info) {
        Node<E> help;
        if (isEmpty()) {
            addHeader(info);
        } else {
            help = new Node<>(info, null);
            last.setNext(help);
            last = help;
            this.size++;
        }
    }

    public void add(E info) {
        addLast(info);
    }

    public void add(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty() || index.intValue() == 0) {
            addHeader(info);
        } else if (index.intValue() == this.size.intValue()) {
            addLast(info);
        } else {
            Node<E> search_preview = getNode(index - 1);
            Node<E> search = getNode(index);
            Node<E> help = new Node<>(info, search);
            search_preview.setNext(help);
            this.size++;
        }
    }

    // OBTENER NODO
    private Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header;
        } else if (index.intValue() == (this.size - 1)) {
            return last;
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    // OBTENER ELEMENTOS
    public E get(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista vacía");
        } else if (index.intValue() < 0 || index.intValue() >= this.size.intValue()) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header.getInfo();
        } else if (index.intValue() == (this.size - 1)) {
            return last.getInfo();
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search.getInfo();
        }
    }

    // ELIMINAR ELEMENTOS
    public E deleteFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista vacia");
        } else {
            E element = header.getInfo();
            Node<E> help = header.getNext();
            header = help;
            if (size.intValue() == 1) {
                last = null;
            }
            size--;
            return element;
        }
    }

    public E deleteLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista vacia");
        } else {
            E element = last.getInfo();
            Node<E> help = getNode(size - 2);
            if (help == null) {
                last = null;
                if (size == 2) {
                    last = header;
                } else {
                    header = null;
                }
            } else {
                last = null;
                last = help;
                last.setNext(null);
            }
            size--;
            return element;
        }
    }

    public E delete(Integer index) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista vacia");
        } else if (index.intValue() < 0 || index.intValue() >= this.size.intValue()) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index == 0) {
            return deleteFirst();
        } else if (index == this.size - 1) {
            return deleteLast();
        } else {
            Node<E> before = getNode(index - 1);
            Node<E> actual = getNode(index);

            E element = before.getInfo();
            Node<E> next = actual.getNext();

            before.setNext(actual.getNext());
            actual = null;
            before.setNext(next);
            size--;
            return element;
        }
    }

    // MODIFICAR ELEMENTOS
    public void update(E info, int index) throws ListEmptyException, IndexOutOfBoundsException {
        // Verificar si la lista está vacía
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista vacía");
        }

        // Verificar si el índice está fuera de rango
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        }

        // Si el índice es 0, actualiza el primer nodo (header)
        if (index == 0) {
            header.setInfo(info);
        }
        // Si el índice es el último, actualiza el último nodo (last)
        else if (index == this.size - 1) {
            last.setInfo(info);
        }
        // Si el índice está en cualquier otro lugar, recorre la lista
        else {
            Node<E> search = header;
            for (int i = 0; i < index; i++) {
                search = search.getNext();
            }
            search.setInfo(info);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("List Data \n");
        try {
            Node<E> help = header;
            while (help != null) {
                sb.append(help.getInfo()).append(" -> ");
                help = help.getNext();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }

        return sb.toString();
    }

    public E[] toArray() {
        E[] matrix = null;
        if (!isEmpty()) {
            Class clazz = header.getInfo().getClass();
            matrix = (E[]) java.lang.reflect.Array.newInstance(clazz, size);
            Node<E> aux = header;
            for (int i = 0; i < size && aux != null; i++) {
                matrix[i] = aux.getInfo();
                aux = aux.getNext();
            }
        }
        return matrix;
    }

    public LinkedList<E> toList(E[] matrix) {
        reset();
        for (int i = 0; i < matrix.length; i++) {
            this.add(matrix[i]);
        }
        return this;
    }
    
    //QUICKSORT
    public LinkedList<E> order(String attribute, Integer type) throws Exception {
        if (!isEmpty()) {
            E data = this.header.getInfo();
            if (data instanceof Object) {
                E[] lista = this.toArray();
                reset();
                this.toList(quickSort(lista, 0, lista.length-1, attribute, type));
            }
        }
        return this;
    }

    //METODOS AUXILIARES PARA QUICK SORT
    private E[] quickSort(E[] lista, int first, int last, String attribute, Integer type) throws Exception {
        if (first < last) {
            int pivote = particionLista(lista, first, last, attribute, type);

            quickSort(lista, first, pivote - 1, attribute, type);
            quickSort(lista, pivote + 1, last, attribute, type);
        } 
        return lista;
    }

    private int particionLista(E[] lista, int first, int last, String attribute, Integer type) throws Exception {
        E pivote = lista[last];
        //System.out.println((Familia) pivote);
        int i = first - 1;

        for (int j = first; j < last; j++) {
            if (atrribute_compare(attribute, pivote, lista[j], type)) {
                i++;
                E aux = lista[i];
                lista[i] = lista[j];
                lista[j] = aux;
            }
        }

        E aux = lista[i + 1];
        lista[i + 1] = lista[last];
        lista[last] = aux;
        
        return i + 1;
    }

    //BUSQUEDA LINEAL
    public LinkedList<E> linealSearch(String attribute, Object value) throws Exception {
        LinkedList<E> lista = new LinkedList<>();
        if (!this.isEmpty()) {
            E[] aux = this.toArray();
            for (int i = 0; i < aux.length; i++) {
                Object attrValue = exist_attribute(aux[i], attribute);
                if (attrValue != null) {
                    String attrValueStr = attrValue.toString().toLowerCase();
                    String valueStr = value.toString().toLowerCase();
                    if (attrValueStr.equals(valueStr) || attrValueStr.startsWith(valueStr)) {
                        lista.add(aux[i]);
                    }
                }
            }
        }
        return lista;
    }

    //BUSQUEDA LINEAR BINARIA
    public E binarySearch(String attribute, Object value) throws Exception {
        E obj = null;
        if (!this.isEmpty()) {
            E[] aux = this.order(attribute, 0).toArray();
            int first = 0;
            int last = aux.length - 1;
             
            while (first <= last) {
                int mid = first + (last - first) / 2;
                Object attrValue = exist_attribute(aux[mid], attribute);
            
                if (attrValue != null) {
                    String attrValueStr = attrValue.toString().toLowerCase();
                    String valueStr = value.toString().toLowerCase();
                    //System.out.println("mid: " + attrValueStr);
                    //System.out.println("value: " + valueStr);

                    Integer valueInt = -1; //numeros

                    try {
                        valueInt = Integer.parseInt(valueStr);
                    } catch (Exception e) {
                    }

                    if (attrValueStr.equals(valueStr) ||
                        attrValue.equals(valueInt)) {
                        obj = aux[mid];
                        break;
                    } else if (attrValue instanceof Number) {
                        if (compare(attrValue, valueInt, 0)) {
                            last = mid - 1;
                        } else {
                            first = mid + 1;
                        }  
                    } else if (compare(attrValueStr, valueStr, 0)) {
                        last = mid - 1;
                    } else {
                        first = mid + 1;
                    } 
                } 
            }
        }
        return obj;
    }

    //METODOS AUXILIARES PARA ORDENAMIENTO Y BUSQUEDA
    private Boolean compare(Object a, Object b, Integer type) {
        switch (type) {
            case 0:
                if (a instanceof Number) {
                    Number a1 = (Number) a;
                    Number b1 = (Number) b;
                    return a1.doubleValue() > b1.doubleValue();
                } else {
                    // a > b
                    return (a.toString()).compareTo(b.toString()) > 0;
                }
                // break;

            default:
                // mayor a menor
                if (a instanceof Number) {
                    Number a1 = (Number) a;
                    Number b1 = (Number) b;
                    return a1.doubleValue() < b1.doubleValue();
                } else {
                    // a < b
                    return (a.toString()).compareTo(b.toString()) < 0;
                }
                // break;
        }

    }

    // compare class
    private Boolean atrribute_compare(String attribute, E a, E b, Integer type) throws Exception {
        return compare(exist_attribute(a, attribute), exist_attribute(b, attribute), type);
    }

    private Object exist_attribute(E a, String attribute) throws Exception {
        Method method = null;
        attribute = attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
        attribute = "get" + attribute;
        //OBLIGATORIO QUE EXISTA UN GET EN LE OBJETO
        for (Method aux : a.getClass().getMethods()) {           
            if (aux.getName().contains(attribute)) {
                method = aux;
                break;
            }
        }
        if (method == null) {
            for (Method aux : a.getClass().getSuperclass().getMethods()) {              
                if (aux.getName().contains(attribute)) {
                    method = aux;
                    break;
                }
            }
        }
        if (method != null) {            
            return method.invoke(a);
        }
        
        return null;
    }

    public LinkedList<E> clone() {
        LinkedList<E> clonedList = new LinkedList<>();
        for (int i = 0; i < this.size; i++) {
            clonedList.add(this.toArray()[i]);
        }
        return clonedList;
    }

    private Boolean compareDates(Object a, Object b, Integer type) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    if (a instanceof String && b instanceof String) {
        try {
            LocalDate dateA = LocalDate.parse((String) a, formatter);
            LocalDate dateB = LocalDate.parse((String) b, formatter);

            if (type == 0) {
                return dateA.isAfter(dateB); // Orden ascendente
            } else {
                return dateA.isBefore(dateB); // Orden descendente
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    return false; // En caso de error
}
}
