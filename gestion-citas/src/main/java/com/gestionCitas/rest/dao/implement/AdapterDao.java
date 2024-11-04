package com.practica1.rest.controller.dao.implement;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import com.practica1.rest.controller.tda.list.LinkedList;
import com.google.gson.Gson;

public class AdapterDao <T> implements InterfazDao<T> {
    private Class clazz;
    private Gson g;
    public static String URL = "media/";

    public AdapterDao(Class clazz){
        this.clazz = clazz;
        g = new Gson();
    }

    
    @Override
    public LinkedList listAll() {
        LinkedList<T> list = new LinkedList<>();
        try {
            String data = readFile();
            T[] matrix = (T[]) g.fromJson(data, java.lang.reflect.Array.newInstance(clazz,0).getClass());
            list.toList(matrix);


        } catch (Exception e) {
        
        
        }
        return list;
    }
    

    public T get(Integer id) throws Exception {
        return null;
    }
    
    public AdapterDao(InterfazDao<T> dao) {
    }
    
    public void persist(T object) throws Exception {
        LinkedList<T> list = listAll();
        list.add(object);
        String info = g.toJson(list.toArray());
        saveFile(info);
    }

    public void merge(T object, Integer index) throws Exception {
        //terminar lista, modiciar y eliminar en la lista enlazada
        //completar
    }
        //lee archivo
    private String readFile() throws Exception {
        Scanner in = new Scanner(new FileReader(URL+clazz.getSimpleName()+".json"));
        StringBuilder sb = new StringBuilder();
        while(in.hasNext()){
            sb.append(in.next());
        }
        in.close();
        return sb.toString();
    }

    private void saveFile(String data) throws Exception {
        FileWriter f = new FileWriter(URL+clazz.getSimpleName()+".json");
        f.write(data);
        f.flush();
        f.close();
    }
}
