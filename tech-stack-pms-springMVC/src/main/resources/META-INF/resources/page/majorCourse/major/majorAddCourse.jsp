<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../config/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" action="major_majorAddCourse.action" onsubmit="return dwzSearch(this, 'dialog');" method="post">
		<%@include file="../../config/pageForm.jsp"%>
		<input type="hidden" name="majorId" value="${majorId }"/>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<label>课程名称：</label>
						<input type="text" name="courseName" value="${courseName }" size="25" alt="模糊搜索"/>
					</td>
					
					
					<td>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
							</ul>
						</div>
					</td>
				</tr> 
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>课程名称</th>
				<th>学分</th>
				<th>课程类型</th>
				<th>描述</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
				    <td>${courseName}</td>
				    <td>${score}</td>
				    <td>
				    	<c:forEach items="${courseTypeEnumList }" var="courseTypeEnum">
							<c:if test="${courseTypeEnum.value eq courseType }">${courseTypeEnum.desc}</c:if>
						</c:forEach>
					</td>
				    <td>${descride }</td>
					<td>
						<a  href="javascript:majorAddCourse('${id}','${majorId }')" title="添加" style="color:blue">添加</a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页条 -->
    <%@include file="../../config/pageBarLookup.jsp"%>
</div>
<script type="text/javascript">
	function majorAddCourse(courseId,majorId){
		$.post("major_majorCourseSave.action",{"courseId":courseId,"majorId":majorId},function(res){
			 if(res.STATE=="SUCCESS"){
				 alertMsg.correct(res.MSG);
			 }else if(res.STATE == "FAIL"){
			 	alertMsg.error(res.MSG);
			 }
		 },"json");
	}


</script>