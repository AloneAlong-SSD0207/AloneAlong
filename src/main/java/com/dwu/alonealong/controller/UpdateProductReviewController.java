package com.dwu.alonealong.controller;

import javax.servlet.http.HttpServletRequest;

import com.dwu.alonealong.exception.ContentSizeException;
import com.dwu.alonealong.exception.UserNotMatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.dwu.alonealong.domain.Product;
import com.dwu.alonealong.domain.ProductReview;
import com.dwu.alonealong.service.AloneAlongFacade;

@Controller
@SessionAttributes({"product", "userSession"})
public class UpdateProductReviewController {
	private AloneAlongFacade aloneAlong;

	@Autowired
	public void setAloneAlong(AloneAlongFacade aloneAlong) {
		this.aloneAlong = aloneAlong;
	}
	
	@RequestMapping("/shop/review/update")
	public String handleRequest(HttpServletRequest request,
//			@ModelAttribute("userSession") UserSession userSession,
			@RequestParam(value="productId") String productId,
			@RequestParam(value="reviewId") String reviewId,
			@RequestParam(value="rating") int rating,
			@RequestParam(value="contents") String contents, 
			ModelMap model) throws Exception {
		UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
		if(userSession == null) {
			return "redirect:/login";
		}
		String userId = userSession.getUser().getId();
		
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

}