package com.gestionCitas.rest.APIs;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.mindrot.jbcrypt.BCrypt;

import com.gestionCitas.controls.dao.services.CuentaServices;
import com.gestionCitas.controls.dao.services.RolServices;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Cuenta;
import com.gestionCitas.models.Rol;
import com.google.gson.Gson;

@Path("cuenta")
public class CuentaApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        HashMap map = new HashMap<>();
        CuentaServices ps = new CuentaServices();
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

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map) {
        HashMap res = new HashMap<>();
        Gson g = new Gson();

        try {
            if (map.get("rol") != null) {
                HashMap rolMap = (HashMap) map.get("rol"); // Acceder al objeto rol
                if (rolMap != null && rolMap.get("id") != null) {
                    Integer rolId = Integer.parseInt(rolMap.get("id").toString()); // Obtener el ID del rol
                    RolServices rolServices = new RolServices();
                    rolServices.setRol(rolServices.get(rolId));
                    if (rolServices.getRol().getId() != null) {
                        CuentaServices cs = new CuentaServices();
                        cs.getCuenta().setUsuario(map.get("usuario").toString());
                        cs.getCuenta().setContrasena(map.get("contrasena").toString());
                        cs.getCuenta().setId_rol(rolId);

                        cs.save();
                        res.put("msg", "OK");
                        res.put("data", "Cuenta registrada correctamente");
                        return Response.ok(res).build();
                    } else {
                        res.put("msg", "Error");
                        res.put("data", "El rol no existe");
                        return Response.status(Status.BAD_REQUEST).entity(res).build();
                    }
                } else {
                    res.put("msg", "Error");
                    res.put("data", "Faltan datos del rol");
                    return Response.status(Status.BAD_REQUEST).entity(res).build();
                }
            } else {
                res.put("msg", "Error");
                res.put("data", "Faltan datos");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }
        } catch (Exception e) {
            System.out.println("Error en save data " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(HashMap<String, String> credentials) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            String usuario = credentials.get("usuario");
            String contrasena = credentials.get("contrasena");

            if (usuario == null || contrasena == null) {
                response.put("msg", "Error");
                response.put("data", "Usuario y contraseña son requeridos");
                return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
            }

            CuentaServices cs = new CuentaServices();
            Cuenta cuenta = cs.findByUsuario(usuario);

            // Verificar si la cuenta existe y comparar las contraseñas
            if (cuenta != null && BCrypt.checkpw(contrasena, cuenta.getContrasena())) {
                response.put("msg", "OK");
                response.put("data", "Inicio de sesión exitoso");
                response.put("usuario", cuenta.getUsuario());
                response.put("id", cuenta.getId());
                response.put("rol", cuenta.getId_rol());
                return Response.ok(response).build();
            } else {
                response.put("msg", "Error");
                response.put("data", "Usuario o contraseña incorrectos");
                return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
            }
        } catch (Exception e) {
            response.put("msg", "Error");
            response.put("data", "Error al procesar la solicitud");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCuenta(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        CuentaServices ps = new CuentaServices();
        try {
            ps.setCuenta(ps.get(id));
        } catch (Exception e) {
            // TODO: handle exception
        }

        map.put("msg", "Ok");
        map.put("data", ps.getCuenta());
        if (ps.getCuenta().getId() == null) {
            map.put("data", "Cuenta no encontrado");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }
        return Response.ok(map).build();
    }

    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap<String, Object> map) {
        CuentaServices ps = new CuentaServices();
        RolServices rolServices = new RolServices();
        HashMap<String, Object> res = new HashMap<>();

        try {
            // Obtener el ID de la cuenta
            Integer cuentaId = Integer.parseInt(map.get("id").toString());
            Cuenta cuenta = ps.get(cuentaId);

            // Verificar si la cuenta existe
            if (cuenta == null) {
                res.put("msg", "Error");
                res.put("data", "Cuenta no encontrada con id " + cuentaId);
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            // Verificar si se envió un rol y actualizarlo solo si es necesario
            if (map.containsKey("rol")) {
                HashMap rolMap = (HashMap) map.get("rol");
                Integer rolId = Integer.parseInt(rolMap.get("id").toString());

                // Verificar si el rol existe
                Rol rol = rolServices.get(rolId);
                if (rol == null) {
                    res.put("msg", "Error");
                    res.put("data", "Rol no encontrado con id " + rolId);
                    return Response.status(Status.BAD_REQUEST).entity(res).build();
                }

                // Actualizar el ID del rol en la cuenta
                cuenta.setId_rol(rolId);
            }

            // Actualizar la cuenta en el archivo JSON
            ps.update(cuenta);

            System.out.println("Cuenta actualizada: " + cuenta);

            res.put("msg", "OK");
            res.put("data", "Rol actualizado correctamente");
            return Response.ok(res).build();

        } catch (NumberFormatException e) {
            res.put("msg", "Error");
            res.put("data", "Formato de número inválido: " + e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(res).build();

        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", "Error al actualizar la cuenta: " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

}
