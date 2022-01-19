package ykvlv.lab4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ykvlv.lab4.data.dto.UserDto;
import ykvlv.lab4.data.entity.User;
import ykvlv.lab4.Service.UserService;
import ykvlv.lab4.exception.BadArgumentException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    //TODO userDto надо бы в параметрах принимать
    public String registration(@RequestParam Map<String, String> body, HttpServletRequest req) {
        try {
            UserDto userDto = new UserDto(body.get("username"), body.get("password"), body.get("confirmPassword"));

            User user = userService.register(userDto);
            req.login(user.getUsername(), user.getPassword());

            return "application";
        } catch (BadArgumentException e) {
            return "forward:/reg?228";
        } catch (ServletException e) {
            return "reg";
        }
    }
}
