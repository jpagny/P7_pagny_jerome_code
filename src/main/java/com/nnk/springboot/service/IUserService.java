package com.nnk.springboot.service;

import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.entity.UserEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;

import java.util.List;

public interface IUserService {

    UserDTO findById(Integer id) throws ResourceNotFoundException;

    List<UserEntity> findAll();

    UserDTO create(UserDTO userDTO);

    UserDTO update(Integer id, UserDTO userDTO) throws ResourceNotFoundException;

    void delete(Integer id) throws ResourceNotFoundException;
}
