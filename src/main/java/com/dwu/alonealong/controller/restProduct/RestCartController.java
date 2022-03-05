package com.dwu.alonealong.controller.restProduct;

import com.dwu.alonealong.exception.NullProductException;
import com.dwu.alonealong.exception.UserNotMatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dwu.alonealong.domain.CartItem;
import com.dwu.alonealong.service.AloneAlongFacade;

import java.util.List;

@RestController
@SessionAttributes({"userSession"})
public class RestCartController {
    private AloneAlongFacade aloneAlong;

    @Autowired
    public void setAloneAlong(AloneAlongFacade aloneAlong) {
        this.aloneAlong = aloneAlong;
    }

    @GetMapping("/cart/{userId}/items")
    public List<CartItem> ViewCartItemList(@PathVariable("userId") String userId){
        return aloneAlong.getAllCartItem(userId);
    }

    @DeleteMapping("/cart/{userId}/items")
    public void DeleteCartItemList(@PathVariable("userId") String userId){
        aloneAlong.deleteAllCartItem(userId);
    }

    @PostMapping("/cart/{userId}/items")
    public void addCartItem(@RequestBody CartItem cartItem,
                                   @PathVariable("userId") String userId){
        aloneAlong.insertCartItem(cartItem.getProductId(), cartItem.getQuantity(), userId);
    }

    @PutMapping("/cart/{userId}/items/{cartItemId}")
    public void updateCartItem(@RequestBody CartItem cartItem,
                            @PathVariable("userId") String userId,
                            @PathVariable("cartItemId") long cartItemId) throws Exception {
        CartItem nowCartItem = aloneAlong.getCartItem(cartItemId);
        if(cartItem == null){
            throw new NullProductException();
        }
        if(!userId.equals(nowCartItem.getUserId())) {
            throw new UserNotMatchException();
        }
        nowCartItem.setQuantity(cartItem.getQuantity());
        aloneAlong.updateCartItem(nowCartItem);
    }

    @DeleteMapping("/cart/{userId}/items/{cartItemId}")
    public void DeleteCartItem(@PathVariable("userId") String userId,
                               @PathVariable("cartItemId") long cartItemId){
        aloneAlong.deleteCartItem(cartItemId);
    }
}
