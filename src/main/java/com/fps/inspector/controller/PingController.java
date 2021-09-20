package com.fps.inspector.controller;

import org.slf4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PingController {

    @Inject
    Logger logger;

    @GET
    public String ping() {
        logger.info("[PING]: Test ping...");
        return ("Ping Inspector Auth: ".concat("Successful..."));
    }

}