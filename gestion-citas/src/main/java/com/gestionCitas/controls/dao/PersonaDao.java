package com.gestionCitas.dao;

import com.gestionCitas.models.Persona;
import java.util.ArrayList;
import java.util.List;

public class PersonaDao {
    private List<Persona> personas;

    public PersonaDao() {
        this.personas = new ArrayList<>();
    }

    public void crearPersona(Persona persona) {
        personas.add(persona);
    }

    public Persona leerPersona(int id) {
        return personas.stream()
                .filter(persona -> persona.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void actualizarPersona(Persona personaActualizada) {
        for (int i = 0; i < personas.size(); i++) {
            if (personas.get(i).getId() == personaActualizada.getId()) {
                personas.set(i, personaActualizada);
                return;
            }
        }
    }

    public void eliminarPersona(int id) {
        personas.removeIf(persona -> persona.getId() == id);
    }

    public List<Persona> obtenerTodasLasPersonas() {
        return personas;
    }
}
