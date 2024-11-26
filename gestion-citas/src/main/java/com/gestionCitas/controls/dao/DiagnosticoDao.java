package com.gestionCitas.controls.dao;

import com.gestionCitas.controls.dao.implement.AdapterDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Diagnostico;

public class DiagnosticoDao extends AdapterDao<Diagnostico> {
    private Diagnostico diagnostico;
    private LinkedList listAll;

    public DiagnosticoDao() {
        super(Diagnostico.class);
    }

    public Diagnostico getDiagnostico() {
        if (diagnostico == null) {
            this.diagnostico = new Diagnostico();
        }
        return this.diagnostico;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
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
            diagnostico.setId(id);
            this.persist(this.diagnostico);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean update() throws Exception {
        try {
            this.mergeById(this.diagnostico, this.diagnostico.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
