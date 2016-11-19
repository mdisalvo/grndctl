package com.grndctl;

import java.net.URL;

import static java.lang.String.format;
import static java.lang.System.*;

/**
 */
class TestProperties {

    protected static final String BEG_MSG;
    protected static final String END_MSG;

    protected static final String SERVER_PORT;
    protected static final String ICAO_CODE;
    protected static final String IATA_CODE;

    protected static final URL BASE_URL;

    static {
        BEG_MSG = "Beginning Test: %s...";
        END_MSG = "Ending Test: %s...";

        SERVER_PORT = getProperty("server.port", "9999");
        ICAO_CODE = getProperty("icao.code", "KIAD");
        IATA_CODE = getProperty("iata.code", "IAD");

        try {
            BASE_URL = new URL(format("http://localhost:%s/", SERVER_PORT));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
