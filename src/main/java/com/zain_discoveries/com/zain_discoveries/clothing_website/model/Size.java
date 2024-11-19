package com.zain_discoveries.com.zain_discoveries.clothing_website.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Size {
    private String name;
    private String quantity;
}
