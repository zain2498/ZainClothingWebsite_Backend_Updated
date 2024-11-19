package com.zain_discoveries.com.zain_discoveries.clothing_website.service;

import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.UserException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.User;

public interface UserService {
    public User findUserById(Long userId) throws UserException;
    public User findUserProfileByJwtToken(String jwt) throws  UserException;
}
