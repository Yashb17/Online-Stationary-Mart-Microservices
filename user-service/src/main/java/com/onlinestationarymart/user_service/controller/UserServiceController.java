package com.onlinestationarymart.user_service.controller;

import com.onlinestationarymart.domain_service.dto.UserDTO;
import com.onlinestationarymart.user_service.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user/")
public class UserServiceController {

    @Autowired
    private IUserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceController.class);

    //Post Request - createuser  http://localhost:8082/api/User/createUser
    @PostMapping("createUser")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        LOGGER.info("@@@@ UserController: Received create user Request with User: {}", userDTO);
        UserDTO userSavedDTO = userService.createUser(userDTO);
        return new ResponseEntity<>(userSavedDTO, HttpStatus.CREATED);
    }

    //Get Request - findById  http://localhost:8082/api/User/findUser?id=1
    @GetMapping("/findUser")
    public UserDTO findUserDTOById(@RequestParam Long id){
        LOGGER.info("@@@@ UserController: Received find user Request with id: {}", id);
        return userService.findUserDTOById(id).orElse(null);
    }

    //Get Request - getAllUsers  http://localhost:8082/api/User/getAllUsers
    @GetMapping("/getAllUsers")
    public List<UserDTO> findAllUsersById(){
        LOGGER.info("@@@@ UserController: Received get All users request");
        return userService.getAllUsers();
    }

    //Put Request - updateUser  http://localhost:8082/api/User/updateUser
    @PutMapping("updateUser/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        LOGGER.info("@@@@ UserController: Received update user Request with User: {}", userDTO);
        UserDTO userSavedDTO = userService.updateUser(id, userDTO);
        return userSavedDTO != null ? new ResponseEntity<>(userSavedDTO, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    //Delete Request - deleteUser  http://localhost:8082/api/User/deleteUser
    @DeleteMapping("deleteUser")
    public ResponseEntity<String> deleteUser(@RequestBody UserDTO userDTO) {
        LOGGER.info("@@@@ UserController: Received delete user Request for User: {}", userDTO);
        Boolean userDeleted = userService.deleteUser(userDTO);
        return userDeleted != Boolean.FALSE ? new ResponseEntity<>("User deleted successfully!", HttpStatus.OK) :
                new ResponseEntity<>("Ohh No,User doesn't exist in database!", HttpStatus.OK);
    }
}
