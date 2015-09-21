<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../config/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name ="courseSelectInfoForm" onsubmit="return navTabSearch(this);" action="student_courseSelectInfo.action" method="post">
		<%@include file="../config/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<label>课程名称：</label>
						<input type="text" name="courseName" value="${courseName }" size="25" alt="模糊搜索"/>
					</td>
					
					<td>
						<label>课程类型：</label>
						<select name="courseType" id="courseType">
							<option value="">请选择</option>
							<c:forEach items="${courseTypeEnumList }" var="courseTypeEnum">
								<option value="${courseTypeEnum.value}" <c:if test="${courseType eq courseTypeEnum.value }">selected="selected"</c:if>>${courseTypeEnum.desc}</option>
							</c:forEach>
						</select>
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
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="95">
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
						<a  href="javascript:selectCourse('${id}')" title="选择课程" style="color:blue">选择课程</a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
  <!-- 分页条 -->
    <%@include file="../config/pageBar.jsp"%>
</div>
<script type="text/javascript">
	function selectCourse(id){
		$.post("student_courseSelect.action",{"courseId":id},function(res){
			 if(res.STATE=="SUCCESS"){
				 alertMsg.correct(res.MSG);
			 }else if(res.STATE == "FAIL"){
			 	alertMsg.error(res.MSG);
			 }
		 },"json");
	}
	
</script>