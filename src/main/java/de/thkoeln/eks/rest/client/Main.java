package de.thkoeln.eks.rest.client;

import de.thkoeln.eks.rest.client.client.RestClient;
import de.thkoeln.eks.rest.client.resources.Car;
import de.thkoeln.eks.rest.client.resources.Driver;

import java.util.List;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());
    private static final String BASEURL = "http://localhost:9998";

    public static void main(String[] args) {
        RestClient restClient = new RestClient(BASEURL);

        Driver michaelKnight = new Driver("Michael Knight");
        Car kitt = new Car("Pontiac", "Firebird");

        Boolean response = restClient.addDriver(michaelKnight);
        LOG.info("POST /drivers: " + response);
        response = restClient.addCarToDriver("Michael Knight", kitt);
        LOG.info("POST /drivers/Michael%20Knight/cars: " + response);
        List<Driver> drivers = restClient.getDriver();
        LOG.info("GET /drivers: " + drivers);
    }

}
