package com.fps.inspector.controller;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class Exceptions implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException weException) {

        // get initial response
        Response response = weException.getResponse();

        // return the custom error
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

}