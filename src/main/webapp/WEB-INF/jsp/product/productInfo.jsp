<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<div class="row mx-5 mb-5">
	<!-- ���� -->
	<div class="col-md-6 my-3">
		<svg class="img"
			style="background-image: url('data:image/jpeg;base64,${product.img64}'); background-size: cover; background-position: center; width: 100%; height: 400px;"></svg>
	</div>
	<!-- ���� ���� -->
	<div class="col-md-6 my-3" style="height: 400px">
		<div class="mx-2" style="height: 150px">
			<h1 class="text-right mb-3">${product.productName}</h1>
			<h3 class="text-right"><b class="text-orange-roboto">
				<fmt:formatNumber value="${product.productPrice}" pattern="#,###,###"/></b> ��</h3>
		</div>
		<div class="mx-2 my-2 priceInfo" style="height: 110px;">
			<form class="form-inline" action='<c:url value="/shop/${productId}"/>'>
				<p class="mb-3">
					<span class=" border-right pr-4 mr-4">������</span>
					<input class="form-control text-center w-25" name="quantity" type="number" value="${product.quantity}" min="1">
					<!-- �ӽ� ��ư -->
					<button class="btn btn-light" type="submit">��������</button>
				</p>
			</form>
			<p class="mb-2"><span class="border-right pr-4 mr-4">��ۺ�</span>
				<fmt:formatNumber value="${product.shippingFee}" pattern="#,###,###"/>��</p>
			<small class="text-muted">30,000�� �̻� ���Ž� ���� / ���� �� ���� ���� �߰� 5,000��</small>
		</div>
		
		<!-- �ӽ� -->
		<form method="post">
		<input name="type" type="hidden" value="product">
		<input name="productId" type="hidden" value="${product.productId}">
		<input name="quantity" type="hidden" value="${product.quantity}">
		<div class="totalPriceInfo bg-light">
			<p class="text-right">�� �ݾ� <b class="pl-2 text-roboto">
				<fmt:formatNumber value="${product.getUnitPrice()}" pattern="#,###,###"/></b>��</p>
			<div class="row justify-content-around">
				<button type="submit" class="btn btn-green rounded-pill"
					onclick="javascript: form.action='/cart/insert/${productId}/${product.quantity}/product';">
					<small><i class="fas fa-shopping-cart pr-1"></i></small> ��ٱ���</button>
				<button type="submit" class="btn btn-orange rounded-pill"
					onclick="javascript: form.action='/shop/order';">
					<small> <i class="far fa-credit-card pr-1"></i></small> �����ϱ�</button>
			</div>
		</div>
		</form>
	</div>
</div>