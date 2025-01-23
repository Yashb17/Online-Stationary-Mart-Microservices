package com.onlinestationarymart.domain_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userid;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String address;
}
