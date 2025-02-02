package com.gestionCitas.controls.dao.implement;

import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Persona;

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
    
}
