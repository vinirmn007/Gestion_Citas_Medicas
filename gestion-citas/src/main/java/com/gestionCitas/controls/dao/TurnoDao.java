package com.gestionCitas.controls.dao;

import com.gestionCitas.controls.dao.implement.AdapterDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import java.util.Iterator;
import com.gestionCitas.models.Turno;
import com.gestionCitas.models.enums.Estado;
public class TurnoDao extends AdapterDao<Turno> {
    private Turno turno;
    private LinkedList<Turno> listAll;

    public TurnoDao() {
        super(Turno.class);
    }

    public Turno getTurno() {
        if (turno == null) {
            this.turno = new Turno();
        }
        return this.turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public LinkedList getListAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return this.listAll;
    }

    /**
     * Guarda un turno en la base de datos.
     * @return true si se guarda correctamente, false en caso de error.
     * @throws Exception en caso de error durante la persistencia.
     */
    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        try {
            turno.setId(id);
            if (turno.getEstado() == null) {
                turno.setEstado(Estado.RESERVADO); // Asignar un estado predeterminado si no está definido.
            }
            this.persist(this.turno);
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar el turno: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Actualiza un turno existente.
     * @return true si se actualiza correctamente, false en caso de error.
     * @throws Exception en caso de error durante la actualización.
     */
    public Boolean update() throws Exception {
        try {
            if (turno.getId() == 0) {
                throw new IllegalArgumentException("El ID del turno no puede ser 0 para actualizar.");
            }
            this.mergeById(this.turno, this.turno.getId());
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar el turno: " + e.getMessage());
            throw e;
        }
    }
    
    public Boolean delete() throws Exception {
        try {
            if (turno.getId() == 0) {
                throw new IllegalArgumentException("El ID del turno no puede ser 0 para eliminar.");
            }
            this.deleteById(this.turno.getId());
            return true;
        } catch (Exception e) {
            System.err.println("Error al eliminar el turno: " + e.getMessage());
            throw e;
        }
    }
}
