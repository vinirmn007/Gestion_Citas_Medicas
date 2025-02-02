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

import com.gestionCitas.controls.dao.services.DiagnosticoServices;
import com.gestionCitas.controls.dao.services.ExamenServices;
import com.google.gson.Gson;

@Path("/examen")
public class ExamenApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDiagnositicos () throws Exception {
        HashMap map = new HashMap();
        ExamenServices es = new ExamenServices();
        
        map.put("msg", "OK");
        map.put("data", es.getListAll().toArray());

        if(es.getListAll().isEmpty()) {
            map.put("data", new Object[]{});
            map.put("msg", "No hay datos");
        }

        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiagnostico (@PathParam("id") Integer id) throws Exception {
        HashMap map = new HashMap();
        ExamenServices es = new ExamenServices();
        
        map.put("msg", "OK");
        map.put("data", es.get(id));
        
        if (es.get(id) == null) {
            map.put("msg", "Error");
            map.put("data", "Examen no encontrado");
            return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
        }

        return Response.ok(map).build();
    }

    @Path("/listTiposExamenes/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDiagnositicosType () throws Exception {
        HashMap map = new HashMap();
        ExamenServices es = new ExamenServices();

        map.put("msg", "OK");
        map.put("data", es.getTipos());
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveExamen(HashMap<String, Object> map) {
        HashMap<String, String> res = new HashMap<>();
        Gson g = new Gson();
    
        try {
            // Validar que el campo diagnosticoC esté presente
            if (!map.containsKey("diagnosticoC")) {
                res.put("msg", "Error al guardar");
                res.put("data", "El ID del diagnóstico es obligatorio.");
                System.err.println("Error en id no esta presente");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
    
            int idDiagnostico;
            try {
                idDiagnostico = Integer.parseInt(map.get("diagnosticoC").toString());
            } catch (NumberFormatException e) {
                System.out.println("Error en id no es un numero");
                res.put("msg", "Error al guardar");
                res.put("data", "El ID del diagnóstico debe ser un número válido.");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
    
            DiagnosticoServices cms = new DiagnosticoServices();
            cms.getDiagnostico().setId(idDiagnostico);
    
            // Verificar si el diagnóstico existe
            if (cms.get(idDiagnostico) == null) {
                System.out.println("Error en id no existe");
                res.put("msg", "Error al guardar");
                res.put("data", "El diagnóstico especificado no existe.");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
    
            // Validar otros campos necesarios
            if (!map.containsKey("descripcion") || !map.containsKey("tipoExamen") || !map.containsKey("nombreArchivo")) {
                System.out.println("Error en campos obligatorios");
                res.put("msg", "Error al guardar");
                res.put("data", "Faltan campos obligatorios (descripción, tipo de examen o nombre del archivo).");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
    
            // Crear y guardar el examen
            ExamenServices es = new ExamenServices();
            es.getExamen().setDescripcion(map.get("descripcion").toString());
            es.getExamen().setTipo(es.getTipoExamen(map.get("tipoExamen").toString()));
            es.getExamen().setNombreArchivo(map.get("nombreArchivo").toString());
            es.getExamen().setIdDiagnostico(idDiagnostico);
            es.save();

            System.out.println("Datos recibidos: " + g.toJson(map));

    
            res.put("msg", "OK");
            res.put("data", "Examen guardado exitosamente.");
            return Response.ok(res).build();
    
        } catch (Exception e) {
            System.err.println("Error al guardar examen: " + e.getMessage());
            res.put("msg", "Error al guardar");
            res.put("data", "Ocurrió un error inesperado: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }


}

