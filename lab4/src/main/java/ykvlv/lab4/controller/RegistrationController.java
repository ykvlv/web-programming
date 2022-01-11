package ykvlv.lab4.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ykvlv.lab4.data.dto.UserDto;
import ykvlv.lab4.data.entity.User;
import ykvlv.lab4.securingweb.UserService;

import java.util.Collections;

@RestController
public class RegistrationController {
    UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public User registration(@RequestBody UserDto userDto) {
        //TODO сделать выбрасывание конкретной ошибки чтобы пользователь видел в чем дело
        if (userService.userValid(userDto)) {
            return userService.registerNewUser(userDto);
        } else {
            return new User("a", "b", false, Collections.emptyList());
        }
    }
}
