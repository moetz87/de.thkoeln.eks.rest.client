# RESTful Jersey-Client mit XML

Beispiel fuer einen Jersey-REST-Client mit XML am Beispiel des [RESTful Jersey-Webserver mit XML](https://github.com/moetz87/de.thkoeln.eks.rest.server).

## WebTarget

Die Klasse `WebTarget` aus der JAX-RS Spezifikation repraesentiert eine spezifische URI, auf die HTTP-Requests ausgefuehrt werden koennen. Um ein `WebTarget`-Objekt zu erstellen, existiert ein entsprechender Builder:


```java
String baseUrl = "http://localhost:55554";

WebTarget webTarget = ClientBuilder.newBuilder()
            .register(JacksonJaxbXMLProvider.class, MessageBodyReader.class, MessageBodyWriter.class)
            .build()
            .target(baseUrl);
```

Fuer Marshalling und Unmarshalling von Java-Objekten nach XML wird die Bibliothek *Jackson* verwendet. Hierfuer existiert ein `JacksonJaxbXMLProvider`, der die von JAX-RS vorgesehenen `MessageBodyReader` (Unmarshalling) und `MessageBodyWriter` (Marshalling) implementiert. Dieser muss registriert werden. Anschließend kann ueber eine Base-URL bestehend aus Host und Port eine `WebTarget`-Instanz erzeugt werden. 

## Requests

Auf dem zuvor erzeugten `WebTarget` koennen nun Requests ausgefuehrt werden. Auch hierfuer existieren wiederum Builder-Pattern:

```java
public List<Driver> getDriver() {
    return this.webTarget
            .path("drivers")
            .request()
            .accept(MediaType.APPLICATION_XML)
            .get()
            .readEntity( new GenericType<List<Driver>>(){} );
}

public Boolean addCarToDriver(String name, Car car) {
    return this.webTarget
            .path("drivers").path(name).path("cars")
            .request()
            .accept(MediaType.TEXT_PLAIN)
            .post(Entity.xml(car))
            .readEntity(Boolean.class);
}
```

* `path`: Definiert ausgehend von der Base-URL einen Pfad zu einer Ressource
* `request`: Erstellt ein `Request`-Objekt
* `accept`: Hier koennen verschiedene Media-Types angegeben werden, die im Response-Body akzeptiert werden
* `get`, `post`, `put`, `delete`: Definiert die HTTP-Operation. Im Falle von `post` und `put` koennen Entitaeten angegeben werden, die in der gewuenschten Repraesentation im Request-Body mitgesendet werden.
* `readEntity`: Fuehrt ein Unmarshalling des Response-Body in die angegebene Klasse durch 