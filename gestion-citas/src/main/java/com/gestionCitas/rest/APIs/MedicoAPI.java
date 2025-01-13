package com.gestionCitas.rest.APIs;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.gestionCitas.controls.dao.services.MedicoServices;
import com.gestionCitas.models.Medico;

@Path("medicos")
public class MedicoAPI {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMeds() throws Exception {
        HashMap map = new HashMap<>();
        MedicoServices ps = new MedicoServices();

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
    public Response saveMeds(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            MedicoServices ps = new MedicoServices();

            ps.getMedico().setNombre(map.get("nombre").toString());
            ps.getMedico().setEmail(map.get("email").toString());
            ps.getMedico().setDireccion(map.get("direccion").toString());
            ps.getMedico().setTelefono(map.get("telefono").toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ps.getMedico().setFechaNacimiento(sdf.parse(map.get("fechaNacimiento").toString()));
            ps.getMedico().setNumeroIdentificacion(map.get("numeroIdentificacion").toString());
            ps.getMedico().setTipoIdentificacion(ps.getIdentificacion(map.get("tipoIdentificacion").toString()));
            ps.getMedico().setGenero(ps.getGenero(map.get("genero").toString()));
            ps.getMedico().setHistorialMedicoId(Integer.parseInt(map.get("historialMedicoId").toString()));
            ps.getMedico().setCuentaId(Integer.parseInt(map.get("cuentaId").toString()));
            ps.getMedico().setEspecialidad(map.get("especialidad").toString());
            ps.getMedico().setMatricula(map.get("matricula").toString());
            ps.save();

            res.put("msg", "OK");
            res.put("data", "Medico registrado correctamente");

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
        MedicoServices ps = new MedicoServices();
        Medico medico = ps.get(id);

        if (medico == null || medico.getId() == null) {
            map.put("msg", "Error");
            map.put("data", "No existe esa Medico");
            return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
        }

        map.put("msg", "OK");
        map.put("data", medico);

        return Response.ok(map).build();
    }
    
    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMeds(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            MedicoServices ps = new MedicoServices();

            ps.getMedico().setEmail(map.get("email").toString());
            ps.getMedico().setDireccion(map.get("direccion").toString());
            ps.getMedico().setTelefono(map.get("telefono").toString());
            ps.update();

            res.put("msg", "OK");
            res.put("data", "Medico actualizada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
    /* 
    @Path("/delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMeds(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            MedicoServices ps = new MedicoServices();
            
            Integer id = Integer.parseInt(map.get("id").toString());
            Medico Medico = ps.get(id);

            if (Medico == null || Medico.getId() == null) {
                res.put("msg", "Error");
                res.put("data", "No existe esa Medico");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            ps.setMedico(Medico);
            ps.delete();

            res.put("msg", "OK");
            res.put("data", "Medico eliminado correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }*/
}
