package com.gestionCitas.dao;

import com.gestionCitas.models.Medico;
import java.util.ArrayList;
import java.util.List;

public class MedicoDao {
    private List<Medico> medicos;

    public MedicoDao() {
        this.medicos = new ArrayList<>();
    }

    public void crearMedico(Medico medico) {
        medicos.add(medico);
    }

    public Medico leerMedico(int id) {
        return medicos.stream()
                .filter(medico -> medico.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void actualizarMedico(Medico medicoActualizado) {
        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getId() == medicoActualizado.getId()) {
                medicos.set(i, medicoActualizado);
                return;
            }
        }
    }

    public void eliminarMedico(int id) {
        medicos.removeIf(medico -> medico.getId() == id);
    }

    public List<Medico> obtenerTodosLosMedicos() {
        return medicos;
    }
}
