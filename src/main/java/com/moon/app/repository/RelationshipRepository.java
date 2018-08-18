package com.moon.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moon.app.enumeration.FriendType;
import com.moon.app.model.RelationshipEntity;

public interface RelationshipRepository extends JpaRepository<RelationshipEntity, Long> {

  RelationshipEntity findById(long id);

  List<RelationshipEntity> findByAccountId(long accountId);

  RelationshipEntity findByAccountIdAndFriendIdAndFriendType(long accountId, long friendId, FriendType friendType);

  List<RelationshipEntity> findByAccountIdInAndFriendType(List<Long> accountIds, FriendType friendType);

  List<RelationshipEntity> findByAccountIdAndFriendTypeIn(Long accountId, List<FriendType> friendTypes);

  List<RelationshipEntity> findByFriendId(long friendId);

  List<RelationshipEntity> findByFriendIdIn(List<Long> friendIds);

}
