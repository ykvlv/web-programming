package ykvlv.lab4.oauth;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenResponseConverter implements Converter<Map<String, Object>, OAuth2AccessTokenResponse> {

    @Override
    public OAuth2AccessTokenResponse convert(Map<String, Object> tokenResponseParameters) {
        String accessToken = (String) tokenResponseParameters.get(OAuth2ParameterNames.ACCESS_TOKEN);
        long expiresIn = (int) tokenResponseParameters.get(OAuth2ParameterNames.EXPIRES_IN);


        OAuth2AccessToken.TokenType accessTokenType = OAuth2AccessToken.TokenType.BEARER;

        Map<String, Object> additionalParameters = new HashMap<>();

        tokenResponseParameters.forEach(additionalParameters::put);

        return OAuth2AccessTokenResponse.withToken(accessToken)
                .tokenType(accessTokenType)
                .expiresIn(expiresIn)
                .additionalParameters(additionalParameters)
                .build();
    }
}