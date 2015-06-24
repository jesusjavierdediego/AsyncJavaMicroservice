package com.ms.utils;


import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

public class CORSFilter implements ContainerResponseFilter {

@Override
public void filter(ContainerRequestContext requestContext,   ContainerResponseContext responseContext)
    throws IOException {
        //http://www.codingpedia.org/ama/how-to-add-cors-support-on-the-server-side-in-java-with-jersey/
        responseContext.getHeaders().add("Access-Control-Allow-Origin","*");
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT. OPTIONS");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Accept");


  }
}