package ykvlv.lab4.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/", "/registration", "/resources/**", "/webjars/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage("/login").defaultSuccessUrl("/login", true)
                    .permitAll()
                .and()
                    .logout()
                    .permitAll()
                .and()
                    .oauth2Login().loginPage("/login").defaultSuccessUrl("/login", true)
                    .tokenEndpoint()
                    .accessTokenResponseClient(accessTokenResponseClient())
                .and()
                    .userInfoEndpoint()
                    .userService(new CustomOAuth2UserService());
    }

    @Bean
    // Был необходим самописный accessTokenResponseClient для использования собственного конвертера токенов
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient =
                new DefaultAuthorizationCodeTokenResponseClient();

        OAuth2AccessTokenResponseHttpMessageConverter tokenResponseHttpMessageConverter =
                new OAuth2AccessTokenResponseHttpMessageConverter();

        tokenResponseHttpMessageConverter.setAccessTokenResponseConverter(new CustomTokenResponseConverter());

        RestTemplate restTemplate = new RestTemplate(Arrays.asList(
                new FormHttpMessageConverter(), tokenResponseHttpMessageConverter));

        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());

        accessTokenResponseClient.setRestOperations(restTemplate);
        return accessTokenResponseClient;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // неужели это правда так криво конфигурируется(
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