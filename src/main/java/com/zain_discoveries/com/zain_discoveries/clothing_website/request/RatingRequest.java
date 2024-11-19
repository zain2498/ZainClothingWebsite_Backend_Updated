package com.zain_discoveries.com.zain_discoveries.clothing_website.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequest  {
    private Long productId;
    private double rating;
}
