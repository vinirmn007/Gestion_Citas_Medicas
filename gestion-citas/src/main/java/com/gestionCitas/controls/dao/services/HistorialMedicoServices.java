package com.gestionCitas.controls.dao.services;

import com.gestionCitas.controls.dao.HistorialMedicoDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.HistorialMedico;
import com.gestionCitas.models.enums.TipoSangre;

public class HistorialMedicoServices {
    private HistorialMedicoDao obj;

    public HistorialMedicoServices() {
        this.obj = new HistorialMedicoDao();
    }

    public HistorialMedico getHistorialMedico() {
        return this.obj.getHistorial();
    }

    public void setHistorialMedico(HistorialMedico HistorialMedico) {
        this.obj.setHistorial(HistorialMedico);
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

    public HistorialMedico get(Integer id) throws Exception {
        return this.obj.getById(id);
    }

    public TipoSangre getTipoSangre(String tipo) {
        return this.obj.getTipoSangre(tipo);
    }

    public TipoSangre[] getAllTiposSangre() {
        return this.obj.getAllTiposSangre();
    }
}
