package com.gestionCitas.controls.dao.services;

import com.gestionCitas.controls.dao.MedicoDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Medico;

public class MedicoServices {
    private MedicoDao obj;

    public MedicoServices() {
        this.obj = new MedicoDao();
    }

    public Medico getMedico() {
        return this.obj.getMedico();
    }

    public void setMedico(Medico Medico) {
        this.obj.setMedico(Medico);
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

    public Medico get(Integer id) throws Exception {
        return this.obj.get(id);
    }
}
