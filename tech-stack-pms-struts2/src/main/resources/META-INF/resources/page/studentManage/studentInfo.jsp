<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../config/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name ="studentForm" onsubmit="return navTabSearch(this);" action="student_studentInfo.action" method="post">
		<%@include file="../config/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<label>学号：</label>
						<input type="text" name="studentNo" value="${studentNo }" size="25" alt="精确搜索"/>
					</td>
					<td>
						<label>姓名：</label>
						<input type="text" name="name" value="${name }" size="25" alt="模糊搜索"/>
					</td>
					<td>
						<label>专业名称：</label>
						<input type="text" name="majorName" value="${majorName }" size="25" alt="模糊搜索"/>
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
				<th>学号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>年龄</th>
				<th>出生日期</th>
				<th>入学年份</th>
				<th>专业名称</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
				    <td>${studentNo}</td>
				    <td>${name}</td>
				    <td>${sex }</td>
					<td>${age }</td>
					<td><fmt:formatDate value="${birthday}" type="date" pattern="yyyy-MM-dd"/></td>
					<td>${inSchoolYear }</td>
					<td>${majorName }</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
  <!-- 分页条 -->
    <%@include file="../config/pageBar.jsp"%>
</div>
<script type="text/javascript">
	
</script>