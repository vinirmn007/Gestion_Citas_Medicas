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

import com.gestionCitas.controls.dao.services.CuentaServices;
import com.gestionCitas.controls.dao.services.RolServices;
import com.gestionCitas.models.Rol;
import com.google.gson.Gson;

@Path("cuenta")
public class CuentaApi{
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
                        cs.getCuenta().setId_rol(rolServices.getRol().getId());
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
    

}