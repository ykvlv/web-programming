package ykvlv.lab4.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ykvlv.lab4.data.dto.Response;
import ykvlv.lab4.data.dto.UserDto;
import ykvlv.lab4.data.entity.User;
import ykvlv.lab4.Service.UserService;
import ykvlv.lab4.exception.BadArgumentException;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Response<User> registration(@RequestBody UserDto userDto) {
        try {
            User user = userService.register(userDto);
            return new Response<>("Регистрация прошла успешно", true, user);
        } catch (BadArgumentException e) {
            return new Response<>(e.getMessage(), false, null);
        }
    }
}
