package com.gestionCitas.controls.dao;

import com.gestionCitas.controls.dao.implement.AdapterDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Medicamento;

public class MedicamentoDao extends AdapterDao<Medicamento> {
    private Medicamento medicamento;
    private LinkedList listAll;

    public MedicamentoDao() {
        super(Medicamento.class);
    }

    public Medicamento getMedicamento() {
        if (medicamento == null) {
            this.medicamento = new Medicamento();
        }
        return this.medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
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
            medicamento.setId(id);
            this.persist(this.medicamento);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean update() throws Exception {
        try {
            this.mergeById(this.medicamento, this.medicamento.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete() throws Exception {
        try {
            this.deleteById(this.medicamento.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
