package com.gestionCitas.controls.dao.implement;

import com.gestionCitas.models.Persona;
import com.gestionCitas.controls.estructures.list.LinkedList;

<<<<<<< HEAD
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
=======
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
>>>>>>> origin/David
    }
}
