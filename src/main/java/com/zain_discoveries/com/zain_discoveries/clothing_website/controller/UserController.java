package com.zain_discoveries.com.zain_discoveries.clothing_website.controller;

import com.zain_discoveries.com.zain_discoveries.clothing_website.model.User;
import com.zain_discoveries.com.zain_discoveries.clothing_website.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/API/users")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(){
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByEmail(email);
            if(user == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user,HttpStatus.OK);
        }catch (Exception e){
            System.out.println("user not found");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
