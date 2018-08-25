package com.moon.app.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.moon.app.domain.AccountResponse;
import com.moon.app.enumeration.FriendType;
import com.moon.app.exception.TechnicalException;
import com.moon.app.model.AccountEntity;
import com.moon.app.model.RelationshipEntity;
import com.moon.app.repository.AccountRepository;
import com.moon.app.repository.RelationshipRepository;
import com.moon.app.service.AccountService;
import com.moon.app.util.CommonUtil;

@Service
public class AccountServiceImpl implements AccountService {

  private static final Logger logger = Logger.getLogger(AccountServiceImpl.class);

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private RelationshipRepository relationshipRepository;

  @Override
  public AccountResponse createByEmail(final String newEmail) throws TechnicalException {
    try {
      AccountEntity account = new AccountEntity();
      account.setEmail(newEmail);
      account.setName(newEmail.split("@")[0]);
      account = accountRepository.save(account);
      logger.info(String.format("Created Successful: %s - %s", account.getId(), account.getEmail()));
      return AccountResponse.convert(account);
    } catch (DataIntegrityViolationException e) {
      throw new TechnicalException("This email already created: " + newEmail);
    } catch (Exception e) {
      throw new TechnicalException("General Exception: " + e.getMessage());
    }
  }

  @Override
  public List<AccountResponse> createByListEmail(final List<String> newEmails) throws TechnicalException {
    try {

      return null;
    } catch (Exception e) {
      throw new TechnicalException("General Exception: " + e.getMessage());
    }
  }

  @Override
  public AccountResponse findbyEmail(final String newEmail) throws TechnicalException {
    try {
      AccountEntity account = accountRepository.findByEmail(newEmail);
      if (ObjectUtils.isEmpty(account)) {
        return null;
      }
      List<Long> blockIds = relationshipRepository.findByAccountId(account.getId()).stream()
          .filter(p -> FriendType.BLOCK.equals(p.getFriendType())).map(RelationshipEntity::getFriendId).collect(Collectors.toList());
      List<Long> friendIds = relationshipRepository.findByAccountId(account.getId()).stream()
          .filter(p -> FriendType.FRIEND.equals(p.getFriendType())).map(RelationshipEntity::getFriendId).collect(Collectors.toList());
      List<Long> subscribeIds = relationshipRepository.findByAccountId(account.getId()).stream()
          .filter(p -> FriendType.SUBSCRIBE.equals(p.getFriendType())).map(RelationshipEntity::getFriendId).collect(Collectors.toList());

      List<AccountEntity> blocks = accountRepository.findByIdIn(blockIds);
      List<AccountEntity> friends = accountRepository.findByIdIn(friendIds);
      List<AccountEntity> subscribes = accountRepository.findByIdIn(subscribeIds);
      return AccountResponse.convert(account, friends, subscribes, blocks);
    } catch (Exception e) {
      throw new TechnicalException("General Exception: " + e.getMessage());
    }
  }

  @Override
  public AccountResponse updateByEmail(final String oldEmail, final String newEmail) throws TechnicalException {
    try {
      AccountEntity account = accountRepository.findByEmail(oldEmail);
      if (ObjectUtils.isEmpty(account)) {
        throw new TechnicalException("Account is not existed");
      }
      account.setEmail(newEmail);
      account = accountRepository.save(account);
      logger.info("Updated: " + account);
      return AccountResponse.convert(account);
    } catch (DataIntegrityViolationException e) {
      throw new TechnicalException("This email already existed: " + newEmail);
    } catch (Exception e) {
      throw new TechnicalException("General Exception: " + e.getMessage());
    }
  }

