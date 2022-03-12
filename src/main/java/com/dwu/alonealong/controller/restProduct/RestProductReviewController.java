package com.dwu.alonealong.controller.restProduct;

import com.dwu.alonealong.domain.ProductReview;
import com.dwu.alonealong.domain.User;
import com.dwu.alonealong.exception.ContentSizeException;
import com.dwu.alonealong.exception.UserNotMatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import com.dwu.alonealong.service.AloneAlongFacade;

import java.util.List;

@RestController
@SessionAttributes({"userSession"})
public class RestProductReviewController {
    private AloneAlongFacade aloneAlong;


    @Autowired
    public void setAloneAlong(AloneAlongFacade aloneAlong) {
        this.aloneAlong = aloneAlong;
    }

    @GetMapping("/products/{pId}/reviews")
    public List<ProductReview> ViewProductReviewList(@PathVariable("pId") String productId,
                                           @RequestParam("userId") String userId,
                                           @RequestParam("sort") String sortType){
        return this.aloneAlong.getProductReviewList(productId, sortType, userId);
    }

    @PostMapping("/products/{pId}/reviews")
    public void addProductReview(@RequestBody ProductReview productReview,
                                 @PathVariable("pId") String productId) throws Exception {
        System.out.println("호출됨");
        System.out.println(productReview.toString());
        if(!aloneAlong.checkUsersOrder(productReview.getUserId(), productId)){
            throw new UserNotMatchException();
        }
        if (productReview.getReviewContents().length() > ProductReview.MAX_CONTENT_SIZE) {
            throw new ContentSizeException();
        }
        aloneAlong.insertProductReview(productReview);
    }

    @PutMapping("/products/{pId}/reviews/{reviewId}")
    public void updateProductReview(@RequestBody ProductReview productReview,
                                 @PathVariable("pId") String productId,
                                 @PathVariable("reviewId") long reviewId) throws Exception {
        String userId = productReview.getUserId();
        ProductReview review = aloneAlong.getProductReview(reviewId, userId);
        if(!aloneAlong.checkUsersOrder(userId, productId)){
            throw new UserNotMatchException();
        }
        if (productReview.getReviewContents().length() > ProductReview.MAX_CONTENT_SIZE) {
            throw new ContentSizeException();
        }
        review.setRating(productReview.getRating());
        review.setReviewContents(productReview.getReviewContents());
        aloneAlong.updateProductReview(review);
    }

    @DeleteMapping("/products/{pId}/reviews/{reviewId}/{userId}")
    public void deleteProductReview(@PathVariable("userId") String userId,
                                    @PathVariable("reviewId") long reviewId,
                                    @PathVariable("pId") String productId) throws UserNotMatchException {
        ProductReview productReview = aloneAlong.getProductReview(reviewId, userId);
        if (!productReview.getUserId().equals(userId)) {
            throw new UserNotMatchException();
        }
        aloneAlong.deleteProductReview(reviewId);
    }

    //recommend
    @PostMapping("/products/{pId}/reviews/{reviewId}/recommends")
    public void addProductReviewRecommend(@RequestBody User user,
                                    @PathVariable("reviewId") long reviewId,
                                    @PathVariable("pId") String productId) throws Exception {
        String userId = user.getUser_id();
        aloneAlong.insertProductReviewRecommend(reviewId, userId);
        ProductReview review = aloneAlong.getProductReview(reviewId, userId);
        if (review.getUserId().equals(userId)) {
            throw new Exception();
        }
        review.increaseRecommend();
        aloneAlong.updateProductReview(review);
    }

    @DeleteMapping("/products/{pId}/reviews/{reviewId}/recommends/{userId}")
    public void deleteProductReviewRecommend(@PathVariable("userId") String userId,
                                          @PathVariable("reviewId") long reviewId,
                                          @PathVariable("pId") String productId){
        aloneAlong.deleteProductReviewRecommend(reviewId, userId);
        ProductReview review = aloneAlong.getProductReview(reviewId, userId);
        review.decreaseRecommend();
        aloneAlong.updateProductReview(review);
    }
}
