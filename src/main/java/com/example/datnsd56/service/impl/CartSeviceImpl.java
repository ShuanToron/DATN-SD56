package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.Cart;
import com.example.datnsd56.entity.CartItem;
import com.example.datnsd56.repository.CartItemRepository;
import com.example.datnsd56.repository.CartRepository;
import com.example.datnsd56.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SessionScope
@Service
public class CartSeviceImpl implements CartService {

    @Autowired
        private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
   Map<Integer,CartItem> maps=new HashMap<>();

    @Override
    public void add(CartItem item){
       CartItem cartItem=maps.get(item.getProductDetails().getId());
       if(cartItem == null){
           maps.put(item.getProductDetails().getId(),item);
       }else {
           cartItem.setQuantity(cartItem.getQuantity() +1);
       }
   }
    @Override
    public void remove(Integer id){
       maps.remove(id);
}
    @Override
    public void clear(){
       maps.clear();
}
    @Override
    public int getCount(){
       return maps.values().size();
}

    @Override
    public void add1(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public CartItem update(Integer id, Integer quantity){
        CartItem cartItem=maps.get(id);
        cartItem.setQuantity(quantity);
        return  cartItem;
    }
    @Override
    public Collection<CartItem> getAllItem(){
       return maps.values();
}

//    @Override
//    public List<ViewCart> getAllCartItem() {
//        return cartItemRepository.getAllCartItem();
//    }

    @Override
public double getAmount(){
        return maps.values().stream()
            .mapToDouble(item -> item.getQuantity() * item.getProductDetails().getSellPrice()).sum();
}

    @Override
    public Cart findByNguoiDungId(Integer id) {
        return cartRepository.findByAccountId(id);
    }
}
