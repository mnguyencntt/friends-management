package com.moon.app.controller;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.moon.app.domain.AccountResponse;
import com.moon.app.domain.EmailRequest;
import com.moon.app.domain.FriendsRequest;
import com.moon.app.domain.ResponseJSON;
import com.moon.app.domain.SenderRequest;
import com.moon.app.domain.TargetRequest;
import com.moon.app.exception.TechnicalException;
import com.moon.app.service.AccountService;
import com.moon.app.util.CommonUtil;

@RestController
@RequestMapping(value = "/")
public class InfoController {

  @Autowired
  private AccountService accountService;

  @RequestMapping(value = "/createOne", method = RequestMethod.GET)
  public ResponseJSON createOne(String email) {
    try {
      if (!CommonUtil.validate(email)) {
        throw new TechnicalException("Email is invalid");
      }
      AccountResponse account = accountService.createByEmail(email);
      if (ObjectUtils.isEmpty(account)) {
        throw new TechnicalException("Account creates fail");
      }
      return new ResponseJSON(TRUE, "Account creates success", account);
    } catch (TechnicalException e) {
      return new ResponseJSON(FALSE, e.getMessage(), null);
    }
  }

  @RequestMapping(value = "/getAllAccounts", method = RequestMethod.GET)
  public ResponseJSON getAllAccount() {
    return new ResponseJSON(TRUE, "Get All Accounts", accountService.getAllAccounts());
  }

  @RequestMapping(value = "/getAllRelationships", method = RequestMethod.GET)
  public ResponseJSON getAllRelationships() {
    return new ResponseJSON(TRUE, "Get All Relationships", accountService.getAllRelationships());
  }

  @RequestMapping(value = "/updateEmail", method = RequestMethod.POST)
  public ResponseJSON updateEmail(@RequestBody TargetRequest request) {
    try {
      AccountResponse account = accountService.updateByEmail(request.getRequestor(), request.getTarget());
      String msg = String.format("Change email success from %s to %s", request.getRequestor(), request.getTarget());
      return new ResponseJSON(TRUE, msg, account);
    } catch (TechnicalException e) {
      return new ResponseJSON(FALSE, e.getMessage(), null);
    }
  }

  /*
   * 1. As a user, I need an API to create a friend connection between two email addresses.
   */
  @RequestMapping(value = "/connect", method = RequestMethod.POST)
  public ResponseJSON connect(@RequestBody FriendsRequest request) {
    try {
      String result = accountService.connectbyEmail(request.getFriends());
      return new ResponseJSON(TRUE, result, request.getFriends());
    } catch (TechnicalException e) {
      return new ResponseJSON(FALSE, e.getMessage(), null);
    }
  }

  /*
   * 2. As a user, I need an API to retrieve the friends list for an email address.
   */
  @RequestMapping(value = "/retrieve", method = RequestMethod.POST)
  public ResponseJSON retrieve(@RequestBody EmailRequest request) {
    try {
      if (!CommonUtil.validate(request.getEmail())) {
        throw new TechnicalException("Email is invalid");
      }
      AccountResponse account = accountService.findbyEmail(request.getEmail());
      if (ObjectUtils.isEmpty(account)) {
        throw new TechnicalException("Account finds fail");
      }
      return new ResponseJSON(TRUE, "Account finds success", account);
    } catch (TechnicalException e) {
      return new ResponseJSON(FALSE, e.getMessage(), null);
    }
  }

  /*
   * 3. As a user, I need an API to retrieve the common friends list between two email addresses.
   */
  @RequestMapping(value = "/retrieveCommon", method = RequestMethod.POST)
  public ResponseJSON retrieveCommon(@RequestBody FriendsRequest request) {
    try {
      AccountResponse account = accountService.commonbyEmail(request.getFriends());
      if (ObjectUtils.isEmpty(account)) {
        String msg = String.format("%s have no common account", request.getFriends());
        throw new TechnicalException(msg);
      }
      String msg = String.format("%s have common account", request.getFriends());
      return new ResponseJSON(TRUE, msg, account);
    } catch (TechnicalException e) {
      return new ResponseJSON(FALSE, e.getMessage(), null);
    }
  }

  /*
   * 4. As a user, I need an API to subscribe to updates from an email address.
   */
  @RequestMapping(value = "/subscribeEmail", method = RequestMethod.POST)
  public ResponseJSON subscribeEmail(@RequestBody TargetRequest request) {
    try {
      AccountResponse account = accountService.subscribeByEmail(request.getRequestor(), request.getTarget());
      String msg = String.format("Subscribe email success from %s to %s", request.getRequestor(), request.getTarget());
      return new ResponseJSON(TRUE, msg, account);
    } catch (TechnicalException e) {
      return new ResponseJSON(FALSE, e.getMessage(), null);
    }
  }

  /*
   * 5. As a user, I need an API to block updates from an email address.
   */
  @RequestMapping(value = "/blockEmail", method = RequestMethod.POST)
  public ResponseJSON blockEmail(@RequestBody TargetRequest request) {
    try {
      AccountResponse account = accountService.blockByEmail(request.getRequestor(), request.getTarget());
      String msg = String.format("%s blocked friend with %s.", request.getTarget(), request.getRequestor());
      return new ResponseJSON(TRUE, msg, account);
    } catch (TechnicalException e) {
      return new ResponseJSON(FALSE, e.getMessage(), null);
    }
  }

  /*
   * 6. As a user, I need an API to retrieve all email addresses that can receive updates from an email address.
   */
  @RequestMapping(value = "/retrieveAll", method = RequestMethod.POST)
  public ResponseJSON retrieveAll(@RequestBody SenderRequest request) {
    try {
      AccountResponse account = accountService.retrieveAll(request.getSender(), request.getText());
      String msg = String.format("%s retrieve all email addresses.", request.getSender());
      return new ResponseJSON(TRUE, msg, account);
    } catch (TechnicalException e) {
      return new ResponseJSON(FALSE, e.getMessage(), null);
    }
  }

}
