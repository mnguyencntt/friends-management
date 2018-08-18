package com.moon.app.service;

import java.util.List;

import com.moon.app.domain.AccountResponse;
import com.moon.app.exception.TechnicalException;
import com.moon.app.model.AccountEntity;
import com.moon.app.model.RelationshipEntity;

public interface AccountService {

  AccountResponse createByEmail(final String newEmail) throws TechnicalException;

  List<AccountResponse> createByListEmail(final List<String> newEmails) throws TechnicalException;

  AccountResponse findbyEmail(final String newEmail) throws TechnicalException;

  AccountResponse updateByEmail(final String oldEmail, final String newEmail) throws TechnicalException;

  String connectbyEmail(final List<String> emails) throws TechnicalException;

  AccountResponse commonbyEmail(final List<String> emails) throws TechnicalException;

  AccountResponse blockByEmail(final String mainEmail, final String blockEmail) throws TechnicalException;

  AccountResponse subscribeByEmail(final String mainEmail, final String subscribeEmail) throws TechnicalException;

  AccountResponse retrieveAll(final String sender, final String text) throws TechnicalException;

  List<AccountEntity> getAllAccounts();

  List<RelationshipEntity> getAllRelationships();

}