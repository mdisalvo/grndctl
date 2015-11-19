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


import com.grndctl.model.metar.METAR;
import com.grndctl.model.taf.TAF;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * An object used to provide aggregate meteorological conditions for a field.
 * </p>
 *
 * @author Michael Di Salvo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"metars", "tafs"})
@XmlRootElement(name = "CombinedWx")
public class CombinedWx {

    @XmlElement(name = "metars")
    protected List<METAR> metars = new ArrayList<>(10);
    @XmlElement(name = "tafs")
    protected List<TAF> tafs = new ArrayList<>(10);

    public List<METAR> getMetars() {
        return metars;
    }

    public void setMetars(List<METAR> metars) {
        this.metars = metars;
    }

    public List<TAF> getTafs() {
        return tafs;
    }

    public void setTafs(List<TAF> tafs) {
        this.tafs = tafs;
    }

}
