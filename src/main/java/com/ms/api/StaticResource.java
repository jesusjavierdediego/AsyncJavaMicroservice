package com.ms.api;


import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.*;

@Path("/dashboard")
public class StaticResource {

    private static final String ROOT_STATIC_RESOURCES = "static";

    @GET
    @Path("{path : .*}")
    public Response staticResource(@PathParam("path") String path) throws IOException {
        File file = file(path);
        if (file != null) {
            String mimeType = mimeType(file);
            return Response.ok(file, mimeType).build();
        } else {
            // manage the case when the resource is inside a jar
            try (InputStream is = is(path)) {
                String mimeType = mimeType(path);
                return Response.ok(CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8)), mimeType).build();
            }
        }
    }

    static String mimeType(String path) {
        if (path.endsWith("js")) {
            return "application/javascript";
        }
        if (path.endsWith("css")) {
            return "text/css";
        }
        return new MimetypesFileTypeMap().getContentType(path);
    }

    static String mimeType(File file) {
        String fileExtension = Files.getFileExtension(file.getName());
        if ("js".equals(fileExtension)) {
            return "application/javascript";
        }
        if ("css".equals(fileExtension)) {
            return "text/css";
        }
        return new MimetypesFileTypeMap().getContentType(file);
    }

    static InputStream is(String path) throws IOException {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(staticPath(path));
    }

    static String staticPath(String path) {
        return ROOT_STATIC_RESOURCES + "/" + path;
    }

    static File file(String path) throws IOException {
        String localPath = Thread.currentThread().getContextClassLoader().getResource(staticPath(path)).getFile();
        File file = new File(localPath);
        if (!file.isFile()) {
            return null;
        }
        return file;
    }
}

