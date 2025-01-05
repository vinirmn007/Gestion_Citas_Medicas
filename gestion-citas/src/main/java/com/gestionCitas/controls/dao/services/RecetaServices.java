package com.gestionCitas.controls.dao.services;

import com.gestionCitas.controls.dao.RecetaDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Receta;

public class RecetaServices {
    private RecetaDao obj;

    public RecetaServices() {
        this.obj = new RecetaDao();
    }

    public Receta getReceta() {
        return this.obj.getReceta();
    }

    public void setReceta(Receta receta) {
        this.obj.setReceta(receta);
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

    public Receta get(Integer id) throws Exception {
        return this.obj.getById(id);
    }
    
}
