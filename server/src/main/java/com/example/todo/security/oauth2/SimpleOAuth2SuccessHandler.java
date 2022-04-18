package com.example.todo.security.oauth2;

import com.example.todo.entities.User;
import com.example.todo.repositories.UserRepository;
import com.example.todo.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static com.example.todo.security.oauth2.RedirectUriToCookiePersister.REDIRECT_URI_PARAM;

public class SimpleOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OAuth2Properties oAuth2Properties;

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        var redirectUrl = extractRedirectUri(request).orElseGet(() -> super.determineTargetUrl(request, response, authentication));
        validateRedirectUrl(redirectUrl);

        var storedUser = loadStoredUser((OAuth2User) authentication.getPrincipal());

        var accessToken = jwtProvider.generateAccessToken(storedUser);
        var refreshToken = jwtProvider.generateRefreshToken(storedUser);

        return generateRedirectUri(redirectUrl, accessToken, refreshToken);
    }

    private User loadStoredUser(OAuth2User oAuth2User) {
        var id = oAuth2User.<String>getAttribute("sub");
        var foundUser = userRepository.findById(Objects.requireNonNull(id));
        return foundUser.orElseGet(() -> userRepository.save(extractUserFromOAuth2User(oAuth2User)));
    }

    private String generateRedirectUri(String redirectUrl, String accessToken, String refreshToken) {
        return UriComponentsBuilder.fromUriString(redirectUrl)
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build()
                .toUriString();
    }

    private Optional<String> extractRedirectUri(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> REDIRECT_URI_PARAM.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }

    private void validateRedirectUrl(String url) {
        var clientRedirectUri = URI.create(url);

        var isRedirectUriUnknown = oAuth2Properties.getRedirectUris()
                .stream()
                .noneMatch(authorizedRedirectUri -> isUriEquals(clientRedirectUri, URI.create(authorizedRedirectUri)));

        if (isRedirectUriUnknown) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Redirect uri is unknown");
        }
    }

    private boolean isUriEquals(URI first, URI second) {
        return first.getHost().equalsIgnoreCase(second.getHost())
                && first.getPort() == second.getPort();
    }

    private User extractUserFromOAuth2User(OAuth2User oAuth2User) {
        var newUser = new User();
        newUser.setId(oAuth2User.getAttribute("sub"));
        newUser.setName(oAuth2User.getAttribute("name"));
        newUser.setEmail(oAuth2User.getAttribute("email"));
        newUser.setGender(oAuth2User.getAttribute("gender"));
        newUser.setLocale(oAuth2User.getAttribute("locale"));
        newUser.setUserpic(oAuth2User.getAttribute("picture"));
        return newUser;
    }
}
