package com.nnk.springboot.service.implement;

import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.entity.UserEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.IGenericService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements IGenericService<UserDTO> {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDTO findById(Integer id) throws ResourceNotFoundException {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User doesn't exist with id : " + id));

        return modelMapper.map(userEntity, UserDTO.class);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        UserEntity userEntitySaved = userRepository.save(userEntity);

        return modelMapper.map(userEntitySaved, UserDTO.class);
    }

    @Override
    public UserDTO update(Integer id, UserDTO userDTO) throws ResourceNotFoundException {

        UserEntity userFound = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User doesn't exist with id : " + id));

        String username = userDTO.getUsername() != null
                ? userDTO.getUsername()
                : userFound.getUsername();

        String password = userDTO.getPassword() != null
                ? userDTO.getPassword()
                : userFound.getPassword();

        String fullName = userDTO.getFullname() != null
                ? userDTO.getFullname()
                : userFound.getFullname();

        String role = userDTO.getRole() != null
                ? userDTO.getRole()
                : userFound.getRole();


        userFound.setUsername(username);
        userFound.setPassword(password);
        userFound.setFullname(fullName);
        userFound.setRole(role);


        userRepository.save(userFound);

        return modelMapper.map(userFound, UserDTO.class);
    }

    @Override
    public void delete(Integer id) throws ResourceNotFoundException {
        UserEntity userFound = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User doesn't exist with id : " + id));

        userRepository.delete(userFound);
    }


}
