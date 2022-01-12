package ykvlv.lab4.data.dto;

import lombok.Value;

@Value
public class UserDto {
    String username;
    String password;
    String confirmPassword;
}
