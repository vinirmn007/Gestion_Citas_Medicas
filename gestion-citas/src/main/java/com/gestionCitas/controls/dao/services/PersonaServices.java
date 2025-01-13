package com.gestionCitas.controls.dao.services;

import com.gestionCitas.controls.dao.PersonaDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Persona;
import com.gestionCitas.models.enums.Genero;
import com.gestionCitas.models.enums.Identificacion;

public class PersonaServices {
    private PersonaDao obj;

    public PersonaServices() {
        this.obj = new PersonaDao();
    }

    public Persona getPersona() {
        return this.obj.getPersona();
    }

    public void setPersona(Persona Persona) {
        this.obj.setPersona(Persona);
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

    public Persona get(Integer id) throws Exception {
        return this.obj.get(id);
    }

    public Identificacion getIdentificacion(String tipo) {
        return this.obj.getTipoIdent(tipo);
    }

    public Identificacion[] getAllIdentificaciones() {
        return this.obj.getAllTiposIdent();
    }

    public Genero getGenero(String genero) {
        return this.obj.getGenero(genero);
    }

    public Genero[] getAllGeneros() {
        return this.obj.getAllGeneros();
    }
}
