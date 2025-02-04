package com.gestionCitas.rest.APIs;

import java.util.HashMap;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.gestionCitas.controls.dao.services.MedicoServices;
import com.gestionCitas.controls.dao.services.PersonaServices;
import com.gestionCitas.controls.dao.services.TurnoServices;
import com.gestionCitas.models.enums.Estado;
import com.gestionCitas.models.Turno;
import com.gestionCitas.controls.estructures.list.LinkedList;

@Path("/turno")
public class TurnoApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listTurnos() {
        HashMap<String, Object> map = new HashMap<>();
        TurnoServices turnoServices = new TurnoServices();
        LinkedList<Turno> turnos = turnoServices.getListAll();
        map.put("msg", "Lista de turnos");
        map.put("data", turnos.isEmpty() ? new Object[]{} : turnos.toArray());
        return Response.ok(map).build();
    }

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTurno(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            TurnoServices turnoServices = new TurnoServices();
            PersonaServices personaServices = new PersonaServices();
            MedicoServices medicoServices = new MedicoServices();

            if (personaServices.get(Integer.parseInt(map.get("idPaciente").toString())) == null) {
                res.put("msg", "Error");
                res.put("data", "El paciente no existe");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            if (medicoServices.get(Integer.parseInt(map.get("idMedico").toString())) == null) {
                res.put("msg", "Error");
                res.put("data", "El médico no existe");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            if (map.get("fecha").toString().isEmpty() || map.get("hora").toString().isEmpty()) {
                res.put("msg", "Error");
                res.put("data", "La fecha y la hora no pueden estar vacías");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            if (!turnoServices.validateDateFormat(map.get("fecha").toString())) {
                res.put("msg", "Error");
                res.put("data", "Formato de fecha incorrecto");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            if (!turnoServices.validateTimeFormat(map.get("hora").toString())) {
                res.put("msg", "Error");
                res.put("data", "Formato de hora incorrecto");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            if (!turnoServices.validateRangeHour(map.get("hora").toString())) {
                res.put("msg", "Error");
                res.put("data", "Hora fuera de rango");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            if (turnoServices.getListAll().binarySearch("fecha", map.get("fecha").toString()) != null &&
                turnoServices.getListAll().binarySearch("hora", map.get("hora").toString()) != null) {
                res.put("msg", "Error");
                res.put("data", "Ya existe un turno para esa fecha y hora");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            
            turnoServices.getTurno().setIdMedico(Integer.parseInt(map.get("idMedico").toString()));
            turnoServices.getTurno().setIdPaciente(Integer.parseInt(map.get("idPaciente").toString()));
            turnoServices.getTurno().setFecha(map.get("fecha").toString());
            turnoServices.getTurno().setHora(map.get("hora").toString());
            turnoServices.getTurno().setEstado(Estado.RESERVADO);
            turnoServices.save();
            res.put("msg", "OK");
            res.put("data", "Turno creado exitosamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error al crear el turno");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTurno(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        TurnoServices turnoServices = new TurnoServices();
        try {
            Turno turno = turnoServices.get(id);
            if (turno != null) {
                map.put("msg", "OK");
                map.put("data", turno);
                return Response.ok(map).build();
            } else {
                map.put("msg", "Turno no encontrado");
                return Response.status(Response.Status.NOT_FOUND).entity(map).build();
            }
        } catch (Exception e) {
            map.put("msg", "Error al buscar el turno");
            map.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTurno(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            TurnoServices turnoServices = new TurnoServices();
            MedicoServices medicoServices = new MedicoServices();

            if (medicoServices.get(Integer.parseInt(map.get("idMedico").toString())) == null) {
                res.put("msg", "Error");
                res.put("data", "El médico no existe");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            if (map.get("fecha").toString().isEmpty() || map.get("hora").toString().isEmpty()) {
                res.put("msg", "Error");
                res.put("data", "La fecha y la hora no pueden estar vacías");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            if (!turnoServices.validateDateFormat(map.get("fecha").toString())) {
                res.put("msg", "Error");
                res.put("data", "Formato de fecha incorrecto");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            if (!turnoServices.validateTimeFormat(map.get("hora").toString())) {
                res.put("msg", "Error");
                res.put("data", "Formato de hora incorrecto");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            if (!turnoServices.validateRangeHour(map.get("hora").toString())) {
                res.put("msg", "Error");
                res.put("data", "Hora fuera de rango");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            if (turnoServices.getListAll().binarySearch("fecha", map.get("fecha").toString()) != null &&
                turnoServices.getListAll().binarySearch("hora", map.get("hora").toString()) != null) {
                res.put("msg", "Error");
                res.put("data", "Ya existe un turno para esa fecha y hora");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            turnoServices.setTurno(turnoServices.get(Integer.parseInt(map.get("id").toString())));
            turnoServices.getTurno().setFecha(map.get("fecha").toString());
            turnoServices.getTurno().setHora(map.get("hora").toString());
            turnoServices.getTurno().setIdMedico(Integer.parseInt(map.get("idMedico").toString()));
            turnoServices.update();
            res.put("msg", "OK");
            res.put("data", "Turno actualizado exitosamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error al actualizar el turno");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTurno(@PathParam("id") Integer id) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            TurnoServices turnoServices = new TurnoServices();
            turnoServices.getTurno().setId(id);
            turnoServices.delete();
            res.put("msg", "Turno eliminado exitosamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error al eliminar el turno");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/binarySearch/{key}/{value}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response binarySearchTurno(@PathParam("key") String key, @PathParam("value") String value) {
        HashMap<String, Object> map = new HashMap<>();
        TurnoServices turnoServices = new TurnoServices();
        try {
            Turno turno = (Turno) turnoServices.getListAll().binarySearch(key, value);
            if (turno == null) {
                map.put("msg", "OK");
                map.put("data", "Turno no encontrado");
                return Response.ok(map).build();
            }
            map.put("msg", "OK");
            map.put("data", turno);
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/linealSearch/{key}/{value}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response linearSearchTurno(@PathParam("key") String key, @PathParam("value") String value) {
        HashMap<String, Object> map = new HashMap<>();
        TurnoServices turnoServices = new TurnoServices();
        try {
            LinkedList<Turno> turnos = (LinkedList<Turno>) turnoServices.getListAll().linealSearch(key, value);
            if (turnos.isEmpty()) {
                map.put("msg", "OK");
                map.put("data", "Turno no encontrado");
                return Response.ok(map).build();
            }
            map.put("msg", "OK");
            map.put("data", turnos.toArray());
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/orderBy/{key}/{type}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response orderByTurno(@PathParam("key") String key, @PathParam("type") Integer type) {
        HashMap<String, Object> map = new HashMap<>();
        TurnoServices turnoServices = new TurnoServices();
        try {
            LinkedList<Turno> turnos = (LinkedList<Turno>) turnoServices.getListAll().order(key, type);
            map.put("msg", "OK");
            map.put("data", turnos.toArray());
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/cancel")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelTurno(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            TurnoServices turnoServices = new TurnoServices();
            turnoServices.getTurno().setId(Integer.parseInt(map.get("id").toString()));
            turnoServices.getTurno().setEstado(Estado.CANCELADO);
            turnoServices.update();
            res.put("msg", "OK");
            res.put("data", "Turno cancelado exitosamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }


    @Path("/getByEstado/{estado}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByEstado(@PathParam("estado") String estado) {
        HashMap<String, Object> map = new HashMap<>();
        TurnoServices turnoServices = new TurnoServices();
        try {
            LinkedList<Turno> turnos = (LinkedList<Turno>) turnoServices.getListAll().linealSearch("estado", Estado.valueOf(estado.toUpperCase()));
            if (turnos.isEmpty()) {
                map.put("msg", "OK");
                map.put("data", "Turno no encontrado");
                return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
            }
            LinkedList<Turno> turnosOrd = turnoServices.orderByFecha(turnos, 0);
            map.put("msg", "OK");
            map.put("data", turnosOrd.toArray());
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/pac/getByEstado/{pacienteId}/{estado}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByEstadoPaciente(@PathParam("pacienteId") Integer pacienteId, @PathParam("estado") String estado) {
        HashMap<String, Object> map = new HashMap<>();
        TurnoServices turnoServices = new TurnoServices();
        try {
            LinkedList<Turno> turnos = (LinkedList<Turno>) turnoServices.getListAll().linealSearch("idPaciente", pacienteId);
            if (turnos.isEmpty()) {
                map.put("msg", "OK");
                map.put("data", "Turno no encontrado");
                return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
            }
            LinkedList<Turno> turnosEstado = (LinkedList<Turno>) turnos.linealSearch("estado", Estado.valueOf(estado.toUpperCase()));
            if (turnosEstado.isEmpty()) {
                map.put("msg", "OK");
                map.put("data", "Turno no encontrado");
                return Response.status(Response.Status.BAD_REQUEST).entity(map).build();

            }
            LinkedList<Turno> turnosOrd = turnoServices.orderByFecha(turnosEstado, 0);
            map.put("msg", "OK");
            map.put("data", turnosOrd.toArray());
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }
}
