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
package com.grndctl.model.flightplan;

/**
 */
public class Notam {

    public enum FormatType {

        DOMESTIC("DOMESTIC"), ICAO("ICAO");

        private String val;

        FormatType(String val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return val;
        }

        public static FormatType fromString(String val) {
            FormatType[] types = FormatType.values();

            FormatType t = null;
            for (FormatType type : types) {
                if (type.toString().equals(val)) {
                    t = type;
                    break;
                }
            }

            return t;
        }
    }

    public enum ReportType {

        REPORT("REPORT"), RAW("RAW");

        private String val;

        ReportType(String val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return val;
        }

        public static ReportType fromString(String val) {
            ReportType[] types = ReportType.values();

            ReportType rt = null;
            for (ReportType type : types) {
                if (type.toString().equals(val)) {
                    rt = type;
                    break;
                }
            }

            return rt;
        }

    }

}
