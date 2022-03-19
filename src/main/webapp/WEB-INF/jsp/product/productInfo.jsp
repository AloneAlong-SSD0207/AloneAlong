<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script>
$(document).ready(function (){

})
function changeQuantity(){
	var nowQuantity = $("#quantity").val();
	var totalPrice = ${product.productPrice} * nowQuantity;
	if (totalPrice < ${product.getFreeShippingPrice()}) {
	  totalPrice += ${product.getShippingFee()};
	}
	totalPrice = String(totalPrice).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	$("#totalPrice").text(totalPrice);
};
function insertCart(){
	const nowQuantity = $("#quantity").val();
	if(nowQuantity > ${product.productStock}){
		$("#pStock").text('${product.productStock}');
		$("#pName").text('${product.productName}');
		jQuery.noConflict();
		$('#stockModal').modal('show');
	}
	else{
		let cartItem = { productId : ${productId}, quantity : nowQuantity };
		$.ajax({
			url:"/cart/${userSession.user.user_id}/items",
			type:"POST",
			contentType:"application/json",
			data:JSON.stringify(cartItem),
			success: function(){
				jQuery.noConflict();
				$("#cartModal").modal("show");
			},
			error:function(){
				alert("오류가 발생했습니다.");
			},
		})
		<%--location.href="/cart/insert/" + ${productId} + "/product?quantity=" + nowQuantity;--%>
	}
};
function insertProductReviewRecommend(rId){
	const reviewId = Number(rId);
	let user = { user_id : ${userSession.user.user_id} };
	$.ajax({
		url:"/products/${productId}/reviews/" + reviewId + "/recommends",
		type:"POST",
		contentType:"application/json",
		data:JSON.stringify(user),
		success: function(){
			alert("리뷰를 추천했습니다.");
			location.reload();
		},
		error:function(){
			jQuery.noConflict();
			$("#recommendReviewModal").modal("show");
		},
	})
}
function deleteProductReviewRecommend(rId){
	const reviewId = Number(rId);
	$.ajax({
		url:"/products/${productId}/reviews/" + reviewId + "/recommends/${userSession.user.user_id}",
		type:"DELETE",
		contentType:"application/json",
		success: function(){
			alert("추천을 취소했습니다.");
			location.reload();
		},
		error:function(){
			alert("오류가 발생했습니다.");
		},
	})
};
function insertProductReview(){
	const review = $("#insertForm").serializeArray();
	let productReview = {
		rating : review[0].value,
		reviewContents : review[1].value,
		productId : ${productId},
		userId : ${userSession.user.user_id},
	}
	$.ajax({
		url:"/products/${productId}/reviews",
		type:"POST",
		contentType:"application/json",
		data:JSON.stringify(productReview),
		success: function(){
			alert("리뷰가 작성되었습니다.");
			location.reload();
		},
		error:function(){
			alert("오류가 발생했습니다.");
		},
	})
};
function updateProductReview(){
	const review = $("#updateForm").serializeArray();
	let productReview = {
		rating : review[0].value,
		reviewContents : review[1].value,
		reviewId : review[3].value,
		userId : ${userSession.user.user_id},
	}
	$.ajax({
		url:"/products/${productId}/reviews/" + review[3].value,
		type:"PUT",
		contentType:"application/json",
		data:JSON.stringify(productReview),
		success: function(){
			alert("리뷰가 수정되었습니다.");
			location.reload();
		},
		error:function(){
			alert("오류가 발생했습니다.");
		},
	})
};
function deleteProductReview(){
	const reviewId = $(".modal-body #reviewId").val();
	alert(reviewId)
	$.ajax({
		url:"/products/${productId}/reviews/" + reviewId + "/${userSession.user.user_id}",
		type:"DELETE",
		contentType:"application/json",
		success: function(){
			location.reload();
		},
		error:function(){
			alert("오류가 발생했습니다.");
		},
	})
};
</script>
<div class="row mx-5 mb-5">
	<!-- 사진 -->
	<div class="col-md-6 my-3">
		<svg class="img rounded"
			style="background-image: url(<c:url value='/images/${product.productId}.png'/>); background-size: cover; background-position: center; width: 100%; height: 400px;"></svg>
	</div>
	<!-- 가격 정보 -->
	<div class="col-md-6 my-3" style="height: 400px">
		<div class="mx-2" style="height: 150px">
			<h1 class="text-right mb-3">${product.productName}</h1>
			<h3 class="text-right"><b class="text-orange-roboto">
				<fmt:formatNumber value="${product.productPrice}" pattern="#,###,###"/></b> 원</h3>
			<p class="text-right text-muted">${product.productInfo}</p>
		</div>
		<div class="mt-4 priceInfo" style="height: 110px;">
			<p class="row px-3 mb-3 d-flex align-items-center">
				<span class=" border-right pr-4 mr-4">수량　</span>
				<input class="form-control text-center w-25" onchange="changeQuantity()" name="quantity" id="quantity" type="number" value="${product.quantity}" min="1">
			</p>
			<p class="row px-3 mb-2"><span class="border-right pr-4 mr-4">배송비</span>
				<fmt:formatNumber value="${product.getShippingFee()}" pattern="#,###,###"/>원</p>
				<small class="text-muted">
					<fmt:formatNumber value="${product.getFreeShippingPrice()}" pattern="#,###,###"/>원 이상 구매시 무료 배송</small>
		</div>
		
		<!-- 임시 -->
		<input name="type" type="hidden" value="product">
		<input name="productId" type="hidden" value="${productId}">
		<div class="totalPriceInfo bg-light">
			<p class="text-right">총 금액 <b class="pl-2 text-roboto">
				<span id="totalPrice"><fmt:formatNumber value="${product.getUnitPrice()}" pattern="#,###,###"/></span></b>원</p>
			<div class="row justify-content-around">
				<button type="submit" class="btn btn-green rounded-pill"
						onclick="insertCart()">
					<small><i class="fas fa-shopping-cart pr-1"></i></small> 장바구니</button>
				<button type="submit" class="btn btn-orange rounded-pill"
					onclick="order()">
					<small> <i class="far fa-credit-card pr-1"></i></small> 결제하기</button>
			</div>
		</div>
	</div>
</div>