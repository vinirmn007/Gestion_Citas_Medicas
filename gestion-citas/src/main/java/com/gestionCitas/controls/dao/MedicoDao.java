package com.gestionCitas.controls.dao;

import com.gestionCitas.controls.dao.implement.AdapterDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Medico;
import com.gestionCitas.models.enums.Genero;
import com.gestionCitas.models.enums.Identificacion;

public class MedicoDao extends AdapterDao<Medico> {
    private Medico medico;
    private LinkedList listAll;

    public MedicoDao() {
        super(Medico.class);
    }

    public Medico getMedico() {
        if (medico == null) {
            this.medico = new Medico();
        }
        return this.medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
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
            medico.setId(id);
            this.persist(this.medico);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean update() throws Exception {
        try {
            this.mergeById(this.medico, this.medico.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete() throws Exception {
        try {
            this.deleteById(this.medico.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Identificacion getTipoIdent(String tipo) {
        return Identificacion.valueOf(tipo);
    }

    public Identificacion[] getAllTiposIdent() {
        return Identificacion.values();
    }

    public Genero getGenero(String tipo) {
        return Genero.valueOf(tipo);
    }

    public Genero[] getAllGeneros() {
        return Genero.values();
    }
    
}
