package com.gestionCitas.controls.dao.services;

import java.util.HashMap;

import com.gestionCitas.controls.dao.CitaMedicaDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.CitaMedica;
import com.gestionCitas.models.Cuenta;
import com.gestionCitas.models.Persona;
import com.gestionCitas.models.Turno;

public class CitaMedicaServices {
    private CitaMedicaDao obj;

    public CitaMedicaServices() {
        this.obj = new CitaMedicaDao();
    }

    public CitaMedica getCitaMedica() {
        return this.obj.getCita();
    }

    public void setCitaMedica(CitaMedica CitaMedica) {
        this.obj.setCita(CitaMedica);
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

    public CitaMedica get(Integer id) throws Exception {
        return this.obj.getById(id);
    }

    public LinkedList order(String attribute, Integer type) throws Exception {
        return this.obj.getListAll().order(attribute, type);
    }

    public LinkedList linealSearch(String attribute, Object value) throws Exception {
        return this.obj.getListAll().linealSearch(attribute, value);
    }

    public Object binarySearch(String attribute, Object value) throws Exception {
        return this.obj.getListAll().binarySearch(attribute, value);
    }

    public Object[] searchByHistId(Integer id) throws Exception {
        if (!obj.getListAll().isEmpty()) {
            LinkedList citasList = obj.getListAll().linealSearch("historialMedicoId", id);
            if (citasList.isEmpty()) {
                return new Object[] {};
            }
            CitaMedica[] lista = (CitaMedica[]) citasList.toArray();
            Object[] respuesta = new Object[lista.length];
            for (int i = 0; i < lista.length; i++) {
                Turno turno = new TurnoServices().get(lista[i].getTurnoId());
                HashMap mapa = new HashMap<>();
                mapa.put("id", lista[i].getId());
                mapa.put("observaciones", lista[i].getObservaciones());
                mapa.put("motivo", lista[i].getMotivo());
                mapa.put("turno", turno);
                respuesta[i] = mapa;
            }
            return respuesta;
        }
        return new Object[] {};
    }
}
