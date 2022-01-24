package ykvlv.lab4.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    private final DataSource dataSource;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//
//    public WebSecurityConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                    .antMatchers("/", "/registration", "/resources/**", "/webjars/**").permitAll()
//                    .anyRequest().authenticated()
//                .and()
//                    .formLogin().defaultSuccessUrl("/login", true)
//                    .permitAll()
//                .and()
//                    .logout()
//                    .permitAll()
//                .and()
//                    .oauth2Login(Customizer.withDefaults());
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // неужели это правда так криво конфигурируется(
//        auth
//                .jdbcAuthentication()
//                .dataSource(dataSource)
//                .passwordEncoder(bCryptPasswordEncoder)
//                .usersByUsernameQuery("select username, password, active " +
//                        "from lab4_users " +
//                        "where username=?")
//                .authoritiesByUsernameQuery("select u.username, r.roles " +
//                        "from lab4_users u inner join lab4_roles r on u.id = r.user_id " +
//                        "where u.username=?");
//    }
//}