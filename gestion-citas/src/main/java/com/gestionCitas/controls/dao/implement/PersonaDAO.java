package com.gestionCitas.controls.dao.implement;

import com.gestionCitas.models.Persona;
import com.gestionCitas.controls.estructures.list.LinkedList;

public class PersonaDAO extends AdapterDao<Persona> {

    public PersonaDAO() {
        super(Persona.class);
    }

    /**
     * Encuentra una Persona por su ID.
     * @param id ID de la persona que se desea buscar.
     * @return Objeto Persona correspondiente al ID, o null si no se encuentra.
     * @throws Exception si ocurre un error al acceder a los datos.
     */
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

    /**
     * Encuentra personas por su nombre.
     * @param nombre Nombre a buscar (se admite coincidencia parcial).
     * @return Lista de personas que coinciden con el nombre.
     * @throws Exception si ocurre un error al acceder a los datos.
     */
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
    }
}
