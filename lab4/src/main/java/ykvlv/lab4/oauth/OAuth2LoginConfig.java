package ykvlv.lab4.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class OAuth2LoginConfig {

    @EnableWebSecurity
    public static class OAuth2LoginSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests((authorizeRequests) ->
                            authorizeRequests
                                    .anyRequest().authenticated()
                    );
            http.oauth2Login().tokenEndpoint().accessTokenResponseClient(accessTokenResponseClient());
        }

        @Bean
        public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
            DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();

//            accessTokenResponseClient.setRequestEntityConverter(new CustomRequestEntityConverter());

            OAuth2AccessTokenResponseHttpMessageConverter tokenResponseHttpMessageConverter = new OAuth2AccessTokenResponseHttpMessageConverter();

            tokenResponseHttpMessageConverter.setAccessTokenResponseConverter(new CustomTokenResponseConverter());

            RestTemplate restTemplate = new RestTemplate(Arrays.asList(
                    new FormHttpMessageConverter(), tokenResponseHttpMessageConverter));
            restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());

            accessTokenResponseClient.setRestOperations(restTemplate);
            return accessTokenResponseClient;
        }


    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.vkClientRegistration());
    }

    public ClientRegistration vkClientRegistration() {
        return ClientRegistration.withRegistrationId("vk")
                .clientId("8060228")
                .clientSecret("NqBOMgxyRj7zi0hbcav9")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .scope("offline")
                .authorizationUri("https://oauth.vk.com/authorize")
                .tokenUri("https://oauth.vk.com/access_token")
//                .userNameAttributeName(IdTokenClaimNames.SUB)
//                .userInfoUri("https://api.vk.com/method/users.get?{user_id}&v=5.95&fields=photo_id,verified,sex,bdate,city,country,photo_max,home_town,has_photo&display=popup&lang=ru&access_token=xxxxx").
//                .userNameAttributeName("user_id")
                .clientName("Vk")
                .build();
    }
}