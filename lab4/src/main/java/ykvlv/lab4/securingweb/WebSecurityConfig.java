package ykvlv.lab4.securingweb;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO а ну нормально сделал бл
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/", "/registration").permitAll()
                    .antMatchers("/admin").hasAuthority("ROLE_ADMIN")
                    .anyRequest().hasAuthority("ROLE_USER")
                .and()
                    .formLogin()
                    .permitAll()
                .and()
                    .logout()
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder)
                .usersByUsernameQuery("select username, password, active " +
                        "from lab4_users " +
                        "where username=?")
                .authoritiesByUsernameQuery("select u.username, r.roles " +
                        "from lab4_users u inner join lab4_roles r on u.id = r.user_id " +
                        "where u.username=?");
    }
}