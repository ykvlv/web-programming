package ykvlv.lab4.controller;

import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ykvlv.lab4.data.dto.Response;
import ykvlv.lab4.data.dto.UserDto;
import ykvlv.lab4.data.entity.User;
import ykvlv.lab4.Service.UserService;
import ykvlv.lab4.exception.InvalidUserException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @PostMapping("/registration")
    public Response<User> registration(@RequestBody UserDto userDto) {
        try {
            User user = userService.registerNewUser(userDto);
            return new Response<>("Регистрация прошла успешно", true, user);
        } catch (InvalidUserException e) {
            return new Response<>(e.getMessage(), false, null);
        }
    }
}
