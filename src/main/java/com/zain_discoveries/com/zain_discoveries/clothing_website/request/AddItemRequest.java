package com.zain_discoveries.com.zain_discoveries.clothing_website.request;

import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddItemRequest {
    private Long productId;
    private int size;
    private int quantity;
    private Integer price;
}
