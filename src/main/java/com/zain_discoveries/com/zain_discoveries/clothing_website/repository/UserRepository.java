package com.zain_discoveries.com.zain_discoveries.clothing_website.repository;

import com.zain_discoveries.com.zain_discoveries.clothing_website.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
}
