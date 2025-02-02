package com.gestionCitas.controls;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    // Método para hacer hash de la contraseña
    public static String hashPassword(String password) {
        // Generar un salt con un coste de 12
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    // Método para verificar si la contraseña ingresada coincide con el hash almacenado
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
