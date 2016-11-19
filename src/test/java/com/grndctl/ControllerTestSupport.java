package com.grndctl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

/**
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GrndCtl.class)
@WebIntegrationTest
public abstract class ControllerTestSupport extends TestProperties {

    protected static final Logger LOG = LogManager.getLogger(ControllerTestSupport.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * The {@link RestTemplate} used to make calls to the integration testing server.
     */
    private RestTemplate restTemplate = new TestRestTemplate();

    /**
     * Obtain a reference to the static {@link RestTemplate} in order to make calls to the integration testing server.
     * @return A reference to the {@link RestTemplate}
     */
    public RestTemplate rest() {
        return this.restTemplate;
    }

    /**
     * Obtain a reference to the {@link ObjectMapper} member of this class in order to serialize/deserialize objects
     * created from the {@link #restTemplate}
     * @return A reference to the {@link ObjectMapper} used to serialize/deserialize
     */
    public ObjectMapper om() {
        return OBJECT_MAPPER;
    }

    /**
     * A utility method to obtain a reference to the harnesses base URL
     * @return A {@link URL} that is a complex type representing the testing server base URL
     */
    public static URL baseUrl() {
        return BASE_URL;
    }

    public static URL addUrlSpecs(String... specs) {
        List<String> specList = Arrays.asList(specs);
        String specStr = "/" + String.join("/", specList);
        try {
            return new URL(baseUrl(), specStr);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static URL addQueryParams(String...queryParam) {
        List<String> paramList = Arrays.asList(queryParam);
        StringBuilder paramString = new StringBuilder();
        return null;
    }
}
