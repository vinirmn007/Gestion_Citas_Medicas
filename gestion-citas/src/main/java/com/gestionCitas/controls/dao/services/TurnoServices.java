package com.gestionCitas.controls.dao.services;

import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.controls.dao.TurnoDao;
import com.gestionCitas.models.enums.Estado;
import com.gestionCitas.models.Turno;

public class TurnoServices {
    private TurnoDao obj;

    public TurnoServices() {
        this.obj = new TurnoDao();
    }

    public Turno getTurno() {
        return this.obj.getTurno();
    }

    public void setTurno(Turno turno) {
        this.obj.setTurno(turno);
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

    public Turno get(Integer id) throws Exception {
        return this.obj.get(id);
    }

    /**
     * Obtiene una lista de turnos filtrados por estado.
     * @param estado El estado por el cual se quiere filtrar.
     * @return Una lista de turnos con el estado especificado.
     
    public LinkedList<Turno> findByEstado(Estado estado) {
        return this.obj.findByEstado(estado);
    }*/

    public LinkedList order(String attribute, Integer type) throws Exception {
        return this.obj.getListAll().order(attribute, type);
    }

    public LinkedList linealSearch(String attribute, Object value) throws Exception {
        return this.obj.getListAll().linealSearch(attribute, value);
    }

    public Object binarySearch(String attribute, Object value) throws Exception {
        return this.obj.getListAll().binarySearch(attribute, value);
    }
}
