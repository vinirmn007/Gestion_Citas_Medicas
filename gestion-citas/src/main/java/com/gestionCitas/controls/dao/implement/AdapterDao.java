package com.gestionCitas.controls.dao.implement;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import com.gestionCitas.controls.dao.implement.InterfazDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Cuenta;
import com.google.gson.Gson;

public class AdapterDao<T> implements InterfazDao<T> {
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
            e.printStackTrace();
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

        // Verifica si la lista está vacía
        if (list == null || list.isEmpty()) {
            throw new ListEmptyException("La lista está vacía, no se puede actualizar.");
        }

        // Verifica si el índice está dentro del rango válido
        if (index < 0 || index >= list.getSize()) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }

        // Actualiza el objeto en la lista
        list.update(obj, index);

        // Convierte la lista actualizada a JSON
        String info = gson.toJson(list.toArray());

        // Guarda los cambios en el archivo
        saveFile(info);
    }

    @Override
    public void mergeT(T obj) throws Exception {
        LinkedList<T> list = listAll();

        // Verifica si la lista está vacía
        if (list == null || list.isEmpty()) {
            throw new ListEmptyException("La lista está vacía, no se puede actualizar.");
        }

        // Encuentra el índice de la cuenta por ID
        boolean encontrado = false;
        for (int i = 0; i < list.getSize(); i++) {
            if (((Cuenta) list.get(i)).getId().equals(((Cuenta) obj).getId())) {
                list.update(obj, i);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            throw new Exception("No se encontró el objeto con el ID proporcionado.");
        }

        // Convierte la lista actualizada a JSON
        String info = gson.toJson(list.toArray());

        // Guarda los cambios en el archivo
        saveFile(info);
    }

    @Override
    public T get(Integer id) throws Exception {
        LinkedList<T> list = listAll();
        if (!list.isEmpty()) {
            T[] matrix = list.toArray();
            return matrix[id - 1];
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

    // METODOS PARA ACTUALIZAR, ELIMINAR Y OBTENER MEDIANTE EL ID DE CADA MODELO
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
