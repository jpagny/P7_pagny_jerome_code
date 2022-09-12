package com.nnk.springboot.service.implement;

import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.entity.UserEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.impl.UserRepository;
import com.nnk.springboot.service.AbstractServiceCrud;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService extends AbstractServiceCrud<UserEntity, UserDTO> {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository theUserRepository) {
        super(theUserRepository);
        this.modelMapper = new ModelMapper();
        this.userRepository = theUserRepository;
    }

    @Override
    public UserDTO update(Integer id, UserDTO userDTO) throws ResourceNotFoundException {

        UserEntity userFound = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

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

}
