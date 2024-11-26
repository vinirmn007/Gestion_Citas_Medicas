package com.gestionCitas.controls.dao.implement;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import com.gestionCitas.controls.estructures.list.LinkedList;
import com.google.gson.Gson;

public class AdapterDao <T> implements InterfazDao<T> {
    private Class clazz;
    private Gson gson;
    public static String URL = "media/";

    public AdapterDao(Class clazz) {
        this.clazz = clazz;
        gson = new Gson();
    }

    @Override
    public LinkedList listAll() {
        LinkedList<T> list = new LinkedList<>();
        try {
            String data = readFile();
            T[] matrix = (T[]) gson.fromJson(data, java.lang.reflect.Array.newInstance(clazz, 0).getClass());
            list.toList(matrix);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    @Override
    public void persist(T obj) throws Exception {
        LinkedList<T> list = listAll();
        list.add(obj);
        String info = gson.toJson(list.toArray());
        saveFile(info);
    }

    @Override
    public void merge(T obj, Integer index) throws Exception {
        LinkedList<T> list = listAll();
        list.update(obj, index);
        String info = gson.toJson(list.toArray());
        saveFile(info);
    }

    @Override
    public T get(Integer id) throws Exception {
        LinkedList<T> list = listAll();
        if (!list.isEmpty()) {
            T [] matrix = list.toArray();
            return matrix[id-1];
        }
        return null;
    }

    @Override
    public void delete(Integer id) throws Exception {
        LinkedList<T> list = listAll();
        list.delete(id);
        String info = gson.toJson(list.toArray());
        saveFile(info);
    }

    //METODOS PARA ACTUALIZAR, ELIMINAR Y OBTENER MEDIANTE EL ID DE CADA MODELO
    @Override
    public void mergeById(T obj, Integer id) throws Exception {
        LinkedList<T> list = listAll();

        for (int i = 0; i < list.getSize(); i++) {
            T objActual = list.get(i);
            Integer objId = (Integer) objActual.getClass().getMethod("getId").invoke(objActual);
            if (objId.equals(id)) {
                list.update(obj, i);
                break;
            }
        }

        String info = gson.toJson(list.toArray());
        saveFile(info);
    }

    @Override
    public T getById(Integer id) throws Exception {
        LinkedList<T> list = listAll();
        if (!list.isEmpty()) {
            for (int i = 0; i < list.getSize(); i++) {
                T obj = list.get(i);
                Integer objId = (Integer) obj.getClass().getMethod("getId").invoke(obj);
                if (objId == id) {
                    return obj;
                }
            }
        } 
        return null;
    }

    @Override
    public void deleteById(Integer id) throws Exception {
        LinkedList<T> list = listAll();
        
        for (int i = 0; i < list.getSize(); i++) {
            T obj = list.get(i);
            Integer objId = (Integer) obj.getClass().getMethod("getId").invoke(obj);
            if (objId == id) {
                list.delete(i);
                break;
            }
        }

        String info = gson.toJson(list.toArray());
        saveFile(info);
    }

    private String readFile() throws Exception {
        Scanner in = new Scanner(new FileReader(URL + clazz.getSimpleName() + ".json"));
        StringBuilder sb = new StringBuilder();

        while (in.hasNext()) {
            sb.append(in.next());
        }
        in.close();
        return sb.toString();
    }

    private void saveFile(String data) throws Exception {
        File directory = new File(URL);
        if (!directory.exists()) {
            directory.mkdirs(); // Crea el directorio si no existe.
        }
        FileWriter file = new FileWriter(URL + clazz.getSimpleName() + ".json");
        file.write(data);
        file.flush();
        file.close();
    }
}
