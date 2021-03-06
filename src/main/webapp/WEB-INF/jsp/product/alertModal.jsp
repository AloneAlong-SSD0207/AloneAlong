<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal fade" id="stockModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content pb-4">
			<div class="modal-header">
				<h5 class="modal-title">Error</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body text-center">현재 <span class="text-green" id="pName"></span>의 재고수량은
				<span class="text-orange" id="pStock"></span>개 입니다.</div>
		</div>
	</div>
</div>
<div class="modal fade" id="recommendReviewModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content pb-4">
			<div class="modal-header">
				<h5 class="modal-title">Error</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body text-center">자신의 리뷰는 추천할 수 없습니다.</div>
		</div>
	</div>
</div>