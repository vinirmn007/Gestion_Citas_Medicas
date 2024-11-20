package com.gestionCitas.controls.dao;

import com.gestionCitas.controls.dao.implement.AdapterDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.CitaMedica;

public class CitaMedicaDao extends AdapterDao<CitaMedica>{
    private CitaMedica cita;
    private LinkedList listAll;

    public CitaMedicaDao() {
        super(CitaMedica.class);
    }

    public CitaMedica getCita() {
        if (cita == null) {
            this.cita = new CitaMedica();
        }
        return this.cita;
    }

    public void setCita(CitaMedica cita) {
        this.cita = cita;
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
            cita.setId(id);
            this.persist(this.cita);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean update() throws Exception {
        try {
            this.mergeById(this.cita, this.cita.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete() throws Exception {
        try {
            this.deleteById(this.cita.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
