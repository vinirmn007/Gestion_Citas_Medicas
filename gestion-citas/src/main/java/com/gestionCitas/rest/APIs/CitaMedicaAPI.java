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

import com.gestionCitas.controls.dao.services.CitaMedicaServices;
import com.gestionCitas.controls.dao.services.HistorialMedicoServices;
import com.gestionCitas.controls.dao.services.PersonaServices;
import com.gestionCitas.controls.dao.services.TurnoServices;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.CitaMedica;
import com.gestionCitas.models.HistorialMedico;
import com.gestionCitas.models.Persona;
import com.gestionCitas.models.Turno;
import com.gestionCitas.models.enums.Estado;

@Path("citasMedicas")
public class CitaMedicaAPI {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response saver() throws Exception {
        HashMap map = new HashMap<>();
        CitaMedicaServices cms = new CitaMedicaServices();

        map.put("msg", "OK");
        map.put("data", cms.getListAll().toArray());

        if (cms.getListAll().isEmpty()) {
            map.put("data", new Object[]{});
        }

        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveCitas(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            CitaMedicaServices cms = new CitaMedicaServices();
            TurnoServices ts = new TurnoServices();
            HistorialMedicoServices hms = new HistorialMedicoServices();

            //VALIDACIONES
            int turnoId = Integer.parseInt(map.get("turnoId").toString());
            Turno turno = ts.get(turnoId);
            if (turno == null) {
                res.put("msg", "Error");
                res.put("data", "No existe ese Turno");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            HistorialMedico historialMedico = (HistorialMedico) hms.binarySearch("pacienteId", turno.getIdPaciente());
            if (historialMedico == null) {
                res.put("msg", "Error");
                res.put("data", "No existe ese Historial Medico o no esta asociado a la persona correcta");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            if (turno.getEstado() != Estado.EN_ESPERA) {
                res.put("msg", "Error");
                res.put("data", "No se han asignado signos vitales al turno");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            //GUARDADO DE DATOS
            cms.getCitaMedica().setObservaciones(map.get("observaciones").toString());
            cms.getCitaMedica().setMotivo(map.get("motivo").toString());
            cms.getCitaMedica().setTurnoId(Integer.parseInt(map.get("turnoId").toString()));
            //cms.getCitaMedica().setSignosVitalesId(Integer.parseInt(map.get("signosVitalesId").toString()));
            cms.getCitaMedica().setHistorialMedicoId(historialMedico.getId());
            cms.save();

            //CAMBIAR ESTADO DEL TURNO
            ts.setTurno(ts.get(Integer.parseInt(map.get("turnoId").toString())));
            ts.getTurno().setEstado(Estado.FINALIZADO);
            ts.update();

            res.put("msg", "OK");
            res.put("data", "Cita registrada correctamente");

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
        CitaMedicaServices cms = new CitaMedicaServices();
        CitaMedica CitaMedica = cms.get(id);

        if (CitaMedica == null || CitaMedica.getId() == null) {
            map.put("msg", "Error");
            map.put("data", "No existe esa Cita");
            return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
        }

        map.put("msg", "OK");
        map.put("data", CitaMedica);

        return Response.ok(map).build();
    }

    @Path("/binarySearch/{key}/{value}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response binarySearchCitas(@PathParam("key") String key, @PathParam("value") String value) {
        HashMap map = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        try {
            CitaMedica cita = (CitaMedica) ps.getListAll().binarySearch(key, value);
            if (cita == null) {
                map.put("msg", "OK");
                map.put("msg", "Cita no encontrada");
                return Response.ok(map).build();
            }
            map.put("msg", "OK");
            map.put("data", cita);
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/linealSearch/{key}/{value}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response linearSearchCitas(@PathParam("key") String key, @PathParam("value") String value) {
        HashMap map = new HashMap<>();
        PersonaServices ps = new PersonaServices();

        map.put("msg", "OK");
        try {
            LinkedList<CitaMedica> citas = (LinkedList<CitaMedica>) ps.getListAll().linealSearch(key, value);
            if (citas.isEmpty()) {
                map.put("data", "Citas no encontradas");
                return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
            }
            map.put("data", citas.toArray());
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
        return Response.ok(map).build();
    }

    @Path("/searchByHistId/{value}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByHistId(@PathParam("value") String value) {
        HashMap map = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        try {
            LinkedList<CitaMedica> citas = (LinkedList<CitaMedica>) ps.getListAll().linealSearch("historialMedicoId", value);
            if (citas.isEmpty()) {
                map.put("msg", "OK");
                map.put("data", new Object[]{});
                return Response.ok(map).build();
            }
            map.put("msg", "OK");
            map.put("data", citas.toArray());
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/orderBy/{key}/{type}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response orderByCitas(@PathParam("key") String key, @PathParam("type") Integer type) {
        HashMap map = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        try {
            LinkedList<CitaMedica> citas = (LinkedList<CitaMedica>) ps.getListAll().order(key, type);
            map.put("msg", "OK");
            map.put("data", citas.toArray());
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }
    /* 
    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCitas(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            CitaMedicaServices cms = new CitaMedicaServices();

            cms.getCitaMedica().setObservaciones(map.get("observaciones").toString());
            cms.getCitaMedica().setMotivo(map.get("motivo").toString());
            cms.update();

            res.put("msg", "OK");
            res.put("data", "Cita actualizada correctamente");

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
    public Response deleteCitas(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            CitaMedicaServices cms = new CitaMedicaServices();
            
            Integer id = Integer.parseInt(map.get("id").toString());
            CitaMedica CitaMedica = cms.get(id);

            if (CitaMedica == null || CitaMedica.getId() == null) {
                res.put("msg", "Error");
                res.put("data", "No existe esa Cita");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            cms.setCitaMedica(CitaMedica);
            cms.delete();

            res.put("msg", "OK");
            res.put("data", "Cita eliminada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }*/
}
