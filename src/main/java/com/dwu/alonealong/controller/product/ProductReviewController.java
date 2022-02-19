package com.dwu.alonealong.controller.product;

import com.dwu.alonealong.controller.UserSession;
import com.dwu.alonealong.domain.Product;
import com.dwu.alonealong.domain.ProductReview;
import com.dwu.alonealong.exception.ContentSizeException;
import com.dwu.alonealong.exception.NotLoginException;
import com.dwu.alonealong.exception.UserNotMatchException;
import com.dwu.alonealong.service.AloneAlongFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@SessionAttributes({"product", "userSession"})
public class ProductReviewController  {
    private AloneAlongFacade aloneAlong;
    private static final int PAGE_SIZE = 3;
    private static final int UNIT_PAGE_SIZE = 5;

    @Autowired
    public void setAloneAlong(AloneAlongFacade aloneAlong) {
        this.aloneAlong = aloneAlong;
    }

    @RequestMapping("/shop/{productId}/review")
    public String viewReview(HttpServletRequest request,
                                @PathVariable("productId") String productId,
                                @RequestParam(value="page", defaultValue="1") int page,
                                @RequestParam(value="sortType", defaultValue="new") String sortType,
                                @RequestParam(value="quantity", defaultValue="1") int quantity,
                                ModelMap model) throws Exception {
        String userId = null;
        UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
        if(userSession != null) {
            userId = userSession.getUser().getId();
            model.put("isPurchaser", aloneAlong.checkUsersOrder(userId, productId));
        }

        String sortTypeName = "최신순";
        switch(sortType) {
            case "recommend" : sortTypeName = "추천순"; break;
            case "lowRating" : sortTypeName = "낮은 평점순"; break;
            case "highRating" : sortTypeName = "높은 평점순"; break;
        }

        Product product = this.aloneAlong.getProduct(productId);
        List<ProductReview> reviewList = this.aloneAlong.getProductReviewList(productId, sortType, userId);
        double avg = 0;
        for (ProductReview review : reviewList){
            avg += review.getRating();
        }
        model.put("numOfReviews", reviewList.size());
        model.put("averageOfReviews", reviewList.size() == 0 ? 0 : avg / reviewList.size());
        model.put("mostRatingOfReviews", aloneAlong.mostRatingOfReviews(productId));

        PagedListHolder<ProductReview> pagedReviewList = new PagedListHolder<ProductReview>(reviewList);
        pagedReviewList.setPageSize(PAGE_SIZE);
        pagedReviewList.setPage(page - 1);

        product.setQuantity(quantity);
        model.put("product", product);
        model.put("pcId", product.getPcId());
        model.put("reviewList", pagedReviewList.getPageList());
        model.put("sortTypeName", sortTypeName);

        model.put("page", pagedReviewList.getPage() + 1);
        int startPage = (pagedReviewList.getPage() / UNIT_PAGE_SIZE) * UNIT_PAGE_SIZE + 1;
        int lastPage = pagedReviewList.getPageCount();
        model.put("startPage", startPage);
        model.put("lastPage", lastPage);
        model.put("nextStartPage", (startPage + UNIT_PAGE_SIZE > lastPage) ? 0 : startPage + UNIT_PAGE_SIZE);

        model.put("userId", userId);
        return "productReview";
    }

    @RequestMapping("/shop/review/insert")
    public String insertReview(HttpServletRequest request,
                               @RequestParam(value="productId") String productId,
                               @RequestParam(value="rating") int rating,
                               @RequestParam(value="contents") String contents)
                                throws Exception {
        String userId = UserSession.getUserId(request);

        if(!aloneAlong.checkUsersOrder(userId, productId)){
            throw new UserNotMatchException();
        }
        if (contents.length() > ProductReview.MAX_CONTENT_SIZE) {
            throw new ContentSizeException();
        }

        ProductReview productReview = new ProductReview();
        productReview.setUserId(userId);
        productReview.setProductId(productId);
        productReview.setRating(rating);
        productReview.setReviewContents(contents);

        this.aloneAlong.insertProductReview(productReview);
        return "redirect:/shop/" + productId + "/review";
    }

    @RequestMapping("/shop/review/update")
    public String updateReview(HttpServletRequest request,
                               @RequestParam(value="productId") String productId,
                               @RequestParam(value="reviewId") long reviewId,
                               @RequestParam(value="rating") int rating,
                               @RequestParam(value="contents") String contents)
                               throws Exception {
        String userId = UserSession.getUserId(request);

        //product를 구매한 user인지 검사, 리뷰 내용 검사
        ProductReview productReview = aloneAlong.getProductReview(reviewId, userId);
        if(!aloneAlong.checkUsersOrder(userId, productId)){
            throw new UserNotMatchException();
        }
        if (contents.length() > ProductReview.MAX_CONTENT_SIZE) {
            throw new ContentSizeException();
        }
        productReview.setRating(rating);
        productReview.setReviewContents(contents);
        aloneAlong.updateProductReview(productReview);

        return "redirect:/shop/" + productId + "/review";
    }

    @RequestMapping("/shop/review/delete")
    public String deleteReview(HttpServletRequest request,
                               @RequestParam(value="productId") String productId,
                               @RequestParam(value="reviewId") long reviewId)
                               throws Exception {
        String userId = UserSession.getUserId(request);

        ProductReview productReview = aloneAlong.getProductReview(reviewId, userId);
        if (!productReview.getUserId().equals(userId)) {
            throw new UserNotMatchException();
        }
        this.aloneAlong.deleteProductReview(reviewId);

        return "redirect:/shop/" + productId + "/review";
    }

    @RequestMapping("/shop/{productId}/review/recommend/{reviewId}")
    public String recommendReview(HttpServletRequest request,
                                @PathVariable("reviewId") long reviewId,
                                @RequestParam(value="page", defaultValue="1") int page,
                                @RequestParam(value="sortType", defaultValue="new") String sortType,
                                @RequestParam(value="quantity", defaultValue="1") int quantity)
                                throws Exception {
        String userId = UserSession.getUserId(request);

        //자신의 리뷰 추천 여부 검사
        ProductReview productReview = aloneAlong.getProductReview(reviewId, userId);
        if (productReview.getUserId().equals(userId)) {
            return "redirect:/shop/{productId}/review?quantity=" + quantity + "&page=" + page + "&sortType=" + sortType + "&recommendError=true";
        }
        else {
            if(productReview.getCheckRecommend() == false) {
                productReview.increaseRecommend();
                aloneAlong.updateProductReview(productReview);
                aloneAlong.insertProductReviewRecommend(productReview.getReviewId(), userId);
            }
            else {
                productReview.decreaseRecommend();
                aloneAlong.updateProductReview(productReview);
                aloneAlong.deleteProductReviewRecommend(productReview.getReviewId(), userId);
            }
        }
        return "redirect:/shop/{productId}/review";
    }
}
