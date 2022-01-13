package ykvlv.lab4.securingweb;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ykvlv.lab4.data.dto.Response;
import ykvlv.lab4.data.dto.UserDto;
import ykvlv.lab4.data.role.Role;
import ykvlv.lab4.data.entity.User;
import ykvlv.lab4.data.repository.UserRepository;
import ykvlv.lab4.exception.UserRegisterException;

import java.util.Collections;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }

    public boolean userValid(UserDto userDto) {
        return usernameValid(userDto.getUsername()) &&
                passwordValid(userDto.getPassword(), userDto.getConfirmPassword());
    }

    private boolean usernameValid(String username) {
        final int MIN_LENGTH = 5;
        if (username == null) {
            throw new UserRegisterException("Имя пользователя пустое");
        } else if (username.length() < MIN_LENGTH) {
            throw new UserRegisterException("Минимальная длина имени пользователя — " + MIN_LENGTH);
        } else if (userRepository.existsByUsername(username)) {
            throw new UserRegisterException("Пользователь с таким именем существует");
        } else {
            return true;
        }
    }

    private boolean passwordValid(String password, String confirmPassword) {
        final int MIN_LENGTH = 8;
        if (password == null || confirmPassword == null) {
            throw new UserRegisterException("Поле пароля пустое");
        } else if (password.length() < MIN_LENGTH) {
            throw new UserRegisterException("Минимальная длина пароля — " + MIN_LENGTH);
        } else if (!password.equals(confirmPassword)) {
            throw new UserRegisterException("Введенные пароли отличаются");
        } else {
            return true;
        }
    }

    public Response<User> registerNewUser(UserDto userDto) {
        if (userValid(userDto)) {
            User user = new User(
                    userDto.getUsername(),
                    bCryptPasswordEncoder.encode(userDto.getPassword()),
                    true,
                    Collections.singletonList(
                            Role.ROLE_USER
                    ));
            userRepository.save(user);
            return new Response<>("Успешная регистрация", true, user);
        } else {
            throw new UserRegisterException("Не удалось заренистрироваться");
        }
    }
}
