package com.onlinestationarymart.user_service.service.impl;

import com.onlinestationarymart.domain_service.Exception.ResourceNotFoundException;
import com.onlinestationarymart.domain_service.dto.UserDTO;
import com.onlinestationarymart.user_service.entity.User;
import com.onlinestationarymart.user_service.mapper.UserMapper;
import com.onlinestationarymart.user_service.repository.UserRepository;
import com.onlinestationarymart.user_service.service.IUserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        LOGGER.info("@@@@ UserServiceImpl: Inside createUser method for creating a new user with data-> {}", userDTO);
        User user = modelMapper.map(userDTO, User.class);
        User createdUser = userRepository.save(user);
        UserDTO createdUserDTO = modelMapper.map(createdUser, UserDTO.class);
        LOGGER.info("@@@@ UserServiceImpl: Created User -> {}", userMapper.mapForLogging(createdUserDTO));
        return createdUserDTO;
    }

    @Override
    public Optional<UserDTO> findUserDTOById(Long id) {
        LOGGER.info("@@@@ UserServiceImpl: Inside findUserById method trying to fetch existing user by Id: {}", id);
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );
        return Optional.ofNullable(modelMapper.map(user, UserDTO.class));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        LOGGER.info("@@@@ UserServiceImpl: Inside getAllUsers method to fetch all users");
        List<User> user = userRepository.findAll();
        return user.stream().map(e -> modelMapper.map(e, UserDTO.class)).toList();
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        LOGGER.info("@@@@ UserServiceImpl: Inside updateUser method trying to find existing user by Id: {}", id);
        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userDTO.getUserid())
        );
        if(Objects.nonNull(existingUser)) {
            existingUser.setUsername(userDTO.getUsername());
            existingUser.setFullName(userDTO.getFullName());
            existingUser.setPassword(userDTO.getPassword());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setAddress(userDTO.getAddress());
            User updatedUser = userRepository.save(existingUser);
            UserDTO updatedUserDTO = modelMapper.map(updatedUser, UserDTO.class);
            LOGGER.info("@@@@ UserServiceImpl: Updated User -> {}", userMapper.mapForLogging(updatedUserDTO));
            return updatedUserDTO;
        }
        else{
            return null;
        }
    }

    @Override
    @Transactional
    public Boolean deleteUser(UserDTO userDTO) {
        LOGGER.info("@@@@ UserServiceImpl: Inside deleteUser method trying to delete existing user by Id: {}", userDTO.getUserid());
        User existingUser = findUserById(userDTO.getUserid());
        LOGGER.info("@@@@ UserServiceImpl: Found user!. Going to delete it");
        userRepository.deleteById(existingUser.getUserid());
        return Boolean.TRUE;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );
    }
}
