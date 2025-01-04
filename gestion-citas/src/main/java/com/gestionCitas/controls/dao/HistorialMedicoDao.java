package com.gestionCitas.controls.dao;

import com.gestionCitas.controls.dao.implement.AdapterDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.HistorialMedico;
import com.gestionCitas.models.enums.TipoSangre;

public class HistorialMedicoDao extends AdapterDao<HistorialMedico> {
    private HistorialMedico historial;
    private LinkedList listAll;

    public HistorialMedicoDao() {
        super(HistorialMedico.class);
    }

    public HistorialMedico getHistorial() {
        if (historial == null) {
            this.historial = new HistorialMedico();
        }
        return this.historial;
    }

    public void setHistorial(HistorialMedico historial) {
        this.historial = historial;
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
            historial.setId(id);
            this.persist(this.historial);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean update() throws Exception {
        try {
            this.mergeById(this.historial, this.historial.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*public Boolean delete() throws Exception {
        try {
            this.deleteById(this.historial.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }*/

    public TipoSangre getTipoSangre(String tipo) {
        return TipoSangre.valueOf(tipo);
    }

    public TipoSangre[] getAllTiposSangre() {
        return TipoSangre.values();
    }
}