  @Override
  public String connectbyEmail(final List<String> emails) throws TechnicalException {
    try {
      if (ObjectUtils.isEmpty(emails)) {
        throw new TechnicalException("List emails cannot be empty");
      }
      if (emails.size() != 2) {
        throw new TechnicalException("List emails have to be 2 elements only");
      }
      for (String email : emails) {
        if (!CommonUtil.validate(email)) {
          throw new TechnicalException(String.format("Email is not valid: %s", email));
        }
      }
      final List<AccountEntity> accounts = accountRepository.findByEmailIn(emails);
      if (accounts.size() == 0) {
        throw new TechnicalException(String.format("Data cannot be found for %s", emails));
      }
      if (accounts.size() != 2) {
        throw new TechnicalException(String.format("Data is not correct for %s, %s", emails, accounts));
      }
      final AccountEntity first = accounts.get(0);
      final AccountEntity second = accounts.get(1);
      final RelationshipEntity firstBlock = relationshipRepository.findByAccountIdAndFriendIdAndFriendType(first.getId(), second.getId(),
          FriendType.BLOCK);
      if (!ObjectUtils.isEmpty(firstBlock)) {
        throw new TechnicalException(String.format("%s blocked by %s", second.getEmail(), first.getEmail()));
      }
      final RelationshipEntity secondBlock = relationshipRepository.findByAccountIdAndFriendIdAndFriendType(first.getId(), second.getId(),
          FriendType.BLOCK);
      if (!ObjectUtils.isEmpty(secondBlock)) {
        throw new TechnicalException(String.format("%s blocked by %s", first.getEmail(), second.getEmail()));
      }

      relationshipRepository.save(new RelationshipEntity(first.getId(), second.getId(), FriendType.FRIEND));
      relationshipRepository.save(new RelationshipEntity(second.getId(), first.getId(), FriendType.FRIEND));
      return String.format("Connect Success for %s", emails);
    } catch (TechnicalException e) {
      throw e;
    } catch (DataIntegrityViolationException e) {
      throw new TechnicalException("These emails already connected: " + emails);
    } catch (Exception e) {
      throw new TechnicalException("General Exception: " + e.getMessage());
    }
  }

  @Override
  public AccountResponse commonbyEmail(final List<String> emails) throws TechnicalException {
    try {
      final List<AccountEntity> accounts = accountRepository.findByEmailIn(emails);
      final List<Long> accountIds = accounts.stream().map(AccountEntity::getId).collect(Collectors.toList());
      final List<RelationshipEntity> relatioships = relationshipRepository.findByAccountIdInAndFriendType(accountIds, FriendType.FRIEND);

      final Map<Long, Integer> countMap = new HashMap<>();
      relatioships.forEach(relatioship -> {
        Integer count = countMap.get(relatioship.getFriendId());
        if (ObjectUtils.isEmpty(countMap.get(relatioship.getFriendId()))) {
          countMap.put(relatioship.getFriendId(), 1);
        } else {
          countMap.put(relatioship.getFriendId(), ++count);
        }
      });
      final List<AccountResponse> commonAccounts = new ArrayList<>();
      countMap.forEach((key, value) -> {
        if (value == 2) {
          commonAccounts.add(AccountResponse.convert(accountRepository.findById(key)));
        }
      });
      if (ObjectUtils.isEmpty(commonAccounts)) {
        throw new TechnicalException("No Common Accounts");
      }
      final AccountResponse response = new AccountResponse();
      response.setFriendList(commonAccounts);
      response.setFriendCount(commonAccounts.size());
      return response;
    } catch (TechnicalException e) {
      throw e;
    } catch (Exception e) {
      throw new TechnicalException("General Exception: " + e.getMessage());
    }
  }

