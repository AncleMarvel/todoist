package com.example.todo.security.oauth2;

import com.example.todo.entities.User;
import com.example.todo.repositories.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SimpleOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public SimpleOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        var oAuth2User = super.loadUser(userRequest);
        var id = oAuth2User.<String>getAttribute("sub");
        var foundUser = userRepository.findById(Objects.requireNonNull(id));
        if (foundUser.isEmpty()) {
            userRepository.save(extractUserFromOAuth2User(oAuth2User));
        }
        return oAuth2User;
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
