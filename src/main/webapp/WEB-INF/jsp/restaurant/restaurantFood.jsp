<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
$(document).ready(function() {

        if("${restaurant.open}" == ""){
            console.log("${restaurant.open}");
            $("button[id^='btn']").attr('onClick', null)
            .click(function() {$("#infoModal").modal("show");
            });

        }
});

</script>
<div class="row my-5 mx-5">
					<div class="col-md-12">
				      <div class="shadow-sm rounded-lg">
				        <!-- Tabs-->
				        <ul class="nav nav-tabs nav-fill" role="tablist">
				          <li class="nav-item"><a class="nav-link py-4 px-sm-4 active" href="<c:url value='/eating/'/> ${restaurant.resId}">메뉴</a></li>
				          <li class="nav-item"><a class="nav-link py-4 px-sm-4" href="<c:url value='/eating/${restaurant.resId}/RestaurantReview' />">한줄평</a></li> <!-- mav.setViewName("/eating/RestaurantReview");로 연결 -->
				          <li class="nav-item"><a class="nav-link py-4 px-sm-4" href="<c:url value='/eating/${resId}/togetherList' />">같밥 모집ing</a></li>
				        </ul>
				        <!-- General info tab-->
				        <div class="tab-content px-lg-3 py-5">
				        <div class="info-content px-4 pt-lg-3 pb-3 mb-5 border">
				        <c:if test="${restaurant.ownerId eq userId}">
				        	<button type = "button" class="btn btn-sm btn-warning" onClick="location.href='<c:url value='/eating/${restaurant.resId}/adminFood' />'">메뉴 추가</button>
				        </c:if>
				        <c:forEach var="food" items="${foodList}">     
							<div class="shadow-sm m-2">
				            <div class="row no-gutters">
				              <div class="col-4">
				                <img src="data:image/jpeg;base64,${food.img64}" style="width:230px; height:150px; object-fit: cover;" />
				              </div>
				                <div class="card-body">
					                <div class="d-flex justify-content-between align-items-start py-2" >
									<h6 class="card-text text-left">${food.name}</h6>
									</div>
									<p>${food.description}</p>
									<p>${food.price}원 </p>  
									<c:if test="${restaurant.ownerId eq userId}">
					              	<button id="btnEdit" type="button" class="btn btn-sm btn-warning" onClick="location.href='<c:url value='/eating/${restaurant.resId}/adminFood/update'><c:param name="foodId" value="${food.foodId}"/></c:url>'">정보수정</button>
					              	<button id="btnDel" type="button" class="btn btn-sm btn-outline-warning" onClick="location.href='<c:url value='/eating/${restaurant.resId}/adminFood/delete'><c:param name="foodId" value="${food.foodId}"/></c:url>'">판매중지</button>
				             	  	</c:if>
				              </div>
				              <div class="card-footer" style="width:120px; text-align: center;">
				              	<div class="mt-5">
				              		
					              <button type = "button" class="btn btn-md btn-success" id="btn${food.foodId}"
					              onClick="location.href='<c:url value='/eating/${restaurant.resId}/addFoodToCart'>
					              <c:param name="foodId" value="${food.foodId}"/></c:url>'">담기</button></br> 	       		  
				             	  </div>
				              </div>
				              
				            </div>
				          </div>
						<!-- <li><a href="<c:url value='/eating/' />${res.resId}">${res.resName}</a></li> -->
					</c:forEach>
				          
						</div>
					</div>
				          
				            
				       </div>
				   </div>
				</div>

<!-- modal -->
<div class="modal fade" id="infoModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content py-5">
			<div class="modal-body text-center pb-4">
				<h6>영업중인 식당이 아닙니다.</h6>
			</div>
			<div class="row mx-5 mb-2 justify-content-center">
			<a type="button" class="btn btn-green rounded-pill mx-2 py-2 px-3" data-dismiss="modal">돌아가기</a>

			</div>
		</div>
	</div>
</div>