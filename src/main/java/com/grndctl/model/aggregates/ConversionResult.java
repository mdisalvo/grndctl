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
package com.grndctl.model.aggregates;

import javax.xml.bind.annotation.*;

/**
 * @author Michael Di Salvo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ConversionResult")
public class ConversionResult {

    private Number value;
    private String unit;
    private String result;

    public ConversionResult(Number value, String unit) {
        this.value = value;
        this.unit = unit;
        this.result = convertNumToString(value) + unit;
    }

    private static String convertNumToString(Number n) {
        String s = "";
        if (n instanceof Integer) { s = Integer.toString((Integer)n); }
        if (n instanceof Double) { s = Double.toString((Double)n); }
        if (n instanceof Long) { s = Long.toString((Long)n); }
        if (n instanceof Float) { s = Float.toString((Float)n); }
        return s;
    }

    public String getResult() {
        return result;
    }

    public Number getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }
}
