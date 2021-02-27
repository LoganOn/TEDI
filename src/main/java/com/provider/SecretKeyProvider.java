package com.provider;


import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class SecretKeyProvider {

  public byte[] getKey() throws IOException {
    InputStream resource = new ClassPathResource(
            "jwt.key").getInputStream();
    return ((InputStream) resource).readAllBytes();
  }

}
