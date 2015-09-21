<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../config/taglib.jsp"%>
<div class="pageHeader">
	<table class="table" style="width:100%">
		<tr>
			<td width="10%"><strong>专业名称：</strong></td><td width="15%">${major.majorName }</td>
			<td width="10%"><strong>所需学分：</strong></td><td width="15%">${major.needScore }</td>
		</tr> 
		<tr>
			<td width="10%"><strong>描述：</strong></td><td width="15%">${major.descride }</td>
		</tr> 
	</table>
</div>
<div class="pageContent">
	<form id="pagerForm" action="major_majorInfo.action" onsubmit="return dwzSearch(this, 'dialog');" method="post">
		<%@include file="../../config/pageForm.jsp"%>
		<input type="hidden" name="id"  value="${major.id }" />
	</form>
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="150">
		<thead>
			<tr>
				<th>课程名称</th>
				<th>学分</th>
				<th>课程类型</th>
				<th>描述</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
				    <td>${courseName}</td>
				    <td>${score}</td>
				    <td>
				    	<c:forEach items="${courseTypeEnumList }" var="courseTypeEnum">
							<c:if test="${courseTypeEnum.value eq courseType }">${courseTypeEnum.desc}</c:if>
						</c:forEach>
					</td>
				    <td>${descride }</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页条 -->
    <%@include file="../../config/pageBarLookup.jsp"%>
    
</div>
<script type="text/javascript">
	

</script>