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
package com.grndctl.controllers;

import com.grndctl.ControllerTestSupport;
import com.grndctl.ExceptionModel;
import com.grndctl.model.station.FaaStation;
import com.grndctl.model.station.Station;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 */
public class StationControllerTest extends ControllerTestSupport {

  private static final String TEST_NAME = StationControllerTest.class.getSimpleName();

  private static URL STATION_RESOURCE;

  @BeforeClass
  public static void setUp() {
    LOG.info(format(BEG_MSG, TEST_NAME));
    STATION_RESOURCE = addPathParams(baseUrl(), "station");
  }

  @AfterClass
  public static void tearDown() {
    LOG.info(format(END_MSG, TEST_NAME));
  }

  @Test
  public void testGetStationInfo404() throws Exception {
    URL url = addPathParams(STATION_RESOURCE, "adds");
    url = addPathParams(url, BAD_ICAO_CODE);
    LOG.info(url.toString());

    ExceptionModel e = rest().getForObject(url.toExternalForm(), ExceptionModel.class);

    assert e.getStatus() == 404;
  }

  @Test
  @Ignore
  public void testGetFAAStationStatus() throws Exception {
    URL url = addPathParams(STATION_RESOURCE, "faa");
    url = addPathParams(url, IATA_CODE);
    LOG.info(url.toString());

    FaaStation station = rest().getForObject(url.toExternalForm(), FaaStation.class);

    assert (station != null);
    assert (station.getIata().equals(IATA_CODE));
  }

  @Test
  public void testGetFAAStationStatus404() throws Exception {
    URL url = addPathParams(STATION_RESOURCE, "faa");
    url = addPathParams(url, BAD_IATA_CODE);
    LOG.info(url.toString());

    ExceptionModel e = rest().getForObject(url.toExternalForm(), ExceptionModel.class);

    assert e.getStatus() == 404;
  }

  private static List<Station> stations(List<Object> objects) {
    List<Station> stations = new ArrayList<>(objects.size());
    objects.forEach(o -> stations.add((Station) o));
    return stations;
  }

}
