/**
 * This file is part of grndctl.
 *
 * grndctl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * grndctl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with grndctl.  If not, see <http://www.gnu.org/licenses/>.
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
