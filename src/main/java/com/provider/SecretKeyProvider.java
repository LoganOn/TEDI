package com.provider;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class SecretKeyProvider {

  public byte[] getKey() throws IOException {
    InputStream resource = new ClassPathResource(
            "jwt.key").getInputStream();
    return resource.readAllBytes();
  }
}
