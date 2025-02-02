package com.gestionCitas.rest.APIs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.gestionCitas.controls.dao.services.CuentaServices;
import com.gestionCitas.controls.dao.services.PersonaServices;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Cuenta;
import com.gestionCitas.models.Persona;
import com.gestionCitas.models.enums.Genero;
import com.gestionCitas.models.enums.Identificacion;

@Path("persona")
public class PersonaApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        HashMap map = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        map.put("msg", "OK");
        // TODO
        // map.put("data", ps.listAll().toArray());
        try {
            map.put("data", ps.listShowAll());
            if (ps.listAll().isEmpty()) {
                map.put("data", new Object[] {});
            }

        } catch (Exception e) {
            map.put("data", new Object[] {});
            System.out.println("Error " + e);
            // TODO: handle exception
        }
        return Response.ok(map).build();
    }

    @Path("/saveP")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveP(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();

        try {
            // Verificar si los datos son válidos
            if (map == null || !map.containsKey("cuenta") || !map.containsKey("persona")) {
                res.put("msg", "Error");
                res.put("data", "Faltan datos de la cuenta o de la persona");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            // Obtener y validar los datos de la cuenta
            HashMap<String, Object> cuentaMap = (HashMap<String, Object>) map.get("cuenta");
            if (cuentaMap == null || !cuentaMap.containsKey("usuario") || !cuentaMap.containsKey("contrasena")) {
                res.put("msg", "Error");
                res.put("data", "Faltan datos de la cuenta");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            String usuario = cuentaMap.get("usuario").toString();
            String contrasena = cuentaMap.get("contrasena").toString();

            // Asignar id_rol con un valor por defecto de 3 si no se proporciona
            Integer idRol = 3; // Rol por defecto (paciente)
            if (cuentaMap.containsKey("id_rol")) {
                idRol = Integer.parseInt(cuentaMap.get("id_rol").toString());
            }

            // Crear servicios de cuenta
            CuentaServices cuentaServices = new CuentaServices();

            // Verificar si la cuenta ya existe por el usuario
            LinkedList<Cuenta> cuentas = cuentaServices.listAll();
            for (int i = 0; i < cuentas.getSize(); i++) {
                Cuenta cuenta = cuentas.get(i);
                if (cuenta.getUsuario().equals(usuario)) {
                    res.put("msg", "Error");
                    res.put("data", "Ya existe el usuario, por favor inicia sesion");
                    return Response.status(Status.BAD_REQUEST).entity(res).build();
                }
            }

            // Obtener datos de la persona
            HashMap<String, Object> personaMap = (HashMap<String, Object>) map.get("persona");
            PersonaServices personaServices = new PersonaServices();

            String cedula = personaMap.getOrDefault("numeroIdentificacion", "").toString();
            String correo = personaMap.getOrDefault("email", "").toString(); // Obtener el correo
            // Validar que la cédula tenga un formato válido
            if (!esCedulaValida(cedula)) {
                res.put("msg", "Error");
                res.put("data", "Cédula inválida");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            // Verificar si la persona con esa cédula ya existe
            LinkedList<Persona> personas = personaServices.listAll();
            for (int i = 0; i < personas.getSize(); i++) {
                if (personas.get(i).getNumeroIdentificacion().equals(cedula)) {
                    res.put("msg", "Error");
                    res.put("data", "La persona con esta cédula ya está registrada");
                    return Response.status(Status.BAD_REQUEST).entity(res).build();
                }
                // Verificar si el correo ya está registrado
                if (personas.get(i).getEmail().equals(correo)) {
                    res.put("msg", "Error");
                    res.put("data", "El correo ya está registrado");
                    return Response.status(Status.BAD_REQUEST).entity(res).build();
                }
            }

            // Si la cuenta no existe, crearla
            Cuenta nuevaCuenta = new Cuenta(null, usuario, contrasena, idRol);
            cuentaServices.setCuenta(nuevaCuenta);

            String fechaNacimientoStr = personaMap.getOrDefault("fechaNacimiento", "").toString();

            // Validar y formatear la fecha
            String fechaFormateada = formatearFechaNacimiento(fechaNacimientoStr);
            if (fechaFormateada == null) {
                res.put("msg", "Error");
                res.put("data", "Formato de fecha inválido. Use dd-MM-yyyy.");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            // Crear el objeto Persona
            Persona persona = new Persona();
            persona.setNombre(personaMap.getOrDefault("nombre", "").toString());
            persona.setEmail(personaMap.getOrDefault("email", "").toString());
            persona.setCelular(personaMap.getOrDefault("celular", "").toString());
            persona.setFechaNacimiento(fechaFormateada);

            try {
                persona.setGenero(Genero.valueOf(personaMap.get("genero").toString().toUpperCase()));
            } catch (IllegalArgumentException | NullPointerException e) {
                res.put("msg", "Error");
                res.put("data", "Género inválido");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            persona.setNumeroIdentificacion(cedula);
            try {
                persona.setTipoIdentificacion(
                        Identificacion.valueOf(personaMap.get("tipoIdentificacion").toString().toUpperCase()));
            } catch (IllegalArgumentException | NullPointerException e) {
                res.put("msg", "Error");
                res.put("data", "Tipo de identificación inválido");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            cuentaServices.setCuenta(nuevaCuenta);
            cuentaServices.save(); // Asegúrate de guardar la cuenta antes de asignar su ID a la persona

            // Ahora asigna el id de la cuenta a la persona
            persona.setId_cuenta(nuevaCuenta.getId());
            personaServices.save(persona); // Guardar la persona después de asignar el ID de la cuenta

            // Guardar la persona
            personaServices.save(persona);

            res.put("msg", "OK");
            res.put("data", "Usuario y persona registrados correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            System.err.println("Error en save data: " + e.getMessage());
            e.printStackTrace();

            res.put("msg", "Error");
            res.put("data", "Error al procesar la solicitud: " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    /**
     * Método para validar y formatear la fecha en dd-MM-yyyy
     */
    private String formatearFechaNacimiento(String fechaNacimientoStr) {
        try {
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd-MM-yyyy");
            formatoEntrada.setLenient(false); // Evita fechas inválidas como 30/02/2024

            // Parsear la fecha para verificar si es válida
            Date fecha = formatoEntrada.parse(fechaNacimientoStr);

            // Convertir la fecha a String en el mismo formato
            return formatoEntrada.format(fecha);
        } catch (Exception e) {
            return null; // Si la fecha es inválida, retorna null
        }
    }

    /**
     * Método para validar el formato de la cédula.
     * Se puede ajustar según el país o requerimientos específicos.
     */
    private boolean esCedulaValida(String cedula) {
        if (cedula == null || !cedula.matches("\\d{10}")) {
            return false; // Debe tener 10 dígitos numéricos
        }

        // Validación específica (Ejemplo: Algoritmo de validación de cédula
        // ecuatoriana)
        int suma = 0;
        int[] coeficientes = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
        int verificador = Character.getNumericValue(cedula.charAt(9));

        for (int i = 0; i < coeficientes.length; i++) {
            int valor = Character.getNumericValue(cedula.charAt(i)) * coeficientes[i];
            if (valor > 9)
                valor -= 9;
            suma += valor;
        }

        int digitoCalculado = (10 - (suma % 10)) % 10;

        return digitoCalculado == verificador;
    }
}
