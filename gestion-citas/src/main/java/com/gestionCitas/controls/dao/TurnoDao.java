package com.gestionCitas.controls.dao;

import com.gestionCitas.controls.dao.implement.AdapterDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Turno;

public class TurnoDao extends AdapterDao<Turno>{
    private Turno turno;
    private LinkedList listAll;

    public TurnoDao() {
        super(Turno.class);
    }

    public Turno getTurno() {
        if (turno == null) {
            this.turno = new Turno();
        }
        return this.turno;
    }

    public void setturno(Turno turno) {
        this.turno = turno;
    }

    public LinkedList getListAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return this.listAll;
    }

    // OPERACIONES
    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        try {
            turno.setId(id);
            this.persist(this.turno);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean update() throws Exception {
        try {
            this.mergeById(this.turno, this.turno.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete() throws Exception {
        try {
            this.deleteById(this.turno.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}