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
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.grndctl.model.flightplan.Navaid;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Michael Di Salvo
 */
@Service
public class NavaidSvc {

    private static final String NAVAID_URL = "src/main/resources/navaids.csv";

    private static final int COLUMN_COUNT = 20;

    private static ImmutableListMultimap<String, Navaid> IDENT_NAVAID_MAP;

    private static ImmutableListMultimap<String, Navaid> STATION_NAVAID_MAP;

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
        List<String[]> rows;
        try {
            CSVReader reader = new CSVReader(new FileReader(csvUrl));
            // read off the column headers...
            reader.readNext();
            rows = reader.readAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        transformRows(rows);
    }

    private static void transformRows(List<String[]> rows) {
        ListMultimap<String, Navaid> identNavaids = ArrayListMultimap.create();
        ListMultimap<String, Navaid> stationNavaids = ArrayListMultimap.create();
        for (String[] row : rows) {
            Navaid navaid = new Navaid();
            for (int i = 0; i < COLUMN_COUNT; i++) {
                switch (i) {
                    case (0):
                        navaid.setId(row[i]);
                        continue;
                    case (1):
                        navaid.setFilename(row[i]);
                        continue;
                    case (2):
                        navaid.setIdent(row[i]);
                        continue;
                    case (3):
                        navaid.setName(row[i]);
                        continue;
                    case (4):
                        navaid.setType(row[i]);
                        continue;
                    case (5):
                        navaid.setFrequencyKhz(row[i]);
                        continue;
                    case (6):
                        navaid.setLatitudeDeg(row[i]);
                        continue;
                    case (7):
                        navaid.setLongitudeDeg(row[i]);
                        continue;
                    case (8):
                        navaid.setElevationFt(row[i]);
                        continue;
                    case (9):
                        navaid.setIsoCountry(row[i]);
                        continue;
                    case (10):
                        navaid.setDmeFrequencyKhz(row[i]);
                        continue;
                    case (11):
                        navaid.setDmeChannel(row[i]);
                        continue;
                    case (12):
                        navaid.setDmeLatitudeDeg(row[i]);
                        continue;
                    case (13):
                        navaid.setDmeLongitudeDeg(row[i]);
                        continue;
                    case (14):
                        navaid.setDmeElevationFt(row[i]);
                        continue;
                    case (15):
                        navaid.setSlavedVariationDeg(row[i]);
                        continue;
                    case (16):
                        navaid.setMagneticVariationDeg(row[i]);
                        continue;
                    case (17):
                        navaid.setUsageType(row[i]);
                        continue;
                    case (18):
                        navaid.setPower(row[i]);
                        continue;
                    case (19):
                        navaid.setAssociatedAirport(row[i]);
                        break;
                }
            }
            identNavaids.put(navaid.getIdent(), navaid);
            stationNavaids.put(navaid.getAssociatedAirport(), navaid);

        }

        IDENT_NAVAID_MAP = ImmutableListMultimap.copyOf(identNavaids);
        STATION_NAVAID_MAP = ImmutableListMultimap.copyOf(stationNavaids);
    }

}
