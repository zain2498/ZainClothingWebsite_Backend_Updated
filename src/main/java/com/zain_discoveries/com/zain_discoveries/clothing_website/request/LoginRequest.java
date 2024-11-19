package com.zain_discoveries.com.zain_discoveries.clothing_website.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginRequest {
    private String userName;
    private String password;
}
