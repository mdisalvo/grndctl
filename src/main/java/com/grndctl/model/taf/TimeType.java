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
package com.grndctl.model.taf;

/**
 * <p>
 * Possible time type parameters for retrieving a {@link TAF}.
 * </p>
 *
 * @author Michael Di Salvo
 */
public enum TimeType {

    ISSUE("issue"), VALID("valid");

    private String value;

    TimeType(String value) {
        this.value = value;
    }

    public String valueOf() {
        return value;
    }

}
