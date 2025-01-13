package com.gestionCitas.rest.APIs;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.gestionCitas.controls.dao.services.PersonaServices;
import com.gestionCitas.models.Persona;

@Path("personas")
public class PersonaAPI {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() throws Exception {
        HashMap map = new HashMap<>();
        PersonaServices ps = new PersonaServices();

        map.put("msg", "OK");
        map.put("data", ps.getListAll().toArray());

        if (ps.getListAll().isEmpty()) {
            map.put("data", new Object[]{});
        }

        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response savePersons(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            PersonaServices ps = new PersonaServices();

            ps.getPersona().setNombre(map.get("nombre").toString());
            ps.getPersona().setEmail(map.get("email").toString());
            ps.getPersona().setDireccion(map.get("direccion").toString());
            ps.getPersona().setTelefono(map.get("telefono").toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ps.getPersona().setFechaNacimiento(sdf.parse(map.get("fechaNacimiento").toString()));
            ps.getPersona().setNumeroIdentificacion(map.get("numeroIdentificacion").toString());
            ps.getPersona().setTipoIdentificacion(ps.getIdentificacion(map.get("tipoIdentificacion").toString()));
            ps.getPersona().setGenero(ps.getGenero(map.get("genero").toString()));
            ps.getPersona().setHistorialMedicoId(Integer.parseInt(map.get("historialMedicoId").toString()));
            ps.getPersona().setCuentaId(Integer.parseInt(map.get("cuentaId").toString()));
            ps.save();

            res.put("msg", "OK");
            res.put("data", "Persona registrada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCita(@PathParam("id") Integer id) throws Exception {
        HashMap map = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        Persona Persona = ps.get(id);

        if (Persona == null || Persona.getId() == null) {
            map.put("msg", "Error");
            map.put("data", "No existe esa Persona");
            return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
        }

        map.put("msg", "OK");
        map.put("data", Persona);

        return Response.ok(map).build();
    }
    
    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePersons(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            PersonaServices ps = new PersonaServices();

            ps.getPersona().setEmail(map.get("email").toString());
            ps.getPersona().setDireccion(map.get("direccion").toString());
            ps.getPersona().setTelefono(map.get("telefono").toString());
            ps.update();

            res.put("msg", "OK");
            res.put("data", "Persona actualizada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePersons(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            PersonaServices ps = new PersonaServices();
            
            Integer id = Integer.parseInt(map.get("id").toString());
            Persona Persona = ps.get(id);

            if (Persona == null || Persona.getId() == null) {
                res.put("msg", "Error");
                res.put("data", "No existe esa Persona");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            ps.setPersona(Persona);
            ps.delete();

            res.put("msg", "OK");
            res.put("data", "Persona eliminada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
}
