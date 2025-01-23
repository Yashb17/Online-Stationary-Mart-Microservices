package com.onlinestationarymart.user_service.mapper;

import com.onlinestationarymart.domain_service.dto.UserDTO;
import com.onlinestationarymart.user_service.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
public class UserMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserMapper.class);

    public UserDTO toUserDTO(User user){
        LOGGER.info("@@@@ UserMapper: mapping from user to userDTO");
        requireNonNull(user);
        return new UserDTO(
                user.getUserid(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getPassword(),
                user.getAddress());
    }

    public User toUser(UserDTO userDTO){
        LOGGER.info("@@@@ UserMapper: mapping from userDTO to user");
        requireNonNull(userDTO);
        return new User(
                userDTO.getUserid(),
                userDTO.getUsername(),
                userDTO.getFullName(),
                userDTO.getAddress(),
                userDTO.getPassword(),
                userDTO.getEmail());
    }

    public String mapForLogging(UserDTO userDTO){
        return userDTO.toString();
    }
}
