package test;

import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.fge.jsonschema.SchemaVersion.DRAFTV4;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class ApiTest {

    private static final String ENDPOINT = "http://api.citybik.es/v2/networks";

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = ENDPOINT;

    }

    @Test
    void testFrankfurtIsInGermany() {
        RestAssured.get( "/visa-frankfurt").then().statusCode(200)
                .assertThat().body("network.location.country", equalTo("DE"));
    }

    @Test
    @Ignore("json schema not working")
    void assertVelobikeResult() {
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder().setValidationConfiguration(ValidationConfiguration.newBuilder().setDefaultVersion(DRAFTV4).freeze()).freeze();

        RestAssured.get( "/velobike-moscow").then().statusCode(200)
                .assertThat()
                .body("network.name", equalTo("Velobike"))
                .body("network.stations", hasSize(656))
                .body("network.location", matchesJsonSchemaInClasspath("location.json").using(jsonSchemaFactory));
    }

    @Test
    public void testUnder2000msResponseTime() {
        Response response = RestAssured.get(ENDPOINT);
        Assertions.assertThat(response.time() < 2000).isTrue();
    }

    @Test
    void testLocationsAreCorrectFormat() {
        RestAssured.get( "?fields=location").then().statusCode(200)
                .assertThat().body("", hasSize(44));
    }
    //The numbers are in decimal degrees format and range from -90 to 90 for latitude and -180 to 180 for longitude.

    // Assert location contains all 4 values, city, latitude, country, longitude

    // assert that for each href in http://api.citybik.es/v2/networks, a correlating value is returned

}
