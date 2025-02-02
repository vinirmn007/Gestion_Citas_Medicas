package com.gestionCitas.controls.dao.services;

import java.util.HashMap;
import com.gestionCitas.controls.dao.PersonaDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Cuenta;
import com.gestionCitas.models.Persona;
import com.gestionCitas.models.Persona;
import com.gestionCitas.models.Rol;

public class PersonaServices {
    private PersonaDao obj;

    public Object[] listShowAll() throws Exception {
        if (!obj.getListAll().isEmpty()) {
            Persona[] lista = (Persona[]) obj.getListAll().toArray();
            Object[] respuesta = new Object[lista.length];
            for (int i = 0; i < lista.length; i++) {
                Cuenta c = new CuentaServices().get(lista[i].getId_cuenta());
                HashMap mapa = new HashMap<>();
                mapa.put("id", lista[i].getId());
                mapa.put("nombre", lista[i].getNombre());
                mapa.put("email", lista[i].getEmail());
                mapa.put("celular", lista[i].getCelular());
                mapa.put("cuenta", c);
                mapa.put("tipoIdentificacion", lista[i].getTipoIdentificacion());
                mapa.put("numeroIdentificacion", lista[i].getNumeroIdentificacion());
                mapa.put("genero", lista[i].getGenero());
                mapa.put("fechaNacimiento", lista[i].getFechaNacimiento());
                respuesta[i] = mapa;
            }
            return respuesta;
        }
        return new Object[] {};
    }

    public PersonaServices() {
        obj = new PersonaDao();
    }

    public Boolean save() throws Exception {
        return obj.save();
    }

    // Modify the save method to accept a Persona object as a parameter
    public Boolean save(Persona persona) throws Exception {
        return obj.save(persona);
    }

    public Boolean update() throws Exception {
        return obj.update();
    }

    public LinkedList listAll() {
        return obj.getListAll();
    }

    public Persona getPersona() {
        return obj.getPersona();
    }

    public void setPersona(Persona persona) {
        obj.setPersona(persona);
    }

    public Persona get(Integer id) throws Exception {
        return obj.get(id);
    }

}
