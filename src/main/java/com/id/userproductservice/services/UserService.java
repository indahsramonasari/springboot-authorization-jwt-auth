package com.id.userproductservice.services;

import com.id.userproductservice.configuration.JwtConfiguration;
import com.id.userproductservice.domain.ResponseStatus;
import com.id.userproductservice.domain.UserRequest;
import com.id.userproductservice.domain.UserResponse;
import com.id.userproductservice.exception.GenericException;
import com.id.userproductservice.models.User;
import com.id.userproductservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtConfiguration jwtConfiguration;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CacheStore cacheStore;

    @Value(value = "${session.lifetime}")
    private int sessionLifetime;

    private static final String STATUS_FAILED = "Failed";
    private static final String STATUS_SUCCESS = "Success";
    public static final String KEY_CACHE_USER = "USER_TOKEN";

    public UserResponse loginUser(UserRequest userRequest){
        UserResponse userResponse = new UserResponse();

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtConfiguration.generateJwtToken(authentication);
            UserImpl userDetails = (UserImpl) authentication.getPrincipal();

            userResponse = UserResponse.builder()
                    .statusMessage(STATUS_SUCCESS)
                    .token(token)
                    .id(userDetails.getId())
                    .username(userDetails.getUsername())
                    .gender(userDetails.getGender())
                    .email(userDetails.getEmail())
                    .build();

            cacheStore.storeCache(KEY_CACHE_USER.concat(userDetails.getUsername()), userResponse, sessionLifetime);
        } catch (GenericException ex){
            log.error("Error when login {}", ex);
            throw new GenericException("01", STATUS_FAILED, "User couldn't login!");
        }

        return userResponse;
    }

    public ResponseStatus signUpUser(UserRequest userRequest){
        ResponseStatus response = new ResponseStatus();
        User userbyName = userRepository.findOneByUsername(userRequest.getUsername());
        User userbyEmail = userRepository.findOneByEmail(userRequest.getEmail());

        if (userbyName != null || userbyEmail != null) {
            throw new GenericException("01", STATUS_FAILED, "Username or email already used!");
        } else {
            try {
                User user = User.builder()
                        .username(userRequest.getUsername())
                        .email(userRequest.getEmail())
                        .gender(userRequest.getGender())
                        .password(encoder.encode(userRequest.getPassword()))
                        .build();

                userRepository.save(user);
                response.setMessageDesc("User successfully registered");
                response.setStatusMessage(STATUS_SUCCESS);
            } catch (GenericException ex){
                log.error("Error when sign up user {}", ex);
                throw new GenericException("01", STATUS_FAILED, "Failed Register User");
            }
        }
        return response;
    }

    public UserResponse getUserProfile(String token){
        UserResponse userResponse = new UserResponse();
        try {
            String username = jwtConfiguration.getUsernameFromJwtToken(token);
            userResponse = (UserResponse) cacheStore.getCache(KEY_CACHE_USER.concat(username));
            log.info("userResponse {} ", userResponse);
        } catch (GenericException ex){
            throw new GenericException("09", "Failed", "Error when find user profile!");
        }
        return userResponse;
    }

    public ResponseStatus removeAuth(String token){
        try {
            SecurityContextHolder.getContext().setAuthentication(null);
            String username = jwtConfiguration.getUsernameFromJwtToken(token);
            cacheStore.removeCache(KEY_CACHE_USER.concat(username));
        } catch (GenericException ex){
            throw new GenericException("10", "Failed", "Failed to sign out your account!");
        }
        return new ResponseStatus("Success", "Successfully sign out!");
    }
}
