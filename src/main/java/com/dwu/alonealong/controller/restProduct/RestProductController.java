package com.dwu.alonealong.controller.restProduct;

import com.dwu.alonealong.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dwu.alonealong.service.AloneAlongFacade;

import java.util.List;

@RestController
@SessionAttributes({"userSession"})
public class RestProductController {
    private AloneAlongFacade aloneAlong;

    @Autowired
    public void setAloneAlong(AloneAlongFacade aloneAlong) {
        this.aloneAlong = aloneAlong;
    }

    @GetMapping("/shops/{pcId}")
    public List<Product> viewProductList(@PathVariable("pcId") int pcId,
                                         @RequestParam("sort") String sortType){
        return aloneAlong.getProductList(pcId, sortType);
    }

    @GetMapping("/products/{pId}")
    public Product viewProductList(@PathVariable("pId") String productId){
        return aloneAlong.getProduct(productId);
    }
}
