package com.gestionCitas.controls.dao.implement;

import com.gestionCitas.models.Persona;
import com.gestionCitas.controls.estructures.list.LinkedList;

public class PersonaDAO {
    private Persona persona;
    private LinkedList<Persona> listAll;

    public PersonaDAO() {
        this.listAll = new LinkedList<>();
    }

    public Boolean save() throws Exception {
        try {
            int id = listAll.size() + 1;
            persona.setNumeroIdentificacion(String.valueOf(id));
            listAll.add(persona);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean update() throws Exception {
        try {
            for (int i = 0; i < listAll.size(); i++) {
                if (listAll.get(i).getNumeroIdentificacion().equals(persona.getNumeroIdentificacion())) {
                    listAll.set(i, persona);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete() throws Exception {
        try {
            for (int i = 0; i < listAll.size(); i++) {
                if (listAll.get(i).getNumeroIdentificacion().equals(persona.getNumeroIdentificacion())) {
                    listAll.remove(i);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
