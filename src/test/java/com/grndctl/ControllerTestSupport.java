/**
 * MIT License
 *
 * Copyright (c) 2016 grndctl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Throwables.propagate;
import static java.lang.String.format;

/**
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GrndCtl.class)
@WebIntegrationTest
public abstract class ControllerTestSupport extends TestProperties {

    protected static final Logger LOG = LogManager.getLogger(ControllerTestSupport.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String Q = "?";

    private static final String A = "&";

    private static final String E  = "=";

    /**
     * The {@link RestTemplate} used to make calls to the integration testing server.
     */
    private RestTemplate restTemplate = new TestRestTemplate();

    /**
     * Obtain a reference to the static {@link RestTemplate} in order to make calls to the integration testing server.
     * @return A reference to the {@link RestTemplate}
     */
    protected RestTemplate rest() {
        return this.restTemplate;
    }

    /**
     * Obtain a reference to the {@link ObjectMapper} member of this class in order to serialize/deserialize objects
     * created from the {@link #restTemplate}
     * @return A reference to the {@link ObjectMapper} used to serialize/deserialize
     */
    protected ObjectMapper om() {
        return OBJECT_MAPPER;
    }

    /**
     * A utility method to obtain a reference to the harnesses base URL
     * @return A {@link URL} that is a complex type representing the testing server base URL
     */
    protected static URL baseUrl() {
        return BASE_URL;
    }

    protected static URL addPathParams(URL url, String... specs) {
        List<String> specList = Arrays.asList(specs);
        String specStr = "/" + String.join("/", specList);
        try {
            return new URL(url.toExternalForm() + specStr);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    protected static URL addQueryParams(URL specUrl, Map<String, String> queryParams) {
        StringBuilder paramString = new StringBuilder(Q); // ?
        queryParams.entrySet().forEach(e -> {
            paramString.append(e.getKey());
            paramString.append(E); // =
            paramString.append(e.getValue());
            paramString.append(A); // &
        });

        try {
            return new URL((specUrl.toString() + paramString.toString()));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    protected List<Object> getForListObject(URL reqUrl, Class conversionType) {
        List<Object> retList = new ArrayList<>();
        try {
            @SuppressWarnings({"unchecked"})
            List<Map<String, Object>> response = rest().getForObject(
                    reqUrl.toExternalForm(), List.class
            );

            response.forEach(t -> {
                retList.add(om().convertValue(t, conversionType));
            });
        } catch (Exception e) {
            propagate(e);
        }

        return retList;
    }

}
