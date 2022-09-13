package com.nnk.springboot.service;

import com.nnk.springboot.constant.Role;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.entity.UserEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.impl.UserRepository;
import com.nnk.springboot.service.implement.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void initService() {
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("Should be returned user when the user is found by id")
    public void should_beReturnedUser_when_theUserIsFoundById() throws ResourceNotFoundException {
        UserDTO user = new UserDTO("Jerome Pagny", "JP", "xxx", Role.ADMIN.name());

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(new UserEntity("Jerome Pagny", "JP", "xxx", Role.ADMIN.name())));

        UserDTO userFound = userService.findById(1);

        assertEquals(userFound, user);
    }

    @Test
    @DisplayName("Should be exception when the user is not found by id")
    public void should_beException_when_theUserIsNotFoundById() {
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> userService.findById(1));

        String expectedMessage = "Resource doesn't exist with id : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should be returned a list of user when get all users")
    public void should_beReturnedAListOfUser_when_getAllUsers() {
        List<UserDTO> listUser = new ArrayList<>();
        listUser.add(new UserDTO("Jerome Pagny", "JP", "xxx", Role.ADMIN.name()));
        listUser.add(new UserDTO("Richard Pagny", "RP", "xxx", Role.USER.name()));

        when(userRepository.findAll()).thenReturn(listUser.stream()
                .map(curvePoint -> modelMapper.map(curvePoint, UserEntity.class))
                .collect(Collectors.toList()));

        List<UserDTO> listUserFound = userService.findAll();

        assertEquals(listUserFound, listUser);
    }

    @Test
    @DisplayName("Should be returned user when a new user is created")
    public void should_BeReturnedNewUser_When_ANewUserIsCreated() {
        UserDTO user = new UserDTO("Jerome Pagny", "JP", "xxx", Role.ADMIN.name());

        when(userRepository.save(any(UserEntity.class))).thenReturn(new UserEntity("Jerome Pagny", "JP", "xxx", Role.ADMIN.name()));

        UserDTO newUser = userService.create(user);

        assertEquals(newUser, user);
    }

    @Test
    @DisplayName("Should be returned user updated when a user is updated")
    public void should_beReturnedUserUpdated_when_aUserIsUpdated() throws ResourceNotFoundException {
        UserDTO userToUpdate = new UserDTO("Jerome Pagny", "JP", "xxx", Role.ADMIN.name());
        userToUpdate.setRole(Role.USER.name());

        UserEntity UserEntity = new UserEntity("Jerome Pagny", "JP", "xxx", Role.USER.name());

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(UserEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(UserEntity);

        UserDTO userUpdated = userService.update(1, userToUpdate);

        assertEquals(userUpdated, userToUpdate);
    }

    @Test
    @DisplayName("Should be exception when the user to update doesnt exist")
    public void should_beException_when_theUserToUpdateDoesntExist() {
        UserDTO userDTO = new UserDTO();
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> userService.update(1, userDTO));

        String expectedMessage = "Resource doesn't exist with id : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should be used userRepository.delete method when a user will be deleted")
    public void should_beUsedUserRepositoryDeleteMethod_when_aUserWillBeDeleted() throws ResourceNotFoundException {

        UserEntity user = new UserEntity("Jerome Pagny", "JP", "xxx", Role.USER.name());

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(any(UserEntity.class));

        userService.delete(1);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    @DisplayName("Should be exception when the user to delete doesnt exist")
    public void should_beException_when_theUserToDeleteDoesntExist() {
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> userService.delete(1));

        String expectedMessage = "Resource doesn't exist with id : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


}
