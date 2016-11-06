package de.thkoeln.eks.rest.client.client;

import com.fasterxml.jackson.jaxrs.xml.JacksonJaxbXMLProvider;
import de.thkoeln.eks.rest.client.resources.Car;
import de.thkoeln.eks.rest.client.resources.Driver;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import java.util.List;

public class RestClient {

    private final WebTarget webTarget;

    public RestClient(String baseUrl) {
        this.webTarget = ClientBuilder.newBuilder()
                .register(JacksonJaxbXMLProvider.class, MessageBodyReader.class, MessageBodyWriter.class)
                .build()
                .target(baseUrl);
    }

    public List<Driver> getDriver() {
        return this.webTarget
                .path("drivers")
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get()
                .readEntity(new GenericType<List<Driver>>() {
                });
    }

    public Boolean addDriver(Driver driver) {
        return this.webTarget
                .path("drivers")
                .request()
                .accept(MediaType.TEXT_PLAIN)
                .post(Entity.xml(driver))
                .readEntity(Boolean.class);
    }

    public Boolean addCarToDriver(String name, Car car) {
        return this.webTarget
                .path("drivers").path(name).path("cars")
                .request()
                .accept(MediaType.TEXT_PLAIN)
                .post(Entity.xml(car))
                .readEntity(Boolean.class);
    }


}
