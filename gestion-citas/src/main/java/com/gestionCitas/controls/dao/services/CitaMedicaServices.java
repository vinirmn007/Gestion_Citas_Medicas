package com.gestionCitas.controls.dao.services;

import com.gestionCitas.controls.dao.CitaMedicaDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.CitaMedica;

public class CitaMedicaServices {
    private CitaMedicaDao obj;

    public CitaMedicaServices() {
        this.obj = new CitaMedicaDao();
    }

    public CitaMedica getCitaMedica() {
        return this.obj.getCita();
    }

    public void setCitaMedica(CitaMedica CitaMedica) {
        this.obj.setCita(CitaMedica);
    }

    public LinkedList getListAll() {
        return this.obj.getListAll();
    }

    public Boolean save() throws Exception {
        return this.obj.save();
    }

    public Boolean update() throws Exception {
        return this.obj.update();
    }

    public Boolean delete() throws Exception {
        return this.obj.delete();
    }

    public CitaMedica get(Integer id) throws Exception {
        return this.obj.getById(id);
    }

    public LinkedList order(String attribute, Integer type) throws Exception {
        return this.obj.getListAll().order(attribute, type);
    }

    public LinkedList linealSearch(String attribute, Object value) throws Exception {
        return this.obj.getListAll().linealSearch(attribute, value);
    }

    public Object binarySearch(String attribute, Object value) throws Exception {
        return this.obj.getListAll().binarySearch(attribute, value);
    }
}
