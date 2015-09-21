<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="taglib.jsp"%>
<div class="panelBar">
	<div class="pages">
		<span>查到&nbsp;${pageImpl.totalElements}&nbsp;条记录，每页</span>
		<select class="combox" name="numPerPage" value="${pageImpl.size}" onchange="navTabPageBreak({numPerPage:this.value})">
		    <option value="2" <c:if test="${pageImpl.size eq 2 }">selected='selected'</c:if>>2</option>
		    <option value="5" <c:if test="${pageImpl.size eq 5 }">selected='selected'</c:if>>5</option>
		    <option value="15" <c:if test="${pageImpl.size eq 15 }">selected='selected'</c:if>>15</option>
			<option value="20" <c:if test="${pageImpl.size eq 20 }">selected='selected'</c:if>>20</option>
			<option value="30" <c:if test="${pageImpl.size eq 30 }">selected='selected'</c:if>>30</option>
			<option value="50" <c:if test="${pageImpl.size eq 50 }">selected='selected'</c:if>>50</option>
			<option value="100" <c:if test="${pageImpl.size gt 50 }">selected='selected'</c:if>>100</option>
		</select>
		<span>条，&nbsp;${pageImpl.number}/${pageImpl.totalPages}&nbsp;页</span>
	</div>
	<div class="pagination" 
		targetType="navTab" 
		totalCount="${pageImpl.totalElements}" 
		numPerPage="${pageImpl.size}" 
		pageNumShown="10" 
		currentPage="${pageImpl.number}">
	</div>
</div>