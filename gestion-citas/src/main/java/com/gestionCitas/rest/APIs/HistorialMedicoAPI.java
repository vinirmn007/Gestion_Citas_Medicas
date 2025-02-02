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

import com.gestionCitas.controls.dao.services.HistorialMedicoServices;
import com.gestionCitas.controls.dao.services.PersonaServices;
import com.gestionCitas.models.CitaMedica;
import com.gestionCitas.models.HistorialMedico;

@Path("historialMedico")
public class HistorialMedicoAPI {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllHistoriales() throws Exception {
        HashMap map = new HashMap<>();
        HistorialMedicoServices hss = new HistorialMedicoServices();

        map.put("msg", "OK");
        map.put("data", hss.getListAll().toArray());

        if (hss.getListAll().isEmpty()) {
            map.put("data", new Object[]{});
        }

        return Response.ok(map).build();
    }

    @Path("/bloodType")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getType() {
        HashMap map = new HashMap<>();
        HistorialMedicoServices hss = new HistorialMedicoServices();
        map.put("msg", "OK");
        map.put("data", hss.getAllTiposSangre());
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveHistoriales(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            HistorialMedicoServices hss = new HistorialMedicoServices();
            PersonaServices ps = new PersonaServices();

            //VALIDACIONES
            if (ps.get(Integer.parseInt(map.get("pacienteId").toString())) == null) {
                res.put("msg", "Error");
                res.put("data", "No existe ese Paciente");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            if (hss.getListAll().binarySearch("pacienteId", map.get("pacienteId").toString()) != null) {
                res.put("msg", "Error");
                res.put("data", "Ya existe un Historial para ese Paciente");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            if (hss.getTipoSangre(map.get("tipo_sangre").toString()) == null) {
                res.put("msg", "Error");
                res.put("data", "Tipo de sangre no valido");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            if (Integer.parseInt(map.get("hijos").toString()) < 0 || Integer.parseInt(map.get("hijos").toString()) > 20) {
                res.put("msg", "Error");
                res.put("data", "Numero de hijos no valido");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                
            }
            if (map.get("alergias").toString().isEmpty() || map.get("antecedentes").toString().isEmpty()
                    || map.get("discapacidad").toString().isEmpty() || map.get("medicacion").toString().isEmpty()
                    || map.get("patologias").toString().isEmpty()) {
                res.put("msg", "Error");
                res.put("data", "Todos los campos son obligatorios");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            //GUARDADO DE DATOS
            hss.getHistorialMedico().setAlergias(map.get("alergias").toString());
            hss.getHistorialMedico().setAntecendentesFamiliares(map.get("antecedentes").toString());
            hss.getHistorialMedico().setDiscapacidad(map.get("discapacidad").toString());
            hss.getHistorialMedico().setMedicacionActual(map.get("medicacion").toString());
            hss.getHistorialMedico().setPatologiasPasadas(map.get("patologias").toString());
            hss.getHistorialMedico().setTipoSangre(hss.getTipoSangre(map.get("tipo_sangre").toString()));
            hss.getHistorialMedico().setNroHijjos(Integer.parseInt(map.get("hijos").toString()));            
            hss.getHistorialMedico().setPacienteId(Integer.parseInt(map.get("pacienteId").toString()));
            hss.save();

            //ACTUALIZACION DE PACIENTE
            ps.setPersona(ps.get(Integer.parseInt(map.get("pacienteId").toString())));
            ps.getPersona().setHistorialMedicoId(hss.getHistorialMedico().getId());
            ps.update();

            res.put("msg", "OK");
            res.put("data", "Historial registrada correctamente");

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
    public Response getHistorial(@PathParam("id") Integer id) throws Exception {
        HashMap map = new HashMap<>();
        HistorialMedicoServices hss = new HistorialMedicoServices();
        HistorialMedico HistorialMedico = hss.get(id);

        if (HistorialMedico == null || HistorialMedico.getId() == null) {
            map.put("msg", "Error");
            map.put("data", "No existe ese Historial");
            return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
        }

        map.put("msg", "OK");
        map.put("data", HistorialMedico);

        return Response.ok(map).build();
    }
    
    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateHistoriales(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            HistorialMedicoServices hss = new HistorialMedicoServices();

            hss.setHistorialMedico(hss.get(Integer.parseInt(map.get("id").toString())));
            hss.getHistorialMedico().setAlergias(map.get("alergias").toString());
            hss.getHistorialMedico().setAntecendentesFamiliares(map.get("antecedentes").toString());
            hss.getHistorialMedico().setMedicacionActual(map.get("medicacion").toString());
            hss.update();

            res.put("msg", "OK");
            res.put("data", "Historial actualizada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/search/{value}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByPersonaId(@PathParam("value") String value) {
        HashMap map = new HashMap<>();
        HistorialMedicoServices hms = new HistorialMedicoServices();
        try {
            HistorialMedico historial = (HistorialMedico) hms.getListAll().binarySearch("personaId", value);
            if (historial == null) {
                map.put("msg", "OK");
                map.put("data", "Historial no encontrado");
                return Response.ok(map).build();
            }
            map.put("msg", "OK");
            map.put("data", historial);
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    /* 
    @Path("/delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteHistoriales(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            HistorialMedicoServices hss = new HistorialMedicoServices();
            
            Integer id = Integer.parseInt(map.get("id").toString());
            HistorialMedico HistorialMedico = hss.get(id);

            if (HistorialMedico == null || HistorialMedico.getId() == null) {
                res.put("msg", "Error");
                res.put("data", "No existe ese Historial");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            hss.setHistorialMedico(HistorialMedico);
            hss.delete();

            res.put("msg", "OK");
            res.put("data", "Historial eliminada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }*/
}
