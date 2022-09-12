package com.nnk.springboot.repository.impl;


import com.nnk.springboot.entity.UserEntity;
import com.nnk.springboot.repository.IBaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserRepository extends IBaseRepository<UserEntity>, JpaSpecificationExecutor<User> {
    Optional<UserEntity> findUserByUsername(String username);

}


