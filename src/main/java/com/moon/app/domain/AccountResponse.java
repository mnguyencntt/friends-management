package com.moon.app.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.moon.app.model.AccountEntity;

@JsonInclude(Include.NON_NULL)
public class AccountResponse implements Serializable {

  private static final long serialVersionUID = -2231964655598360424L;

  @JsonIgnore
  private long id;

  private String name;
  private String email;

  private List<AccountResponse> friendList;
  private List<AccountResponse> subscribeList;
  private List<AccountResponse> blockList;

  private Integer friendCount;
  private Integer subscribeCount;
  private Integer blockCount;

  private List<String> recipients;

  public AccountResponse() {

  }

  public AccountResponse(long id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public AccountResponse(long id, String name, String email, List<AccountResponse> friendList, List<AccountResponse> blockList) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.friendList = friendList;
    this.blockList = blockList;
  }

  public static AccountResponse convert(AccountEntity account) {
    return new AccountResponse(account.getId(), account.getName(), account.getEmail());
  }

  public static AccountResponse convert(AccountEntity account, List<AccountEntity> friends, List<AccountEntity> subscribes,
      List<AccountEntity> blocks) {
    List<AccountResponse> friendList = null;
    List<AccountResponse> subscribeList = null;
    List<AccountResponse> blockList = null;
    AccountResponse accountResponse = new AccountResponse(account.getId(), account.getName(), account.getEmail());
    if (!ObjectUtils.isEmpty(friends)) {
      friendList = new ArrayList<>();
      for (AccountEntity p : friends) {
        friendList.add(new AccountResponse(p.getId(), p.getName(), p.getEmail()));
      }
      accountResponse.setFriendList(friendList);
      accountResponse.setFriendCount(friendList.size());
    }
    if (!ObjectUtils.isEmpty(subscribes)) {
      subscribeList = new ArrayList<>();
      for (AccountEntity p : subscribes) {
        subscribeList.add(new AccountResponse(p.getId(), p.getName(), p.getEmail()));
      }
      accountResponse.setSubscribeList(subscribeList);
      accountResponse.setSubscribeCount(subscribeList.size());
    }
    if (!ObjectUtils.isEmpty(blocks)) {
      blockList = new ArrayList<>();
      for (AccountEntity p : blocks) {
        blockList.add(new AccountResponse(p.getId(), p.getName(), p.getEmail()));
      }
      accountResponse.setBlockList(blockList);
      accountResponse.setBlockCount(blockList.size());
    }
    return accountResponse;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<AccountResponse> getFriendList() {
    return friendList;
  }

  public void setFriendList(List<AccountResponse> friendList) {
    this.friendList = friendList;
  }

  public List<AccountResponse> getBlockList() {
    return blockList;
  }

  public void setBlockList(List<AccountResponse> blockList) {
    this.blockList = blockList;
  }

  public Integer getFriendCount() {
    return friendCount;
  }

  public void setFriendCount(Integer friendCount) {
    this.friendCount = friendCount;
  }

  public Integer getBlockCount() {
    return blockCount;
  }

  public void setBlockCount(Integer blockCount) {
    this.blockCount = blockCount;
  }

  public List<AccountResponse> getSubscribeList() {
    return subscribeList;
  }

  public void setSubscribeList(List<AccountResponse> subscribeList) {
    this.subscribeList = subscribeList;
  }

  public Integer getSubscribeCount() {
    return subscribeCount;
  }

  public void setSubscribeCount(Integer subscribeCount) {
    this.subscribeCount = subscribeCount;
  }

  public List<String> getRecipients() {
    return recipients;
  }

  public void setRecipients(List<String> recipients) {
    this.recipients = recipients;
  }

}
