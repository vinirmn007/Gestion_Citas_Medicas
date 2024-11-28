package com.gestionCitas.controls.dao.services;

import com.gestionCitas.controls.dao.EstadoDao;
import com.gestionCitas.controls.dao.TurnoDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Estado;

public class TurnoServices {
    private TurnoDao obj;

    public TurnoServices() {
        this.obj = new TurnoDao();
    }

    public Turno getTurno() {
        return this.obj.getTurno();
    }

    public void setTurno(Turno Turno) {
        this.obj.setTurno(Turno);
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

    public Turno get(Integer id) throws Exception {
        return this.obj.get(id);
    }
}