<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../config/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name ="courseForm" onsubmit="return navTabSearch(this);" action="course_courseList.action" method="post">
		<%@include file="../../config/pageForm.jsp"%>
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
	<div class="panelBar">
		<ul class="toolBar">
			<li><a href="course_courseAdd.action" class="add" target="dialog" rel="input" width="541" height="360"  title="添加课程信息"><span>添加</span></a></li>
		</ul>
	</div>
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="120">
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
						<a  href="course_courseEdit.action?id=${id }" title="修改课程信息" target="dialog" rel="input" width="541" height="360" style="color:blue">修改</a>
						&nbsp;|&nbsp;
						<a  href="javascript:deleteCourse('${id}')" title="删除课程信息" style="color:blue">删除</a>
						&nbsp;|&nbsp;
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
  <!-- 分页条 -->
    <%@include file="../../config/pageBar.jsp"%>
</div>
<script type="text/javascript">
	function deleteCourse(id){
		alertMsg.confirm("确定要删除该课程信息?", {
			okCall: function(){
				$.post("course_courseDel.action",{"id":id},function(res){
					 if(res.STATE=="SUCCESS"){
						 alertMsg.correct(res.MSG);
						 $("form[name='courseForm']").submit();
					 }else if(res.STATE == "FAIL"){
					 	alertMsg.error(res.MSG);
					 }
				 },"json");
			}
		});
	}
	
</script>