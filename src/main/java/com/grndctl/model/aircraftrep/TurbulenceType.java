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
public enum TurbulenceType {
    
    CAT("CAT"), CHOP("CHOP"), MWAVE("MWAVE");
    
    private String val;

    private TurbulenceType(String val) {
        this.val = val;
    }
    
    @Override
    public String toString() {
        return val;
    }

    public static TurbulenceType fromString(String val) {
        TurbulenceType[] types = TurbulenceType.values();

        TurbulenceType tt = null;
        for (TurbulenceType type : types) {
            if (type.toString().equals(val)) {
                tt = type;
                break;
            }
        }

        return tt;
    }
}
