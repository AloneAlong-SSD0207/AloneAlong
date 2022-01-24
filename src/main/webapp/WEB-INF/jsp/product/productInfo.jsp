<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
function changeQuantity(){
	var nowQuantity = $("#quantity").val();
	var totalPrice = ${product.productPrice} * nowQuantity;
	if (totalPrice < ${product.getFreeShippingPrice()}) {
	  totalPrice += ${product.getShippingFee()};
	}
	totalPrice = String(totalPrice).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	$("#totalPrice").text(totalPrice);
};
</script>
<form method="get">
<div class="row mx-5 mb-5">
	<!-- ���� -->
	<div class="col-md-6 my-3">
		<svg class="img rounded"
			style="background-image: url(<c:url value='/images/${product.productId}.png'/>); background-size: cover; background-position: center; width: 100%; height: 400px;"></svg>
	</div>
	<!-- ���� ���� -->
	<div class="col-md-6 my-3" style="height: 400px">
		<div class="mx-2" style="height: 150px">
			<h1 class="text-right mb-3">${product.productName}</h1>
			<h3 class="text-right"><b class="text-orange-roboto">
				<fmt:formatNumber value="${product.productPrice}" pattern="#,###,###"/></b> ��</h3>
			<p class="text-right text-muted">${product.productInfo}</p>
		</div>
		<div class="mt-4 priceInfo" style="height: 110px;">
			<p class="row px-3 mb-3 d-flex align-items-center">
				<span class=" border-right pr-4 mr-4">������</span>
				<input class="form-control text-center w-25" onchange="changeQuantity()" name="quantity" id="quantity" type="number" value="${product.quantity}" min="1">
			</p>
			<p class="row px-3 mb-2"><span class="border-right pr-4 mr-4">��ۺ�</span>
				<fmt:formatNumber value="${product.getShippingFee()}" pattern="#,###,###"/>��</p>
				<small class="text-muted">
					<fmt:formatNumber value="${product.getFreeShippingPrice()}" pattern="#,###,###"/>�� �̻� ���Ž� ���� ���</small>
		</div>
		
		<!-- �ӽ� -->
		<input name="type" type="hidden" value="product">
		<input name="productId" type="hidden" value="${productId}">
		<div class="totalPriceInfo bg-light">
			<p class="text-right">�� �ݾ� <b class="pl-2 text-roboto">
				<span id="totalPrice"><fmt:formatNumber value="${product.getUnitPrice()}" pattern="#,###,###"/></span></b>��</p>
			<div class="row justify-content-around">
				<button type="submit" class="btn btn-green rounded-pill"
					onclick="javascript: form.action='/cart/insert/${productId}/product';">
					<small><i class="fas fa-shopping-cart pr-1"></i></small> ��ٱ���</button>
				<button type="submit" class="btn btn-orange rounded-pill"
					onclick="javascript: form.action='/shop/order';">
					<small> <i class="far fa-credit-card pr-1"></i></small> �����ϱ�</button>
			</div>
		</div>
	</div>
</div>
</form>