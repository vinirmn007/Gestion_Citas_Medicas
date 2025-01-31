package com.gestionCitas.rest.APIs;

import java.util.HashMap;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.gestionCitas.controls.dao.services.CuentaServices;
import com.gestionCitas.controls.dao.services.MedicoServices;
import com.gestionCitas.models.Medico;

@Path("medicos")
public class MedicoAPI {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMeds() throws Exception {
        HashMap map = new HashMap<>();
        MedicoServices ms = new MedicoServices();

        map.put("msg", "OK");
        map.put("data", ms.getListAll().toArray());

        if (ms.getListAll().isEmpty()) {
            map.put("data", new Object[]{});
        }

        return Response.ok(map).build();
    }

    @Path("/idens")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIden() {
        HashMap map = new HashMap<>();
        MedicoServices ms = new MedicoServices();
        map.put("msg", "OK");
        map.put("data", ms.getAllIdentificaciones());
        return Response.ok(map).build();
    }

    @Path("/generos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGenre() {
        HashMap map = new HashMap<>();
        MedicoServices ms = new MedicoServices();
        map.put("msg", "OK");
        map.put("data", ms.getAllGeneros());
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveMeds(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            MedicoServices ms = new MedicoServices();
            CuentaServices cs = new CuentaServices();

            //VALIDACIONES
            if (cs.get(Integer.parseInt(map.get("cuentaId").toString())) == null) {
                res.put("msg", "Error");
                res.put("data", "No existe esa Cuenta");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            if (ms.getListAll().binarySearch("cuentaId", map.get("cuentaId").toString()) != null) {
                res.put("msg", "Error");
                res.put("data", "Ya existe una Persona con esa Cuenta");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            if (ms.getListAll().binarySearch("numeroIdentificacion", map.get("numeroIdentificacion").toString()) != null) {
                res.put("msg", "Error");
                res.put("data", "Ya existe una Persona con ese numero de identificacion");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                
            }

            if (ms.getListAll().binarySearch("email", map.get("email").toString()) != null) {
                res.put("msg", "Error");
                res.put("data", "Ya existe una Persona con ese email");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                
            }

            if (ms.getListAll().binarySearch("telefono", map.get("telefono").toString()) != null) {
                res.put("msg", "Error");
                res.put("data", "Ya existe una Persona con ese telefono");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build(); 
            }

            ms.getMedico().setNombres(map.get("nombres").toString());
            ms.getMedico().setApellidos(map.get("apellidos").toString());
            ms.getMedico().setEmail(map.get("email").toString());
            ms.getMedico().setDireccion(map.get("direccion").toString());
            ms.getMedico().setTelefono(map.get("telefono").toString());
            ms.getMedico().setFechaNacimiento(map.get("fechaNacimiento").toString());
            ms.getMedico().setNumeroIdentificacion(map.get("numeroIdentificacion").toString());
            ms.getMedico().setTipoIdentificacion(ms.getIdentificacion(map.get("tipoIdentificacion").toString()));
            ms.getMedico().setGenero(ms.getGenero(map.get("genero").toString()));
            ms.getMedico().setCuentaId(Integer.parseInt(map.get("cuentaId").toString()));
            ms.getMedico().setEspecialidad(map.get("especialidad").toString());
            ms.getMedico().setMatricula(map.get("matricula").toString());
            ms.save();

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
        MedicoServices ms = new MedicoServices();
        Medico medico = ms.get(id);

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
            MedicoServices ms = new MedicoServices();

            //VALIDACIONES
            if (ms.getListAll().binarySearch("email", map.get("email").toString()) != null 
            && !ms.get(Integer.parseInt(map.get("id").toString())).getEmail().equals(map.get("email").toString())) {
                res.put("msg", "Error");
                res.put("data", "Ya existe una Persona con ese email");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            if (ms.getListAll().binarySearch("telefono", map.get("telefono").toString()) != null 
            && !ms.get(Integer.parseInt(map.get("id").toString())).getTelefono().equals(map.get("telefono").toString())) {
                res.put("msg", "Error");
                res.put("data", "Ya existe una Persona con ese telefono");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build(); 
            }

            ms.setMedico(ms.get(Integer.parseInt(map.get("id").toString())));
            ms.getMedico().setEmail(map.get("email").toString());
            ms.getMedico().setDireccion(map.get("direccion").toString());
            ms.getMedico().setTelefono(map.get("telefono").toString());
            ms.update();

            res.put("msg", "OK");
            res.put("data", "Medico actualizado correctamente");

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
            MedicoServices ms = new MedicoServices();
            
            Integer id = Integer.parseInt(map.get("id").toString());
            Medico Medico = ms.get(id);

            if (Medico == null || Medico.getId() == null) {
                res.put("msg", "Error");
                res.put("data", "No existe esa Medico");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            ms.setMedico(Medico);
            ms.delete();

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
