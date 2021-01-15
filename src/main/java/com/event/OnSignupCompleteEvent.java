package com.event;

import com.model.Users;
import org.springframework.context.ApplicationEvent;
import java.util.Locale;
import lombok.Data;

@Data
public class OnSignupCompleteEvent extends ApplicationEvent {

  private transient Users user;

  private Locale locale;

  private String appUrl;

  public OnSignupCompleteEvent(Users user, Locale locale, String appUrl) {
    super(user);
    this.user = user;
    this.locale = locale;
    this.appUrl = appUrl;
  }
}

