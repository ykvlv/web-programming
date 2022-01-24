package ykvlv.lab4.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ykvlv.lab4.data.dto.UserDto;
import ykvlv.lab4.data.entity.Role;
import ykvlv.lab4.data.entity.User;
import ykvlv.lab4.data.repository.UserRepository;
import ykvlv.lab4.exception.BadArgumentException;

import java.util.Collections;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean userValid(UserDto userDto) throws BadArgumentException {
        return isUsernameValid(userDto.getUsername()) &&
                isPasswordValid(userDto.getPassword(), userDto.getConfirmPassword());
    }

    private boolean isUsernameValid(String username) throws BadArgumentException {
        final int MIN_LENGTH = 5;

        if (username == null) {
            throw new BadArgumentException("Имя пользователя пустое");
        } else if (username.matches("^vk_.*")) {
            throw new BadArgumentException("vk_ зарезервировано для авторизации");
        } else if (username.length() < MIN_LENGTH) {
            throw new BadArgumentException("Минимальная длина имени пользователя — " + MIN_LENGTH);
        } else if (userRepository.existsByUsername(username)) {
            throw new BadArgumentException("Пользователь с таким именем существует");
        } else {
            return true;
        }
    }

    private boolean isPasswordValid(String password, String confirmPassword) throws BadArgumentException {
        final int MIN_LENGTH = 8;
        if (password == null || confirmPassword == null) {
            throw new BadArgumentException("Поле пароля пустое");
        } else if (password.length() < MIN_LENGTH) {
            throw new BadArgumentException("Минимальная длина пароля — " + MIN_LENGTH);
        } else if (!password.equals(confirmPassword)) {
            throw new BadArgumentException("Введенные пароли отличаются");
        } else {
            return true;
        }
    }

    public User register(UserDto userDto) throws BadArgumentException {
        if (userValid(userDto)) {
            User user = new User(
                    userDto.getUsername(),
                    bCryptPasswordEncoder.encode(userDto.getPassword()),
                    true,
                    Collections.singletonList(
                            Role.ROLE_USER
                    ));
            userRepository.save(user);
            return user;
        }
        throw new BadArgumentException("Не удалось зарегистрировать пользователя");
    }
}
