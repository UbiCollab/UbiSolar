package com.sintef_energy.ubisolar.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by Håvard on 02.05.14.
 */
public class TimeResource {

    @GET
    @Path("/time")
    long getCurrentTime()
    {
        return System.currentTimeMillis();
    }

}
