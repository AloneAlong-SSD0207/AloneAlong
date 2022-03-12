<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
function updateReviewModal(reviewId, resId, orderId) {
    var rvId="<input type='hidden' id='reviewId' name='reviewId' value='" + reviewId +"'>";
    $("#updateReview").append(rvId);
    $("#updateModal").modal("show");
    $.ajax({
                  type:"get",
                  url:"/foodreview/" + reviewId,
                  async:false,
                  success: function(foodreview){
                    var rating = "" + foodreview.rating;
                    $("#rating").val(rating).prop("selected", true);
                    document.getElementById("contents").value = foodreview.contents;
                  }
    });

}
function updateReview(){
$.ajax({
                  type:"put",
                  url:"/foodreview?" + $("#updateReview").serialize(),
                  async:false,
                  success: function(){
                      location.reload();
                  }
                });
}
function deleteReview(reviewId){
    $.ajax({
        type:"delete",
        url:"/foodreview/" + reviewId,
        async:false,
        success:function(){
               location.reload();
        }
    });
}
$(document).ready(function() {

    $(".edit_div${userId}").css("display", "inline");
    $(".edit_div${userId}").css("padding-left", "20px");
});

</script>

	<div class="row my-5 mx-5">
		<div class="col-md-12" style="width:100%;">
	      <div class="shadow-sm rounded-lg">
	        <!-- Tabs-->
	        <ul class="nav nav-tabs nav-fill" role="tablist">
	          <li class="nav-item"><a class="nav-link py-4 px-sm-4" href="<c:url value='/eating/'/>${resId}">메뉴</a></li>
	          <li class="nav-item"><a class="nav-link py-4 px-sm-4 active" href="<c:url value='/eating/${resId}/RestaurantReview' />">한줄평</a></li> <!-- mav.setViewName("/eating/RestaurantReview");로 연결 -->
	          <li class="nav-item"><a class="nav-link py-4 px-sm-4" href="<c:url value='/eating/${resId}/togetherList' />">같밥 모집ing</a></li>
	        </ul>
	        <!-- 내용 -->
	        <div class="review-content pb-3 mb-5 border">
	          <!-- 상단 리뷰 통계 -->
	          <div class="bg-light py-5">
	          	<div class="row align-items-center justify-content-between">
	          		<div class="col-md-1"></div>
	          		<div class="col-md-3 text-center">
	          			<h4><i class="green fas fa-comment-dots"></i></h4>
	          			<h4 class="green pb-1">리뷰 수</h4>
	          			<h4><b class="orange">${foodReviewCount}</b><small> 개</small></h4>
	          		</div>
	          		<div class="col-md-4 text-center align-items-center">
	          			<h4><i class="far green fa-star"></i></h4>
	          			<h4 class="green pb-1">평균 평점</h4>

	          			<h4><b class="orange"><fmt:formatNumber value="${restaurant.avgRating}" pattern="#.#"/></b><small> 점</small></h4>
	          		</div>
	          		
	          		<div class="col-md-1"></div>
	          	</div>
	          </div>

			  <div class="px-5 pt-lg-5">
			   <!-- 상단 -->
				<div class="d-flex justify-content-between pb-4 px-4 border-bottom mb-4">
				
				<!-- 드롭다운 -->
				<form action="#">
					<div class="dropdown">
						<button class="btn btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true">${sortTypeName}</button>
						<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
							<button class="dropdown-item" name="sortType" value="REVIEW_DATE DESC" type="submit"><small>최신 등록순</small></button>
							<button class="dropdown-item" name="sortType" value="REVIEW_RATING DESC" type="submit"><small>높은 평점순</small></button>
							<button class="dropdown-item" name="sortType" value="REVIEW_RATING" type="submit"><small>낮은 평점순</small></button>
						</div>
				
					</div>
				</form>
				
			</div>
			
			<!-- 리뷰 목록 -->
			<c:if test="${empty foodReviewList}"> <div class="text-center my-5 py-5">등록된 리뷰가 없습니다.</div></c:if>
			<c:forEach var = "rev" items = "${foodReviewList}">
                <script>
                     $(".edit_div").css("display", "none");
                </script>
				<div class="product-review mx-4 pb-4 mb-4 border-bottom">
					<div class="d-flex align-middle me-4 pe-2">
						<h6 class="green-roboto px-1"> </h6>
						<div class="star-rating green">
							<c:forEach begin="1" end="${rev.rating}">
								<i class="fas fa-star"></i>
							</c:forEach>
							<c:forEach begin="${rev.rating + 1}" end="5">
								<i class="far fa-star"></i>
							</c:forEach>
							<div class = "edit_div${rev.userId}" style="display:none;">
                                <i id="updateBtn" onClick="updateReviewModal(${rev.reviewId}, ${rev.resId}, '${rev.orderId}');" class="bi bi-pencil"></i>
                                <i id="deleteBtn" onClick="deleteReview(${rev.reviewId});" class="bi bi-trash"></i>
                             </div>
						</div>
					</div>
					<div class="text-muted ml-2">
					    <fmt:formatDate var="fmtReviewDate" value="${rev.reviewDate}" pattern="yyyy-MM-dd hh/mm"/>
						<small>${rev.userNickName} | ${fmtReviewDate}</small>
					</div>
					<div class="mx-4 my-2">
						<div class="my-3">
							${rev.contents}
						</div>


					</div>
				</div>

			</c:forEach>
				<c:set var="pageListSize" value="${foodReviewList.size()}" />
				<c:set var="sortType" value="REVIEW_RATING" />
			<!-- 페이지네이션 -->
			<div class="row my-xl-5 justify-content-center">
	<div class="paginate mb-xl-5 btn-toolbar" role="toolbar">
		 <c:if test="${page == 1}">
		 	 <button type="button" class="btn" disabled><i class="fas fa-chevron-left"></i></button>
		 </c:if>
		 <c:if test="${page != 1}">
		 	 <button type="button" class="btn" onClick="location.href='<c:url value='/eating/${resId}/RestaurantReview?page=${page - 1}&sortType=${param.sortType}' />'">
		 	 	<i class="fas fa-chevron-left"></i></button>
		 </c:if>
		 <c:forEach var="pageNum" begin="${startPage}" end="${startPage + rangeSize - 1}" varStatus="status">
			 <c:if test="${pageNum == page}">
		 		<div class="btn-group"><button type="button" class="btn active rounded-circle" 
		 			onClick="location.href='<c:url value='/eating/${resId}/RestaurantReview?page=${pageNum}&sortType=${param.sortType}' />'">${pageNum}</button></div>
			 </c:if>
			 <c:if test="${pageNum != page && pageNum <= lastPage}">
		 		<div class="btn-group"><button type="button" class="btn rounded-circle"
		 			onClick="location.href='<c:url value='/eating/${resId}/RestaurantReview?page=${pageNum}&sortType=${param.sortType}' />'">${pageNum}</button></div>
			 </c:if>
		 </c:forEach>
		 <c:if test="${page == lastPage}">
		 	 <button type="button" class="btn" disabled><i class="fas fa-chevron-right"></i></button>
		 </c:if>
		 <c:if test="${page != lastPage}">
		 	 <button type="button" class="btn" onClick="location.href='<c:url value='/eating/${resId}/RestaurantReview?page=${page + 1}&sortType=${param.sortType}' />'">
		 	 	<i class="fas fa-chevron-right"></i></button>
		 </c:if>
	</div>
