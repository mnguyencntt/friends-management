package com.moon.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moon.app.model.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

  AccountEntity findById(long id);

  List<AccountEntity> findByIdIn(List<Long> ids);

  List<AccountEntity> findByName(String name);

  AccountEntity findByEmail(String email);

  List<AccountEntity> findByEmailIn(List<String> emails);

}
