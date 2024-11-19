package com.zain_discoveries.com.zain_discoveries.clothing_website.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class ReviewRequest {
    private Long productId;
    private String review;
}
