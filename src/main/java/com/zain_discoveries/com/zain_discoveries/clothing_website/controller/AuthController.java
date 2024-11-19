package com.zain_discoveries.com.zain_discoveries.clothing_website.controller;

import com.zain_discoveries.com.zain_discoveries.clothing_website.config.JWTProvider;
import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.UserException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.User;
import com.zain_discoveries.com.zain_discoveries.clothing_website.repository.UserRepository;
import com.zain_discoveries.com.zain_discoveries.clothing_website.request.LoginRequest;
import com.zain_discoveries.com.zain_discoveries.clothing_website.response.AuthResponse;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.impl.CustomerUserServiceImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserRepository userRepository;
    private JWTProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private CustomerUserServiceImplementation customerUserServiceImplementation;

    public AuthController(UserRepository userRepository, JWTProvider jwtProvider, PasswordEncoder passwordEncoder, CustomerUserServiceImplementation customerUserServiceImplementation) {
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.customerUserServiceImplementation = customerUserServiceImplementation;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> userHandler(@RequestBody User user) throws UserException {
        String token = "";
        try {
            String userName = user.getEmail();
            String password = user.getPassword();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();

            User isUserNameExist = userRepository.findByEmail(userName);
            if (isUserNameExist != null) {
                throw new UserException("user name not found");
            }

            User createUser = new User();
            createUser.setEmail(userName);
            createUser.setPassword(passwordEncoder.encode(password));
            createUser.setFirstName(firstName);
            createUser.setLastName(lastName);

            User savedUser = userRepository.save(createUser);
            Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtProvider.generateToken(authentication);
        } catch (Exception e) {
            System.out.println("AuthController userHandler function e " + e);
            throw e;
        }
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMsg("Sign up successfully");
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) throws UserException {
        String userName = loginRequest.getUserName();
        String password = loginRequest.getPassword();
        Authentication authentication = authenticate(userName, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMsg("Login successfully");
        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customerUserServiceImplementation.loadUserByUsername(userName);

        if (userName == null) {
            throw new BadCredentialsException("username is not valid");
        }

        if (!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("please enter a valid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
