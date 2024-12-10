/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestionCitas.models;

/**
 *
 * @author david
 */

import com.gestionCitas.controls.estructures.list.LinkedList;

public class GestionUsuarios {
    private LinkedList<Persona> usuarios;

    public GestionUsuarios() {
        usuarios = new LinkedList<>();
    }

    public Persona agregarUsuario(Persona persona) {
        usuarios.add(persona);
        return persona;
    }

    public LinkedList<Persona> obtenerTodos() {
        return usuarios;
    }
}