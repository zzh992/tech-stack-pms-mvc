<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../config/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name ="majorForm" onsubmit="return navTabSearch(this);" action="major_majorList.action" method="post">
		<%@include file="../../config/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
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
	<div class="panelBar">
		<ul class="toolBar">
			<li><a href="major_majorAdd.action" class="add" target="dialog" rel="input" width="541" height="360"  title="添加专业信息"><span>添加</span></a></li>
		</ul>
	</div>
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="123">
		<thead>
			<tr>
				<th>序号</th>
				<th>专业名称</th>
				<th>所需学分</th>
				<th>描述</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
				    <td>${majorName}</td>
				    <td>${needScore}</td>
				    <td>${descride }</td>
					<td>
						<a  href="major_majorEdit.action?id=${id }" title="修改专业信息" target="dialog" rel="input" width="541" height="360" style="color:blue">修改</a>
						&nbsp;|&nbsp;
						<a  href="javascript:deleteMajor('${id}')" title="删除专业信息" style="color:blue">删除</a>
						&nbsp;|&nbsp;
						<a  href="major_majorAddCourse.action?majorId=${id}" title="添加专业课" target="dialog" rel="input" width="541" height="360" style="color:blue">添加专业课</a>
						&nbsp;|&nbsp;
						<a  href="major_majorInfo.action?id=${id}" title="查看专业信息" target="dialog" rel="input" width="900" height="400" style="color:blue">查看</a>
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
	function deleteMajor(id){
		alertMsg.confirm("确定要删除该专业信息?", {
			okCall: function(){
				$.post("major_majorDel.action",{"id":id},function(res){
					 if(res.STATE=="SUCCESS"){
						 alertMsg.correct(res.MSG);
						 $("form[name='majorForm']").submit();
					 }else if(res.STATE == "FAIL"){
					 	alertMsg.error(res.MSG);
					 }
				 },"json");
			}
		});
	}
	
</script>