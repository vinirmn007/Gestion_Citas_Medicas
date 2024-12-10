package com.gestionCitas.controls.dao.services;

import com.gestionCitas.controls.dao.SignosVitalesDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.SignosVitales;

public class SignosVitalesServices {
    private SignosVitalesDao obj;

    public SignosVitalesServices() {
        this.obj = new SignosVitalesDao();
    }

    public SignosVitales getSignosVitales() {
        return this.obj.getSignosVitales();
    }

    public void setSignosVitales(SignosVitales SignosVitales) {
        this.obj.setSignosVitales(SignosVitales);
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

    public SignosVitales get(Integer id) throws Exception {
        return this.obj.getById(id);
    }
}
