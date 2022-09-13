package com.nnk.springboot.service.implement;

import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.entity.UserEntity;
import com.nnk.springboot.exception.ResourceAlreadyExistException;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.impl.UserRepository;
import com.nnk.springboot.service.AbstractServiceCrud;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id)));

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
    public UserDTO create(UserDTO dto) throws ResourceAlreadyExistException {

        Optional<UserEntity> user = this.findUserByUsername(dto.getUsername());

        if (user.isPresent()) {
            throw new ResourceAlreadyExistException((dto.getUsername()));
        }

        UserEntity entity = modelMapper.map(dto, UserEntity.class);
        UserEntity entitySaved = userRepository.save(entity);

        return modelMapper.map(entitySaved, UserDTO.class);

    }

    public Optional<UserEntity> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }


}
