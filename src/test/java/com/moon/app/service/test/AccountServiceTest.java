
package com.moon.app.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.moon.app.ApplicationTest;
import com.moon.app.domain.AccountResponse;
import com.moon.app.exception.TechnicalException;
import com.moon.app.model.AccountEntity;
import com.moon.app.service.AccountService;

public class AccountServiceTest extends ApplicationTest {

  @Autowired
  private AccountService accountService;

  @Test
  public void test_getAllAccounts() {
    List<AccountEntity> allAccounts = accountService.getAllAccounts();
    assertEquals(4, allAccounts.size());
  }

  @Test
  public void test_findbyEmail() {
    try {
      AccountResponse account = accountService.findbyEmail("john@example.com");
      assertEquals("john@example.com", account.getEmail());
    } catch (TechnicalException e) {
      fail();
    }
  }

  @Test
  public void test_commonbyEmail() {
    try {
      accountService.connectbyEmail(Arrays.asList("john@example.com", "andy@example.com"));
      accountService.connectbyEmail(Arrays.asList("lisa@example.com", "john@example.com"));
      accountService.connectbyEmail(Arrays.asList("lisa@example.com", "andy@example.com"));
      AccountResponse account = accountService.commonbyEmail(Arrays.asList("john@example.com", "andy@example.com"));
      assertEquals(1, account.getFriendList().size());
      assertEquals("lisa@example.com", account.getFriendList().get(0).getEmail());
    } catch (TechnicalException e) {
      fail();
    }
  }

}