  @Override
  public AccountResponse blockByEmail(final String mainEmail, final String blockEmail) throws TechnicalException {
    try {
      final AccountEntity mainAccount = accountRepository.findByEmail(mainEmail);
      if (ObjectUtils.isEmpty(mainAccount)) {
        throw new TechnicalException("Main Account is not existed: " + mainEmail);
      }
      final AccountEntity blockAccount = accountRepository.findByEmail(blockEmail);
      if (ObjectUtils.isEmpty(blockAccount)) {
        throw new TechnicalException("Block Account is not existed: " + blockEmail);
      }
      final RelationshipEntity oldCurrentFriend = relationshipRepository.findByAccountIdAndFriendIdAndFriendType(mainAccount.getId(),
          blockAccount.getId(), FriendType.FRIEND);
      if (!ObjectUtils.isEmpty(oldCurrentFriend)) {
        relationshipRepository.delete(oldCurrentFriend);
        final String msgCurrentFriend = String.format("%s is deleted friend from %s.", blockAccount.getEmail(), mainAccount.getEmail());
        logger.info(msgCurrentFriend);
      }
      relationshipRepository.save(new RelationshipEntity(mainAccount.getId(), blockAccount.getId(), FriendType.BLOCK));
      logger.info(String.format("%s is blocked friend from %s.", blockAccount.getEmail(), mainAccount.getEmail()));
      return AccountResponse.convert(mainAccount);
    } catch (DataIntegrityViolationException e) {
      throw new TechnicalException("This email already blocked: " + blockEmail);
    } catch (Exception e) {
      throw new TechnicalException("General Exception: " + e.getMessage());
    }
  }

  @Override
  public AccountResponse subscribeByEmail(String mainEmail, String subscribeEmail) throws TechnicalException {
    try {
      final AccountEntity mainAccount = accountRepository.findByEmail(mainEmail);
      if (ObjectUtils.isEmpty(mainAccount)) {
        throw new TechnicalException("Main Account is not existed: " + mainEmail);
      }
      final AccountEntity subscribeAccount = accountRepository.findByEmail(subscribeEmail);
      if (ObjectUtils.isEmpty(subscribeAccount)) {
        throw new TechnicalException("Subscribe Account is not existed: " + subscribeEmail);
      }
      relationshipRepository.save(new RelationshipEntity(mainAccount.getId(), subscribeAccount.getId(), FriendType.SUBSCRIBE));
      logger.info(String.format("%s is subscribed friend from %s.", subscribeAccount.getEmail(), mainAccount.getEmail()));
      return AccountResponse.convert(mainAccount);
    } catch (DataIntegrityViolationException e) {
      throw new TechnicalException("This email already subscribed: " + subscribeEmail);
    } catch (Exception e) {
      throw new TechnicalException("General Exception: " + e.getMessage());
    }
  }

  @Override
  public AccountResponse retrieveAll(String sender, String text) throws TechnicalException {
    try {
      final AccountEntity senderAccount = accountRepository.findByEmail(sender);
      if (ObjectUtils.isEmpty(senderAccount)) {
        throw new TechnicalException("Sender Account is not existed: " + sender);
      }
      List<String> recipients = new ArrayList<>();

      final List<RelationshipEntity> relationships = relationshipRepository.findByAccountIdAndFriendTypeIn(senderAccount.getId(),
          Arrays.asList(FriendType.FRIEND, FriendType.SUBSCRIBE));
      if (!ObjectUtils.isEmpty(relationships)) {
        logger.info(String.format("%s have relationship with %s", senderAccount.getEmail(), relationships));
        final List<Long> recipientIds = relationships.stream().map(RelationshipEntity::getFriendId).collect(Collectors.toList());
        final List<AccountEntity> recipientAccounts = accountRepository.findByIdIn(recipientIds);
        recipientAccounts.forEach(p -> recipients.add(p.getEmail()));
      }
      final List<String> emailsFromText = CommonUtil.getEmailsFromText(text);
      if (!ObjectUtils.isEmpty(emailsFromText)) {
        recipients.addAll(emailsFromText);
      }
      if (ObjectUtils.isEmpty(recipients)) {
        throw new TechnicalException(String.format("%s dont have any recipient", senderAccount.getEmail()));
      }
      AccountResponse accountResponse = AccountResponse.convert(senderAccount);
      accountResponse.setRecipients(recipients);
      return accountResponse;
    } catch (TechnicalException e) {
      throw e;
    } catch (Exception e) {
      throw new TechnicalException("General Exception: " + e.getMessage());
    }
  }

  @Override
  public List<AccountEntity> getAllAccounts() {
    return accountRepository.findAll();
  }

  @Override
  public List<RelationshipEntity> getAllRelationships() {
    return relationshipRepository.findAll();
  }

}