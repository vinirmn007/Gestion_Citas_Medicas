package com.gestionCitas.controls.dao;

import com.gestionCitas.controls.dao.implement.AdapterDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Examen;

public class ExamenDao extends AdapterDao<Examen> {
    private Examen examen;
    private LinkedList listAll;

    public ExamenDao() {
        super(Examen.class);
    }

    public Examen getExamen() {
        if (examen == null) {
            this.examen = new Examen();
        }
        return this.examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
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
            examen.setId(id);
            this.persist(this.examen);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean update() throws Exception {
        try {
            this.mergeById(this.examen, this.examen.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete() throws Exception {
        try {
            this.deleteById(this.examen.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
