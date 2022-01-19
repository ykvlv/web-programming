package ykvlv.lab4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ykvlv.lab4.data.dto.Response;
import ykvlv.lab4.data.dto.UserDto;
import ykvlv.lab4.data.entity.User;
import ykvlv.lab4.Service.UserService;
import ykvlv.lab4.exception.BadArgumentException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping
    public Response<User> registration(@RequestBody UserDto userDto, HttpServletRequest req) {
        try {
            User user = userService.register(userDto);

            req.login(userDto.getUsername(), userDto.getPassword());

            return new Response<>("Регистрация прошла успешно", true, user);
        } catch (BadArgumentException e) {
            return new Response<>(e.getMessage(), false, null);
        } catch (ServletException e) {
            return new Response<>("Ошибка авторизации", false, null);
        }
    }
}
