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
package com.grndctl.unit;

import com.grndctl.model.aggregates.WindComponent;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Michael Di Salvo
 */
public class WindComponentTest {
    
    @Test
    public void testWindComponent() {
        double windDirection = (240.0 * Math.PI) / 180;
        double heading = (180.0 * Math.PI) / 180;
        double windSpeed = 15.0;
        
        double expectedHeadwind = Math.cos(windDirection - heading) * 15;
        double expectedCrosswind = Math.sin(windDirection - heading) * 15;
        
        WindComponent wc = new WindComponent(windSpeed, 240.0, 180);
        
        assertEquals(expectedHeadwind, wc.getHeadWind(), 0.1);
        assertEquals(expectedCrosswind, wc.getCrossWind(), 0.1);
    }
    
}
