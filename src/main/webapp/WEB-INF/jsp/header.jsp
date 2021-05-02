<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<style type="text/css">
.title, .title:hover, .title:focus {
	color: black;
	text-decoration: none;
}

.topnav>a {
	color: #000000;
	text-decoration: none;
}

.topnav>a:hover, .topnav>a:focus {
	font-weight: bold;
	color: #29A65F;
	text-decoration: none;
}

.searchform>a {
	margin: 0px 0px 0px -50px;
}

.searchform>a:hover, .searchform>a:focus {
	color: #29A65F;
}
</style>
</head>
	<header class="blog-header py-4">
		<div class="row pb-3 text-right">
			<div class="col-md-8"></div>
			<div class="col-md-2">
				<a class="text-muted px-3" href="#"><i class="far fa-user"
					style="color: #29A65F;"></i> <b style="color: #29A65F;">공지수</b> 님</a> <a
					class="text-muted" href="#"><i class="fas fa-sign-out-alt"
					style="color: #29A65F;"></i> 로그아웃</a>
			</div>
			<div class="col-md-2"></div>
		</div>
		<a class="title" href="<c:url value='/' />"><h1 class="display-4 text-center">Alone&Along</h1></a>
	
		<div class="row text-right py-2 pt-1 border-bottom shadow-sm">
			<div class="col-md-2"></div>
			<div class="row no-gutters topnav py-2 col-md-4">
				<a class="pt-2 px-4" href="<c:url value='/shopping' />"><h6>쇼핑</h6></a>
				<a class="pt-2 px-4" href="<c:url value='/eating' />"><h6>식당</h6></a>
				<a class="pt-2 px-4" href="#"><h6>함께 먹기</h6></a>
			</div>
			<div class="row no-gutters topnav py-2 col-md-4 text-right justify-content-end">
				<form class="searchform form-inline">
					<input class="px-3 pr-5 form-control rounded-pill" style="font-size: 13px;" type="text"
						placeholder="검색" aria-label="Search" size=12 >
					<a class="btn text-right" type="submit"><i class="fas fa-search"></i></a>
				</form>
				<a class="pt-2 pl-3" href="<c:url value='/shopping/cart' />"><h6><i class="fas fa-shopping-cart"></i></h6></a>
			</div>
			<div class="col-md-2"></div>
		</div>
		</header>