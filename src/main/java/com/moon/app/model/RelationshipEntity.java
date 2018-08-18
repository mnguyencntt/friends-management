package com.moon.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.moon.app.enumeration.FriendType;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "accountId", "friendId", "friendType" }) })
public class RelationshipEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private long accountId;
  private long friendId;
  private FriendType friendType;

  public RelationshipEntity() {
  }

  public RelationshipEntity(long accountId, long friendId, FriendType friendType) {
    this.accountId = accountId;
    this.friendId = friendId;
    this.friendType = friendType;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getAccountId() {
    return accountId;
  }

  public void setAccountId(long accountId) {
    this.accountId = accountId;
  }

  public long getFriendId() {
    return friendId;
  }

  public void setFriendId(long friendId) {
    this.friendId = friendId;
  }

  public FriendType getFriendType() {
    return friendType;
  }

  public void setFriendType(FriendType friendType) {
    this.friendType = friendType;
  }

}
