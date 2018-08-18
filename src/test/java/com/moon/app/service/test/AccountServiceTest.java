
package com.moon.app.service.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.moon.app.ApplicationTest;
import com.moon.app.exception.TechnicalException;
import com.moon.app.service.AccountService;

public class AccountServiceTest extends ApplicationTest {

  @Autowired
  private AccountService accountService;

  @Test
  public void getAll() {
    try {
      accountService.findbyEmail("AAAA");
    } catch (TechnicalException e) {
      e.printStackTrace();
    }
  }

}
