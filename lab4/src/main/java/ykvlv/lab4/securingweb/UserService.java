package ykvlv.lab4.securingweb;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ykvlv.lab4.data.dto.UserDto;
import ykvlv.lab4.data.entity.Role;
import ykvlv.lab4.data.entity.User;
import ykvlv.lab4.data.repository.RoleRepository;
import ykvlv.lab4.data.repository.UserRepository;

import java.util.Collections;


@Service
public class UserService implements UserDetailsService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    protected UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }

    //TODO нормальную валидацию и шифрование пароля
    public boolean userValid(UserDto userDto) {
        return usernameValid(userDto.getUsername()) && passwordValid(userDto.getPassword(), userDto.getConfirmPassword());
    }

    private boolean usernameValid(String username) {
        if (username == null) return false;
        if (username.length() < 2) return false;
        return !userRepository.existsByUsername(username);
    }

    private boolean passwordValid(String password, String confirmPassword) {
        if (password == null || confirmPassword == null) return false;
        if (password.length() < 2) return false;
        return password.equals(confirmPassword);
    }

    public User registerNewUser(UserDto userDto) {
        Role userRole = roleRepository.getById("ROLE_USER");
        User user = new User(userDto.getUsername(), userDto.getPassword(), true, Collections.singletonList(userRole));
        userRepository.save(user);
        return user;
    }
}