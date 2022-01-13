package ykvlv.lab4.Service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ykvlv.lab4.data.dto.UserDto;
import ykvlv.lab4.data.role.Role;
import ykvlv.lab4.data.entity.User;
import ykvlv.lab4.data.repository.UserRepository;
import ykvlv.lab4.exception.InvalidUserException;
import ykvlv.lab4.security.UserPrincipal;

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

    public boolean isUserValid(UserDto userDto) throws InvalidUserException {
        return isUsernameValid(userDto.getUsername()) &&
                isPasswordValid(userDto.getPassword(), userDto.getConfirmPassword());
    }

    private boolean isUsernameValid(String username) throws InvalidUserException {
        final int MIN_LENGTH = 5;
        if (username == null) {
            throw new InvalidUserException("Имя пользователя пустое");
        } else if (username.length() < MIN_LENGTH) {
            throw new InvalidUserException("Минимальная длина имени пользователя — " + MIN_LENGTH);
        } else if (userRepository.existsByUsername(username)) {
            throw new InvalidUserException("Пользователь с таким именем существует");
        } else {
            return true;
        }
    }

    private boolean isPasswordValid(String password, String confirmPassword) throws InvalidUserException {
        final int MIN_LENGTH = 8;
        if (password == null || confirmPassword == null) {
            throw new InvalidUserException("Поле пароля пустое");
        } else if (password.length() < MIN_LENGTH) {
            throw new InvalidUserException("Минимальная длина пароля — " + MIN_LENGTH);
        } else if (!password.equals(confirmPassword)) {
            throw new InvalidUserException("Введенные пароли отличаются");
        } else {
            return true;
        }
    }

    public User registerNewUser(UserDto userDto) throws InvalidUserException {
        if (isUserValid(userDto)) {
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
        throw new InvalidUserException("Зарегистрировать пользователя не удалось");
    }
}
