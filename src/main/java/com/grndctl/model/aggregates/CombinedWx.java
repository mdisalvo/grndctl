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
