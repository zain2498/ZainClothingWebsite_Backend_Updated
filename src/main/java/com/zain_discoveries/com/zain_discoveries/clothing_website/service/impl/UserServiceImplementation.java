package com.zain_discoveries.com.zain_discoveries.clothing_website.service.impl;

import com.zain_discoveries.com.zain_discoveries.clothing_website.config.JWTProvider;
import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.UserException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.User;
import com.zain_discoveries.com.zain_discoveries.clothing_website.repository.UserRepository;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImplementation implements UserService {
    private UserRepository userRepository;
    private JWTProvider jwtProvider;

    public UserServiceImplementation(UserRepository userRepository, JWTProvider jwtProvider){
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User findUserById(Long userId) throws UserException {

       Optional<User> user = userRepository.findById(userId);
       if (!user.isPresent()){
           throw new UserException("User not found with given userId "+userId);
       }
       return user.get();
    }

    @Override
    public User findUserProfileByJwtToken(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwt);
        if(email.isEmpty()){
            throw new UserException("User not found with the given email address "+email);
        }
        User user = userRepository.findByEmail(email);
        return user;
    }
}
