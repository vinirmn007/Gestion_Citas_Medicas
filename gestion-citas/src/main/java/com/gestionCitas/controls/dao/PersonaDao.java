package com.gestionCitas.controls.dao;

import com.gestionCitas.controls.dao.implement.AdapterDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Persona;

public class PersonaDao extends AdapterDao<Persona> {
    
    private Persona persona;
    private LinkedList<Persona> listAll;
    public PersonaDao() {
        super(Persona.class);
    }
    public Persona getPersona() {
        if (persona == null) {
            persona = new Persona();
        }
        return this.persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    public LinkedList<Persona> getListAll() {
        if(listAll == null){
            this.listAll = (LinkedList<Persona>)listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize()+1;
        persona.setId(id);
        this.persist(this.persona);
        return true;
    }

    public Boolean save(Persona persona) throws Exception {
        Integer id = getListAll().getSize()+1;
        persona.setId(id);
        this.persist(persona);
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getPersona(), getPersona().getId());
        return true;
    }



}

