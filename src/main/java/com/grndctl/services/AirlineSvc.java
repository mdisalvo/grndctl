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
package com.grndctl.services;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.grndctl.model.misc.Airline;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Michael Di Salvo
 */
@Service
public class AirlineSvc {

    private static final String AIRLINE_URL = "src/main/resources/airlines.dat.csv";

    private static final int COLUMN_COUNT = 8;

    private static ImmutableMap<String, Airline> ICAO_NAVAID_MAP;

    private static ImmutableMap<String, Airline> IATA_NAVAID_MAP;

    private static ImmutableList<Airline> AIRLINE_LIST;

    public Airline getAirlineByIcao(String station) {
        return ICAO_NAVAID_MAP.get(station);
    }

    public Airline getAirlineByIata(String station) {
        return IATA_NAVAID_MAP.get(station);
    }

    public List<Airline> getActiveAirlines() {
        return AIRLINE_LIST
                .stream()
                .filter(a -> a.getActive().equals("Y"))
                .collect(Collectors.toList());
    }

    @Cacheable("airlines")
    public List<Airline> getAllAirlines() {
        return AIRLINE_LIST;
    }

    static {
        buildAirlineData(AIRLINE_URL);
    }

    private static void buildAirlineData(String csvUrl) {
        try {
            try (CSVReader reader = new CSVReader(new FileReader(csvUrl))) {
                transformRows(reader.readAll());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void transformRows(List<String[]> rows) {
        Map<String, Airline> icaoAirlines = new HashMap<>();
        Map<String, Airline> iataAirlines = new HashMap<>();
        List<Airline> airlines = new ArrayList<>();
        rows.forEach(r -> {
            Airline a = new Airline();
            for (int i = 0; i < COLUMN_COUNT; i++) {
                switch(i) {
                    case (0):
                        a.setId(r[i]);
                        continue;
                    case (1):
                        a.setName(r[i]);
                        continue;
                    case (2):
                        a.setAlias(r[i]);
                        continue;
                    case (3):
                        a.setIata(r[i]);
                        continue;
                    case (4):
                        a.setIcao(r[i]);
                        continue;
                    case (5):
                        a.setCallsign(r[i]);
                        continue;
                    case (6):
                        a.setCountry(r[i]);
                        continue;
                    case (7):
                        a.setActive(r[i]);
                        break;
                }
            }
            icaoAirlines.put(a.getIcao(), a);
            iataAirlines.put(a.getIata(), a);
            airlines.add(a);
        });
        airlines.sort(new Comparator<Airline>() {
            @Override
            public int compare(Airline o1, Airline o2) {
                return o1.getIcao().compareTo(o2.getIcao());
            }
        });
        ICAO_NAVAID_MAP = ImmutableMap.copyOf(icaoAirlines);
        IATA_NAVAID_MAP = ImmutableMap.copyOf(iataAirlines);
        AIRLINE_LIST = ImmutableList.copyOf(airlines);
    }
}
