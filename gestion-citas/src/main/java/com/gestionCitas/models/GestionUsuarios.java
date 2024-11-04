/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.gestion.citas.model;

/**
 *
 * @author david
 */

import com.example.gestion.citas.estructuras.Lista;
import java.util.List;

public class GestionUsuarios {
    private Lista<Persona> usuarios;

    public GestionUsuarios() {
        usuarios = new Lista<>();
    }

    public Persona agregarUsuario(Persona persona) {
        usuarios.agregar(persona);
        return persona;
    }

    public List<Persona> obtenerTodos() {
        return usuarios.obtenerTodos();
    }
}