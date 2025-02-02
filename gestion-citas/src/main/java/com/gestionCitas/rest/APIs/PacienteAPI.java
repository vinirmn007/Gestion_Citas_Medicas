package com.gestionCitas.rest.APIs;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.gestionCitas.controls.dao.services.CuentaServices;
import com.gestionCitas.controls.dao.services.PersonaServices;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Persona;

@Path("pacientes")
public class PacienteAPI {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() throws Exception {
        HashMap map = new HashMap<>();
        PersonaServices ps = new PersonaServices();

        map.put("msg", "OK");
        map.put("data", ps.getListAll().toArray());

        if (ps.getListAll().isEmpty()) {
            map.put("data", new Object[] {});
        }

        return Response.ok(map).build();
    }

    @Path("/age/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAge(@PathParam("id") Integer id) throws Exception {
        HashMap map = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        Persona Persona = ps.get(id);

        if (Persona == null || Persona.getId() == null) {
            map.put("msg", "Error");
            map.put("data", "No existe esa Persona");
            return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
        }

        try {
            Integer edad = ps.getAge(Persona.getFechaNacimiento());

            map.put("msg", "OK");
            map.put("data", edad);

            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/idens")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIden() {
        HashMap map = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        map.put("msg", "OK");
        map.put("data", ps.getAllIdentificaciones());
        return Response.ok(map).build();
    }

    @Path("/generos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGenre() {
        HashMap map = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        map.put("msg", "OK");
        map.put("data", ps.getAllGeneros());
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
            CuentaServices cs = new CuentaServices();

            // VALIDACIONES
            if (cs.get(Integer.parseInt(map.get("cuentaId").toString())) == null) {
                res.put("msg", "Error");
                res.put("data", "No existe esa Cuenta");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            if (ps.getListAll().binarySearch("cuentaId", map.get("cuentaId").toString()) != null) {
                res.put("msg", "Error");
                res.put("data", "Ya existe una Persona con esa Cuenta");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            if (ps.getListAll().binarySearch("numeroIdentificacion",
                    map.get("numeroIdentificacion").toString()) != null) {
                res.put("msg", "Error");
                res.put("data", "Ya existe una Persona con ese numero de identificacion");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();

            }

            if (ps.getListAll().binarySearch("email", map.get("email").toString()) != null) {
                res.put("msg", "Error");
                res.put("data", "Ya existe una Persona con ese email");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();

            }

            if (ps.getListAll().binarySearch("telefono", map.get("telefono").toString()) != null) {
                res.put("msg", "Error");
                res.put("data", "Ya existe una Persona con ese telefono");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            // GUARDADO
            ps.getPersona().setNombres(map.get("nombres").toString());
            ps.getPersona().setApellidos(map.get("apellidos").toString());
            ps.getPersona().setEmail(map.get("email").toString());
            ps.getPersona().setDireccion(map.get("direccion").toString());
            ps.getPersona().setTelefono(map.get("telefono").toString());
            ps.getPersona().setFechaNacimiento(map.get("fechaNacimiento").toString());
            ps.getPersona().setNumeroIdentificacion(map.get("numeroIdentificacion").toString());
            ps.getPersona().setTipoIdentificacion(ps.getIdentificacion(map.get("tipoIdentificacion").toString()));
            ps.getPersona().setGenero(ps.getGenero(map.get("genero").toString()));
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

            // VALIDACIONES
            if (ps.getListAll().binarySearch("email", map.get("email").toString()) != null
                    && !ps.get(Integer.parseInt(map.get("id").toString())).getEmail()
                            .equals(map.get("email").toString())) {
                res.put("msg", "Error");
                res.put("data", "Ya existe una Persona con ese email");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            if (ps.getListAll().binarySearch("telefono", map.get("telefono").toString()) != null
                    && !ps.get(Integer.parseInt(map.get("id").toString())).getTelefono()
                            .equals(map.get("telefono").toString())) {
                res.put("msg", "Error");
                res.put("data", "Ya existe una Persona con ese telefono");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            // ACTUALIZACION
            ps.setPersona(ps.get(Integer.parseInt(map.get("id").toString())));
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

    @Path("/binarySearch/{key}/{value}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response binarySearchPersons(@PathParam("key") String key, @PathParam("value") String value) {
        HashMap map = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        try {
            Persona Persona = (Persona) ps.getListAll().binarySearch(key, value);
            if (Persona == null) {
                map.put("msg", "Error");
                map.put("msg", "Persona no encontrada");
                return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
            }
            map.put("msg", "OK");
            map.put("data", Persona);

            ResponseBuilder responseBuilder = Response.ok(map)
                    .header("Access-Control-Allow-Origin", "*") // Permite cualquier origen
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS") // Métodos permitidos
                    .header("Access-Control-Allow-Headers", "Content-Type, Authorization"); // Encabezados permitidos

            return responseBuilder.build();
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/linealSearch/{key}/{value}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response linearSearchPersons(@PathParam("key") String key, @PathParam("value") String value) {
        HashMap map = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        try {
            LinkedList<Persona> personas = (LinkedList<Persona>) ps.getListAll().linealSearch(key, value);

            map.put("msg", "OK");

            if (personas.isEmpty()) {
                map.put("data", "Persona no encontrada");
                return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
            } else {
                map.put("data", personas.toArray());
            }
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }

        return Response.ok(map).build();
    }

    @Path("/orderBy/{key}/{type}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response orderByPersons(@PathParam("key") String key, @PathParam("type") Integer type) {
        HashMap map = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        try {
            LinkedList<Persona> personas = (LinkedList<Persona>) ps.getListAll().order(key, type);
            map.put("msg", "OK");
            map.put("data", personas.toArray());
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }
}
