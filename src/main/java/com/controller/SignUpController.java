package com.controller;


import com.exception.BadRequestException;
import com.exception.UserNotRegisteredException;
import com.exception.ValidationFailure;
import com.handler.UserSignupDto;
import com.model.Users;
import com.service.EmailSender;
import com.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SignUpController {

  private final String LOG_ERROR_MSG_EMAIL_IN_USE = "User error while signing up - email already in use";

  private final String VALIDATION_FAILURE = " Validation failure";

  private final String INVALID_PASS = "User error while singing up - password should be six characters long and have at least one capital and one number";

  private final UserService userService;

  @Autowired
  private final EmailSender emailSender;

  private final Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}+$");

  //TODO Change signature to RedirectView
  @PostMapping(
          path="api/register",
          consumes = "application/json")
  public UserSignupDto regsiterNewAccount(
          @RequestBody @Valid UserSignupDto signupDto,
          BindingResult result,
          HttpServletRequest request,
          RedirectAttributes attributes) throws UserNotRegisteredException, ValidationFailure {
    String email = signupDto.getEmail();
    Optional<Users> userOpt = userService.loadUserByEmail(email);
    if(userOpt.isPresent()) {
      throw new BadRequestException(LOG_ERROR_MSG_EMAIL_IN_USE);
    }
    if(!isValid(signupDto.getPassword())) {
      throw new BadRequestException(INVALID_PASS);
    }
    if(result.hasErrors()) {
      throw new ValidationFailure(VALIDATION_FAILURE);
    }
    userService.save(signupDto);
    emailSender.sendEmail("k.krawczyk@femax.pl", "TEST", "TESSSSSSST");
    return signupDto;
  }

  private boolean isValid(String password) {
    return pattern.matcher(password).matches();
  }
}
