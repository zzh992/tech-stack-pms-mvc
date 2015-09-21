<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../config/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name ="courseSelectInfoForm" onsubmit="return navTabSearch(this);" action="student_courseSelectInfo.action" method="post">
		<%@include file="../config/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<label>学号：</label>
						<input type="text" value="${student.studentNo }" readonly="true"/>
					</td>
					
					<td>
						<label>姓名：</label>
						<input type="text" value="${student.name }" readonly="true"/>
					</td>
					
					<td>
						<label>专业：</label>
						<input type="text" value="${student.majorName }" readonly="true"/>
					</td>
					
					<td>
						<label>专业所需学分：</label>
						<input type="text" value="${major.needScore }" readonly="true"/>
					</td>
					<c:if test="${student.isEndSelect != publicStatusEnum.YES.value }">
						<td id="endSelectTd">
							<div class="subBar">
								<ul>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="endSelect">结束选课</button></div></div></li>
								</ul>
							</div>
						</td>
					</c:if>
				</tr> 
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="95">
		<thead>
			<tr>
				<th>序号</th>
				<th>课程名称</th>
				<th>学分</th>
				<th>课程类型</th>
				<th>描述</th>
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
				</tr>
			</s:iterator>
		</tbody>
	</table>
  <!-- 分页条 -->
    <%@include file="../config/pageBar.jsp"%>
</div>
<script type="text/javascript">
	$(function(){
		$("#endSelect").click(function(){
			$.post("student_myCourseEnd.action",{},function(res){
				 if(res.STATE=="SUCCESS"){
					 alertMsg.correct(res.MSG);
					 $("#endSelectTd").hide();
				 }else if(res.STATE == "FAIL"){
				 	alertMsg.error(res.MSG);
				 }
			 },"json");
		});
	});
</script>