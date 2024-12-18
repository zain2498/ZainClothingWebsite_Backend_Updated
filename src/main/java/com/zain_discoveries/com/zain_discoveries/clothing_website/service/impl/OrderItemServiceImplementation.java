package com.zain_discoveries.com.zain_discoveries.clothing_website.service.impl;

import com.zain_discoveries.com.zain_discoveries.clothing_website.model.OrderItem;
import com.zain_discoveries.com.zain_discoveries.clothing_website.repository.OrderItemRepository;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.OrderItemService;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImplementation implements OrderItemService {

    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
