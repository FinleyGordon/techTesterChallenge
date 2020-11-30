package test;
import api.ApiReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ApiTest {

    @Test
    void testFrankfurtIsInGermany() throws IOException {
        Assertions.assertThat(ApiReader.getCountryOfCity("visa-frankfurt")).isEqualTo("DE");
    }


}
