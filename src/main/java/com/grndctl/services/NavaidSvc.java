/**
 * MIT License
 *
 * Copyright (c) 2017 grndctl
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
import com.google.common.base.Strings;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.grndctl.model.flightplan.Navaid;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * All credit for data goes to the hardworking folk at openflights.org.
 *
 * @author Michael Di Salvo
 */
@Service
public class NavaidSvc {

    private static final String NAVAID_URL = "src/main/resources/navaids.csv";

    private static final int COLUMN_COUNT = 20;

    private static ImmutableListMultimap<String, Navaid> IDENT_NAVAID_MAP;

    private static ImmutableListMultimap<String, Navaid> STATION_NAVAID_MAP;

    @Cacheable("navaids")
    public Map<String, Collection<Navaid>> getAllNavaidsByIdent() {
        return IDENT_NAVAID_MAP.asMap();
    }

    public List<Navaid> getNavaidByIdent(String ident) {
        return IDENT_NAVAID_MAP.get(ident);
    }

    public List<Navaid> getNavaidsByAssociatedAirport(String station) {
        return STATION_NAVAID_MAP.get(station);
    }

    static {
        buildNavaidMaps(NAVAID_URL);
    }

    private static void buildNavaidMaps(String csvUrl) {
        try {
            try (CSVReader reader = new CSVReader(new FileReader(csvUrl))) {
                // read off the column headers...
                reader.readNext();
                transformRows(reader.readAll());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void transformRows(List<String[]> rows) {
        ListMultimap<String, Navaid> identNavaids = ArrayListMultimap.create();
        ListMultimap<String, Navaid> stationNavaids = ArrayListMultimap.create();
        rows.forEach(r -> {
            Navaid navaid = new Navaid();
            for (int i = 0; i < COLUMN_COUNT; i++) {
                switch (i) {
                    case (0):
                        navaid.setId(r[i]);
                        continue;
                    case (1):
                        navaid.setFilename(r[i]);
                        continue;
                    case (2):
                        navaid.setIdent(r[i]);
                        continue;
                    case (3):
                        navaid.setName(r[i]);
                        continue;
                    case (4):
                        navaid.setType(r[i]);
                        continue;
                    case (5):
                        if (!Strings.isNullOrEmpty(r[i])) {
                            navaid.setFrequencyKhz(Integer.parseInt(r[i]));
                        }
                        continue;
                    case (6):
                        if (!Strings.isNullOrEmpty(r[i])) {
                            navaid.setLatitudeDeg(Double.parseDouble(r[i]));
                        }
                        continue;
                    case (7):
                        if (!Strings.isNullOrEmpty(r[i])) {
                            navaid.setLongitudeDeg(Double.parseDouble(r[i]));
                        }
                        continue;
                    case (8):
                        if (!Strings.isNullOrEmpty(r[i])) {
                            navaid.setElevationFt(Integer.parseInt(r[i]));
                        }
                        continue;
                    case (9):
                        navaid.setIsoCountry(r[i]);
                        continue;
                    case (10):
                        if (!Strings.isNullOrEmpty(r[i])) {
                            navaid.setDmeFrequencyKhz(Integer.parseInt(r[i]));
                        }
                        continue;
                    case (11):
                        navaid.setDmeChannel(r[i]);
                        continue;
                    case (12):
                        if (!Strings.isNullOrEmpty(r[i])) {
                            navaid.setDmeLatitudeDeg(Double.parseDouble(r[i]));
                        }
                        continue;
                    case (13):
                        if (!Strings.isNullOrEmpty(r[i])) {
                            navaid.setDmeLongitudeDeg(Double.parseDouble(r[i]));
                        }
                        continue;
                    case (14):
                        if (!Strings.isNullOrEmpty(r[i])) {
                            navaid.setDmeElevationFt(Integer.parseInt(r[i]));
                        }
                        continue;
                    case (15):
                        if (!Strings.isNullOrEmpty(r[i])) {
                            navaid.setSlavedVariationDeg(Double.parseDouble(r[i]));
                        }
                        continue;
                    case (16):
                        if (!Strings.isNullOrEmpty(r[i])) {
                            navaid.setMagneticVariationDeg(Double.parseDouble(r[i]));
                        }
                        continue;
                    case (17):
                        navaid.setUsageType(r[i]);
                        continue;
                    case (18):
                        navaid.setPower(r[i]);
                        continue;
                    case (19):
                        navaid.setAssociatedAirport(r[i]);
                        break;
                }
            }
            identNavaids.put(navaid.getIdent(), navaid);
            stationNavaids.put(navaid.getAssociatedAirport(), navaid);
        });
        IDENT_NAVAID_MAP = ImmutableListMultimap.copyOf(identNavaids);
        STATION_NAVAID_MAP = ImmutableListMultimap.copyOf(stationNavaids);
    }

}
