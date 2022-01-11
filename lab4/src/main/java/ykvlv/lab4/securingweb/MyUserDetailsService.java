package ykvlv.lab4.securingweb;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ykvlv.lab4.data.entity.User;
import ykvlv.lab4.data.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    protected MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }


}