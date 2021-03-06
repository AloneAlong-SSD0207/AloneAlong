<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
$(document).on("click", ".open-reviewModal", function () {
     var productId = $(this).data('id');
     $(".modal-body #productId").val(productId);
});
</script>
<c:forEach var="order" items="${productOrderList}">
<div class="col-md-12">
		<div class="mx-3 mt-3">
		<div class="form-row float-right">
		</div>
		<div class="d-flex align-items-center">
			<svg class="img rounded mr-3" style="background-image: url('../images/${order.getProductOrder().getLineItems().get(0).productId}.png'); background-size: cover; background-position: center"
										width="100px" height="100px"></svg>
										<div class="cart-title text-left ml-3">
				<p><strong>${order.getProductOrder().getLineItems().get(0).product.productName}</strong>
				<c:if test="${order.getProductOrder().getLineItems().size() > 1}">외 ${order.getProductOrder().getLineItems().size() - 1}건</c:if></p>
				<small><strong class="text-orange">주문번호 </strong> &nbsp; ${order.orderId}</small><br>
				<small><strong class="text-orange">주문일자 </strong> &nbsp; ${order.orderDate}</small><br>
				<small><strong class="text-orange">결제금액 </strong> &nbsp;
					<fmt:formatNumber value="${order.totalPrice}" pattern="#,###,###"/>원</small><br>
				<small><strong class="text-orange">진행상태 </strong> &nbsp; ${order.status}</small><br>
			</div>
		</div>
		<div class="text-right"><a type="button" class="btn btn-sm btn-orange" data-toggle="collapse" data-parent="#accordion"
				href="#collapse${order.orderId}" aria-expanded="false"> >>주문상세내역 </a></div>
		</div>
		<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="false">
			<div class="panel panel-default">
				<div class="panel-heading" role="tab">
				</div>
				<div id="collapse${order.orderId}" class="panel-collapse collapse" role="tabpanel">
					<div class="panel-body m-1 p-2">
					<div class="p-3 mb-3 rounded bg-light">
						<small><strong class="text-green">결제 정보</strong> &nbsp; ${order.cardName} ${order.cardNum}</small><hr>
						<small><strong class="text-green">배송 정보</strong></small><br>
						<small><strong>수령인</strong> &nbsp; ${order.getProductOrder().shipName}</small><br>
						<small><strong>연락처</strong> &nbsp; ${order.getProductOrder().shipPhone}</small><br>
						<small><strong>배송지</strong> &nbsp; (${order.getProductOrder().shipZip}) ${order.getProductOrder().shipAddress} ${order.getProductOrder().shipAddress2}</small><br>
					</div>
					<div class="p-3 mb-3 rounded bg-gradient-light">
						<c:forEach var="item" items="${order.getProductOrder().getLineItems()}">
						<div class="row d-flex align-items-center">
							<div class="col-md-1">
								<a href=<c:url value="/shop/${item.productId}"/>>
			<svg class="img rounded mr-3" style="background-image: url(<c:url value='/images/${item.productId}.png'/>); background-size: cover; background-position: center"
										width="50px" height="50px"></svg></a></div>
							<div class="col-md-2">
								<a href=<c:url value="/shop/${item.productId}"/>>
								<small style="color: black;	text-decoration: none;">${item.product.productName}</small></a></div>
							<div class="col-md-1 text-right"><small>x</small></div>
							<div class="col-md-1 text-right"><small>${item.quantity}</small></div>
							<div class="col-md-1 text-right"><small>=</small></div>
							<div class="col-md-2 text-right"><small><fmt:formatNumber value="${item.unitPrice}" pattern="#,###,###"/>원</small></div>
							<div class="col-md-4 text-right">
								<button class="open-reviewModal btn btn-sm btn-green" data-toggle="modal" data-id="${item.productId}" data-target="#reviewModal">리뷰 작성</button></div>				
						</div>
						<hr>
						</c:forEach>
						<div class="row d-flex align-items-center">
						<div class="col-md-4"></div>
						<div class="col-md-2 text-right"><small>배송비</small></div>
						<div class="col-md-2 text-right"><small><fmt:formatNumber value="${order.getShippingFee()}" pattern="#,###,###"/>원</small></div>
						</div>
						<hr>
					</div>
					</div>
				</div>
			</div>
		</div>
		<br>
		<hr>
</div>
<div class="modal fade" id="reviewModal" tabindex="-1" role="dialog" aria-hidden="true">
	<form action='<c:url value="/shop/review/insert"/>'>
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content pb-4">
				<div class="modal-header">
					<h5 class="modal-title">리뷰 작성</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group">
									<select name="rating" class="custom-select focus-shadow-0">
										<option value="5">★★★★★ (5/5)</option>
										<option value="4">★★★★☆ (4/5)</option>
										<option value="3">★★★☆☆ (3/5)</option>
										<option value="2">★★☆☆☆ (2/5)</option>
										<option value="1">★☆☆☆☆ (1/5)</option>
									</select>
								</div>
							</div>
						</div>
						<div class="form-group">
							<textarea rows="4" name="contents" id="review"
								placeholder="리뷰를 작성하세요" required="" class="form-control"></textarea>
						</div>
					<input type="hidden" name="productId" id="productId" value=""/>
				</div>
				<div class="text-center">
					<button type="submit" class="btn btn-orange rounded-pill w-25 pb-2">작성하기</button>
				</div>
			</div>
		</div>
	</form>
</div>
</c:forEach>