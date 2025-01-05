package com.gestionCitas.controls.dao.services;

import com.gestionCitas.controls.dao.MedicamentoDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Medicamento;

public class MedicamentoServices {
    private MedicamentoDao obj;

    public MedicamentoServices() {
        this.obj = new MedicamentoDao();
    }

    public Medicamento getMedicamento() {
        return this.obj.getMedicamento();
    }

    public void setMedicamento(Medicamento medicamento) {
        this.obj.setMedicamento(medicamento);
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

    public Medicamento get(Integer id) throws Exception {
        return this.obj.get(id);
    }
}
