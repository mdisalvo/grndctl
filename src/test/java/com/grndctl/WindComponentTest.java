package com.grndctl;

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
