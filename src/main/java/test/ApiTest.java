package test;
import api.ApiReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ApiTest {

    @Test
    void testFrankfurtIsInGermany() throws IOException {
        Assertions.assertThat(ApiReader.getCountry("visa-frankfurt")).isEqualTo("DE");
    }

    //The numbers are in decimal degrees format and range from -90 to 90 for latitude and -180 to 180 for longitude.

    // Assert location contains all 4 values, city, latitude, country, longitude

    // assert that for each href in http://api.citybik.es/v2/networks, a correlating value is returned

    // check 200 status
}
