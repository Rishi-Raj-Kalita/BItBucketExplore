package com.backend.api.auth;

import lombok.Value;
import org.apache.commons.codec.binary.Base64;
import org.elasticsearch.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class AuthController {
    


    public HttpHeaders generateAuth(){



        String plainCreds = "bitbucket:changeit";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }
}
