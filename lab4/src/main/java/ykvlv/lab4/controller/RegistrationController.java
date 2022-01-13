package ykvlv.lab4.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ykvlv.lab4.data.dto.Response;
import ykvlv.lab4.data.dto.UserDto;
import ykvlv.lab4.data.entity.User;
import ykvlv.lab4.securingweb.UserService;

@RestController
@AllArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @PostMapping("/registration")
    public Response<User> registration(@RequestBody UserDto userDto) {
        return userService.registerNewUser(userDto);
    }
}
