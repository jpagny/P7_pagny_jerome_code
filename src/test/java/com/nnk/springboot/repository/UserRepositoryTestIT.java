package com.nnk.springboot.repository;

import com.nnk.springboot.constant.Role;
import com.nnk.springboot.entity.TradeEntity;
import com.nnk.springboot.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class UserRepositoryTestIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userTest() {
        UserEntity user = new UserEntity("Jerome Pagny", "JP", "xxx", Role.USER.name());

        // Save
        user = userRepository.save(user);
        assertNotNull(user.getId());
        assertEquals("Jerome Pagny", user.getFullname());

        // Update
        user.setRole(Role.USER.name());
        user = userRepository.save(user);
        assertEquals(Role.USER.name(), user.getRole());

        // Find
        List<UserEntity> listResult = userRepository.findAll();
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = user.getId();
        userRepository.delete(user);
        Optional<UserEntity> theUser = userRepository.findById(id);
        assertFalse(theUser.isPresent());
    }
}
