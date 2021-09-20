package com.fps.inspector.controller;

import com.fps.inspector.dto.TokenResponse;
import com.fps.inspector.util.Constantes;
import com.fps.inspector.auth.CypherService;
import com.fps.inspector.auth.RolesEnum;
import com.fps.inspector.dto.BaseResponse;
import org.slf4j.Logger;


import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Singleton
@Path("/login")
public class TokenProviderResource {

    @Inject
    private Logger logger;

    @Inject
    CypherService cypherService;

    private PrivateKey key;

    @PostConstruct
    public void init() {
        try {
            key = cypherService.readPrivateKey();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doPosLogin(@FormParam("username") String username, @FormParam("password") String password,
                               @Context HttpServletRequest request) {
        List<String> target = new ArrayList<>();
        try {
            logger.info("[INSPECTOR-AUTH]: Validando usuarios..");
            request.login(username, password);

            if (request.isUserInRole(RolesEnum.USUARIO.getRole()))
                target.add(RolesEnum.USUARIO.getRole());

            if (request.isUserInRole(RolesEnum.ADMIN.getRole()))
                target.add(RolesEnum.ADMIN.getRole());

            String token = CypherService.generateJWT(key, username, target);
            TokenResponse t = new TokenResponse();
            t.setToken(token);
            return Response.status(Response.Status.OK).header(AUTHORIZATION, "Bearer ".concat(token)).entity(t)
                    .type(MediaType.APPLICATION_JSON).build();

        } catch (Exception ex) {
            logger.info("Error de autenticacion: {}", ex.getMessage());
            ex.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