</div>
		</div>
		</div>
	</div>
	</div>
	</div>

	<!-- /.container -->

	<!-- Modal -->

	<!-- 리뷰 작성 -->
    <div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-hidden="true">
    	<form id="updateReview" class="form" >
    		<div class="modal-dialog modal-dialog-centered" role="document">
    			<div class="modal-content pb-4">
    				<div class="modal-header">
    					<h5 class="modal-title">리뷰 수정</h5>
    					<button type="button" class="close" data-dismiss="modal"
    						aria-label="Close">
    						<span aria-hidden="true">&times;</span>
    					</button>
    				</div>
    				<div class="modal-body">

    					<div class="row">
    						<div class="col-sm-6">
    							<div class="form-group">
    								<select name="rating" id="rating"
    									class="custom-select focus-shadow-0">
    									<option value="5">★★★★★</option>
    									<option value="4">★★★★☆</option>
    									<option value="3">★★★☆☆</option>
    									<option value="2">★★☆☆☆</option>
    									<option value="1">★☆☆☆☆</option>
    								</select>
    							</div>
    						</div>
    					</div>
    					<div class="form-group">
    						<textarea rows="4" name="contents" id="contents" maxlength=80
    							placeholder="신중히 리뷰를 작성해주세요. 리뷰 작성 후에는 예약취소가 불가능합니다.(80자)" required="" class="form-control"></textarea>
    					</div>
    			</div>
    			<div class="text-center">
    			    <input id="updateBtn" type="button" onClick="updateReview();"
                     class="btn btn-orange rounded-pill w-25 pb-2" value="수정하기" >

    				<!--<button type="submit" class="btn btn-orange rounded-pill w-25 pb-2" >작성하기</button>-->
    			</div>
    		</div>
    	</div>
    	</form>
    </div>