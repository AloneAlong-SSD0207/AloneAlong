<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<div class="row mx-5 mb-5">
	<!-- ���� -->
	<div class="col-md-6 my-3">
		<svg class="img"
			style="background-image: url('https://img-cf.kurly.com/shop/data/goods/1575003713758y0.jpg'); background-size: cover; background-position: center; width: 100%; height: 400px;"></svg>
	</div>
	<!-- ���� ���� -->
	<div class="col-md-6 my-3" style="height: 400px">
		<div class="mx-2" style="height: 150px">
			<h1 class="text-right mb-3">����� ��� 3����</h1>
			<h3 class="price text-right"><b>1,500</b> ��</h3>
		</div>
		<div class="mx-2 my-2 priceInfo" style="height: 110px;">
			<form class="form-inline">
				<p class="mb-3">
					<span class=" border-right pr-4 mr-4">������</span>
					<input class="form-control text-center w-25" type="number" value="1" min="1">
				</p>
			</form>
			<p class="mb-2"><span class="border-right pr-4 mr-4">��ۺ�</span> 3,000��</p>
			<small class="text-muted">30,000�� �̻� ���Ž� ���� / ���� �� ���� ���� �߰� 5,000��</small>
		</div>
		
		<div class="totalPriceInfo bg-light">
			<p class="text-right">�� �ݾ� <b class="pl-2">4,500</b>��</p>
			<div class="row justify-content-around">
				<a type="button" class="btn btn-green rounded-pill" data-toggle="modal" data-target="#cartModal">
					<small><i class="fas fa-shopping-cart pr-1"></i></small> ��ٱ���</a>
				<a type="button" class="btn btn-orange rounded-pill" href="<c:url value='/order' />">
					<small> <i class="far fa-credit-card pr-1"></i></small> �����ϱ�</a>
			</div>
		</div>
	</div>
</div>