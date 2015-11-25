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
package com.grndctl.model.aircraftrep;

/**
 *
 */
public enum TurbulenceIntensity {
    
    LGT("LGT"),
    LGTMOD("LGT-MOD"),
    MOD("MOD"),
    MODSEV("MOD-SEV"),
    SEV("SEV"),
    SEVEXTRM("SEV-EXTRM"),
    EXTRM("EXTRM"), 
    NEG("NEG");
    
    private String val;

    private TurbulenceIntensity(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val;
    }

    public static TurbulenceIntensity fromString(String val) {
        TurbulenceIntensity[] intensities = TurbulenceIntensity.values();

        TurbulenceIntensity ti = null;
        for (TurbulenceIntensity intensity : intensities) {
            if (intensity.toString().equals(val)) {
                ti = intensity;
                break;
            }
        }

        return ti;
    }
}
