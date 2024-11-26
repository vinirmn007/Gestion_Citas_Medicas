package com.gestionCitas.controls.dao.services;

import com.gestionCitas.controls.dao.EstadoDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Estado;

public class EstadoServices {
    private EstadoDao obj;

    public EstadoServices() {
        this.obj = new EstadoDao();
    }

    public CitaMedica getEstado() {
        return this.obj.getEstado();
    }

    public void setEstado(Estado Estado) {
        this.obj.setEstado(Estado);
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
        return this.obj.get(id);
    }
}