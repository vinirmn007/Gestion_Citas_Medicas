package com.gestionCitas.controls.dao;

import com.gestionCitas.controls.dao.implement.AdapterDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Persona;
import com.gestionCitas.models.enums.Genero;
import com.gestionCitas.models.enums.Identificacion;

public class PersonaDAO extends AdapterDao<Persona> {
    private Persona persona;
    private LinkedList<Persona> listAll;

    public PersonaDAO() {
        super(Persona.class);
    }

    public Persona getPersona() {
        return this.persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public LinkedList<Persona> getListAll() {
        if (this.listAll == null) {
            this.listAll = listAll();
        }
        return this.listAll;
    }

    public Boolean save(Persona persona) throws Exception {
        Integer id = getListAll().getSize()+1;
        persona.setId(id);
        this.persist(persona);
        return true;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        try {
            persona.setId(id);
            this.persist(this.persona);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean update() throws Exception {
        try {
            this.mergeById(this.persona, this.persona.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete() throws Exception {
        try {
            this.deleteById(this.persona.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Identificacion getTipoIdent(String tipo) {
        return Identificacion.valueOf(tipo);
    }

    public Identificacion[] getAllTiposIdent() {
        return Identificacion.values();
    }

    public Genero getGenero(String tipo) {
        return Genero.valueOf(tipo);
    }

    public Genero[] getAllGeneros() {
        return Genero.values();
    }
    
}
