package com.onlinestationarymart.user_service.service;

import com.onlinestationarymart.domain_service.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    UserDTO createUser(UserDTO userDTO);

    Optional<UserDTO> findUserDTOById(Long id);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(Long id, UserDTO userDTO);

    Boolean deleteUser(UserDTO userDTO);
}
