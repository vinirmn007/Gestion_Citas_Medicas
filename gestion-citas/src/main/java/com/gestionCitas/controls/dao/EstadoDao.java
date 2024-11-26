package com.gestionCitas.controls.dao;

import com.gestionCitas.controls.dao.implement.AdapterDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Estado;

public class EstadoDao extends AdapterDao<Estado>{
    private Estado estado;
    private LinkedList listAll;

    public EstadoDao() {
        super(Estado.class);
    }

    public Estado getEstado() {
        if (cita == null) {
            this.cita = new Estado();
        }
        return this.estado;
    }

    public void setCita(Estado estado) {
        this.estado = estado;
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
            estado.setId(id);
            this.persist(this.estado);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean update() throws Exception {
        try {
            this.mergeById(this.estado, this.estado.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete() throws Exception {
        try {
            this.deleteById(this.estado.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}