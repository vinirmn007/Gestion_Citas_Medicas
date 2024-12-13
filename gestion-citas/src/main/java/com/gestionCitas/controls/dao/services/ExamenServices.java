package com.gestionCitas.controls.dao.services;

import com.gestionCitas.controls.dao.ExamenDao;
import com.gestionCitas.models.Examen;
import com.gestionCitas.controls.estructures.list.LinkedList;

public class ExamenServices {
    private ExamenDao obj;

    public ExamenServices() {
        this.obj = new ExamenDao();
    }

    public Examen getExamen() {
        return this.obj.getExamen();
    }

    public void setExamen(Examen examen) {
        this.obj.setExamen(examen);
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

    public Examen get(Integer id) throws Exception {
        return this.obj.get(id);
    }
}