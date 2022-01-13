package ykvlv.lab4.controller.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ykvlv.lab4.data.dto.Response;
import ykvlv.lab4.data.entity.User;
import ykvlv.lab4.exception.UserRegisterException;

@ControllerAdvice
public class RegisterNewUserAdvice {

    @ResponseBody
    @ExceptionHandler(UserRegisterException.class)
    Response<User> employeeNotFoundHandler(UserRegisterException ex) {
        return new Response<>(ex.getMessage(), false, null);
    }
}
