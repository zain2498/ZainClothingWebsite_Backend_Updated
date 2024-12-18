package com.zain_discoveries.com.zain_discoveries.clothing_website.request;

import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Address;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderRequest {
    private User user;
    private Address shippingAddress;
}
