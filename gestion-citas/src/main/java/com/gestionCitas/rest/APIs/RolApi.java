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
import com.gestionCitas.controls.dao.services.RolServices;


@Path("rol")
public class RolApi{
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRol(){
        HashMap<String, Object> map = new HashMap<>();
        RolServices rs = new RolServices();
        map.put("msg","OK");
        map.put("data",rs.listAll().toArray());
        if (rs.listAll().isEmpty()) {
            map.put("data",new Object[]{});
        }
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String, Object> map){
        RolServices rs = new RolServices();
        rs.getRol().setNombre(map.get("nombre").toString());

        HashMap<String, Object> res = new HashMap<>();
        try{
            rs.save();
            res.put("msg","OK");
            res.put("data", "Rol guardado");
        }catch(Exception e){
            res.put("msg","ERROR");
            res.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
        return Response.ok(res).build();
    }

}