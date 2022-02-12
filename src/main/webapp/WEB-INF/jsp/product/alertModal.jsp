<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script languae="javaScript">
$(document).ready(function() {
	// $("#stockModal").modal("show");
	$("#recommendReviewModal").modal("show");
});
</script>
<%--<c:if test="${stockError == true}">--%>
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
			<div class="modal-body text-center">���� <span class="text-green" id="pName"></span>�� ��������
				<span class="text-orange" id="pStock"></span>�� �Դϴ�.</div>
		</div>
	</div>
</div>
<%--</c:if>--%>
<c:if test="${param.recommendError == true}">
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
			<div class="modal-body text-center">�ڽ��� ����� ��õ�� �� �����ϴ�.</div>
		</div>
	</div>
</div>
</c:if>