package com.gestionCitas.controls.dao.implement;

import com.gestionCitas.models.Persona;
import com.gestionCitas.controls.estructures.list.LinkedList;

public class PersonaDAO extends AdapterDao<Persona> {

    public PersonaDAO() {
        super(Persona.class);
    }
    /*
    public Persona findById(int id) throws Exception {
        LinkedList<Persona> personas = listAll();
        for (int i = 0; i < personas.size(); i++) {
            Persona persona = personas.get(i);
            if (persona.getId() == id) {
                return persona;
            }
        }
        return null;
    }

    public LinkedList<Persona> findByName(String nombre) throws Exception {
        LinkedList<Persona> personas = listAll();
        LinkedList<Persona> resultado = new LinkedList<>();

        for (int i = 0; i < personas.size(); i++) {
            Persona persona = personas.get(i);
            if (persona.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(persona);
            }
        }
        return resultado;
    }*/
}
