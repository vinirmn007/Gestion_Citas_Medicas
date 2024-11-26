package com.gestionCitas.controls.dao.services;

import com.gestionCitas.controls.dao.DiagnosticoDao;
import com.gestionCitas.models.Diagnostico;
import com.gestionCitas.controls.estructures.list.LinkedList;

public class DiagnosticoServices {
    private DiagnosticoDao obj;

    public DiagnosticoServices() {
        this.obj = new DiagnosticoDao();
    }

    public Diagnostico getDiagnostico() {
        return this.obj.getDiagnostico();
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.obj.setDiagnostico(diagnostico);
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

    public Diagnostico get(Integer id) throws Exception {
        return this.obj.get(id);
    }
    
}
