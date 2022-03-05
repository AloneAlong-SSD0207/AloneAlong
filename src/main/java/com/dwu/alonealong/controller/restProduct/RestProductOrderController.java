package com.dwu.alonealong.controller.restProduct;

import com.dwu.alonealong.domain.Order;
import com.dwu.alonealong.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dwu.alonealong.service.AloneAlongFacade;

import java.util.List;

@RestController
@SessionAttributes({"userSession"})
public class RestProductOrderController {
    private AloneAlongFacade aloneAlong;

    @Autowired
    public void setAloneAlong(AloneAlongFacade aloneAlong) {
        this.aloneAlong = aloneAlong;
    }

    @GetMapping("/orders/{userId}/products")
    public List<Order> viewOrderList(@PathVariable("userId") String userId){
        System.out.println("호출됨");
        return aloneAlong.getOrdersByUserId(userId);
    }
}
