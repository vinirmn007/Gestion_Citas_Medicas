package com.gestionCitas.rest;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/myapp/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.gestionCitas.rest package
        final ResourceConfig rc = new ResourceConfig().packages("com.gestionCitas.rest");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */

    
    public static void main(String[] args) throws Exception {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        /*
        DiagnosticoServices ds = new DiagnosticoServices();
        ds.getDiagnostico().setDescripcion("Dolor Neurálgico");
        ds.getDiagnostico().setIdCitaMedica(2);
        ds.save();
        

        // Crear una receta
        RecetaServices rs = new RecetaServices();
        rs.getReceta().setPrescripcion("Tomar 2 pastillas de acetaminofeno");
        rs.getReceta().setIdDiagnostico(2);
        rs.getReceta().setIdMedicamentos(new Integer[]{1, 2});
        rs.save();
        
        /*
        MedicamentoServices ms = new MedicamentoServices();
        ms.getMedicamento().setNombre("Paracetamol");
        ms.getMedicamento().setDosis("600 mg");
        ms.save();
        */
        System.in.read();
        server.stop();
    }
}

