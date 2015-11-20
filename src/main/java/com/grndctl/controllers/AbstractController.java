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
package com.grndctl.controllers;

import com.grndctl.ResourceNotFoundException;
import com.grndctl.ServiceException;
import com.grndctl.model.station.StationCodeType;
import com.grndctl.services.StationSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;

/**
 */
@Controller
public abstract class AbstractController {

    @ExceptionHandler({ServiceException.class, ResourceNotFoundException.class})
    public ModelAndView handleServiceException(Exception e) {
        String exName;
        if (e instanceof ServiceException) {
            exName = "ServiceException";
        } else if (e instanceof ResourceNotFoundException) {
            exName = "ResourceNotFoundException";
        } else {
            exName = "Exception";
        }
        ModelAndView model = new ModelAndView(exName);
        model.addObject(exName, e.getMessage());
        return model;
    }

}
