package com.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/logout")
@Slf4j
@RequiredArgsConstructor

public class LogOutController {
  @CrossOrigin(origins = "http://localhost:3000")
  @PostMapping
  public RedirectView logout() {
      return new RedirectView("/api/login");
    }
  }

