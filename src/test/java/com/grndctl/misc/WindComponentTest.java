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
package com.grndctl.misc;

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
