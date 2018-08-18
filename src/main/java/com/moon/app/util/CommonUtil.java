
package com.moon.app.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

public final class CommonUtil {

  private CommonUtil() {
    throw new UnsupportedOperationException();
  }

  public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
      Pattern.CASE_INSENSITIVE);

  public static boolean validate(final String email) {
    Assert.notNull(email, "Email cannot be null. Please insert email value");
    Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
    return matcher.find();
  }

  public static List<String> getEmailsFromText(final String text) {
    final List<String> emails = new ArrayList<>();
    Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(text);
    while (m.find()) {
      emails.add(m.group());
    }
    return emails;
  }

}