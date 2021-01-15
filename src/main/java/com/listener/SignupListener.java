package com.listener;

import com.event.OnSignupCompleteEvent;
import com.model.Users;
import com.service.EmailSender;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SignupListener implements ApplicationListener<OnSignupCompleteEvent> {

  private final EmailSender emailSender;

  @Override
  public void onApplicationEvent(@NonNull OnSignupCompleteEvent event) {
    this.confirmRegistration(event);
  }

  private void confirmRegistration(OnSignupCompleteEvent event) {
    Users user = event.getUser();

    String message = "Thank you for signing up with our T-EDI system";
    emailSender.sendEmail(
            user.getEmail(),
            "T-EDI registration confirmation",
            message );
  }
}

