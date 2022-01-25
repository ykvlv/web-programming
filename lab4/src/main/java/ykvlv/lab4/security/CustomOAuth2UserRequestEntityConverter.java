package ykvlv.lab4.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.bind.DatatypeConverter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

// Потребовался собственный конвертер Entity так как ОДНОКЛАССНИКУ нужно добавлять
// ПОДПИСЬ ЗАПРОСА. она будет расчитываться тут.
public class CustomOAuth2UserRequestEntityConverter extends OAuth2UserRequestEntityConverter {

    private static final MediaType DEFAULT_CONTENT_TYPE = MediaType
            .valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

    @Override
    public RequestEntity<?> convert(OAuth2UserRequest userRequest) {
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        HttpMethod httpMethod = getHttpMethod(clientRegistration);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        URI uri = UriComponentsBuilder
                .fromUriString(clientRegistration.getProviderDetails().getUserInfoEndpoint().getUri()).build().toUri();

        RequestEntity<?> request;
        if (HttpMethod.POST.equals(httpMethod)) {
            headers.setContentType(DEFAULT_CONTENT_TYPE);
            MultiValueMap<String, String> formParameters = new LinkedMultiValueMap<>();
            formParameters.add(OAuth2ParameterNames.ACCESS_TOKEN, userRequest.getAccessToken().getTokenValue());


            // Именно здесь я добавляю подпись для запроса к ОДНОКЛАССНИКИ
            if (clientRegistration.getRegistrationId().equals("ok")) {
                formParameters.add("sig", generateOkSignature(userRequest.getAccessToken().getTokenValue()));
            }


            request = new RequestEntity<>(formParameters, headers, httpMethod, uri);
        }
        else {
            headers.setBearerAuth(userRequest.getAccessToken().getTokenValue());
            request = new RequestEntity<>(headers, httpMethod, uri);
        }

        return request;
    }

    private HttpMethod getHttpMethod(ClientRegistration clientRegistration) {
        if (AuthenticationMethod.FORM
                .equals(clientRegistration.getProviderDetails().getUserInfoEndpoint().getAuthenticationMethod())) {
            return HttpMethod.POST;
        }
        return HttpMethod.GET;
    }

    // ОДНОКЛАССНИКУ нужно еще запросы подписывать. скотина...
    private String generateOkSignature(String accessToken) {
        // TODO импортировать ключи из application.property
        String applicationPublicKey = "***";
        String applicationSecretKey = "***";
        String sessionSecretKey = accessToken + applicationSecretKey;
        String md5SessionSecretKey = DatatypeConverter.printHexBinary(DigestUtils.md5Digest(sessionSecretKey.getBytes(StandardCharsets.UTF_8))).toLowerCase();

        String paramValues = "application_key=" + applicationPublicKey + "method=users.getCurrentUser";
        String sign = paramValues + md5SessionSecretKey;
        return DatatypeConverter.printHexBinary(DigestUtils.md5Digest(sign.getBytes(StandardCharsets.UTF_8))).toLowerCase();
    }
}
