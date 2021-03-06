<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<!-- Optional JavaScript -->
<!-- JS, Popper.js, and jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<!-- for Icon -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<script src="https://kit.fontawesome.com/cfb4151af4.js"></script>
<!-- 구글폰트링크 -->
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@300&family=Roboto&display=swap" rel="stylesheet">
<title>Eating</title>
<style type="text/css">
.totalPriceInfo {width:100%; border-radius: 20px; padding:10px 20px 10px 20px;}
.praceInfo>form { width:50px;}
.totalPriceInfo>div>.btn { width:160px; height:45px;  }
.shipInfo{ display:none;}
</style>
</head>
<body>
   <tiles:insertAttribute name="header" />
	
	<!-- Page Content -->
	<div class="container mt-4">
		<div class="row" style="justify-content: center;">
			<!-- 좌측 카테고리 -->
   			<tiles:insertAttribute name="menu" />
			
			<!-- 식당 정보 -->
			<div class="col-md-10 my-1">
				<tiles:insertAttribute name="body" />
				<tiles:insertAttribute name="body-tab" />
			</div>
		</div>
	</div>
	<!-- /.container -->

    <tiles:insertAttribute name="footer" />
</body>
</html>