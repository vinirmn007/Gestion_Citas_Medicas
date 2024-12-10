package com.gestionCitas.controls.dao.implement;
import com.gestionCitas.controls.estructures.list.LinkedList;

public interface InterfazDao <T> {
    public void persist(T obj) throws Exception;
    public void merge(T obj, Integer index) throws Exception;
    public LinkedList listAll();
    public T get(Integer id) throws Exception;
    public void delete(Integer id) throws Exception;

    //METODOS PARA OPERACIONES BUSCANDO SU ID
    public void mergeById(T obj, Integer id) throws Exception;
    public void mergeT(T obj) throws Exception;
    public void deleteById(Integer id) throws Exception;
    public T getById(Integer id) throws Exception;
}