<%-- 权限模块:权限（功能点）管理:分页查看页面 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../config/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="pmsPermission_pmsActionList.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="../../config/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
			    <td>
					权限名称：<input type="text" name="actionName" value="${actionName}" size="30" alt="模糊查询"  />
				</td>
				<td>
					权限标识：<input type="text" name="act" value="${act}" size="30" alt="精确查询"  />
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
		<!-- <p:permission value="pms:action:add"> -->
		<shiro:hasPermission name="pms:action:add">  
			<li><a class="add" href="pmsPermission_pmsActionAdd.action" target="dialog" width="550" height="350" rel="input" title="添加权限"><span>添加权限</span></a></li>
		</shiro:hasPermission>
		<!-- </p:permission> -->
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="120">
		<thead>
			<tr>
				<th>序号</th>
				<th>权限名称</th>
				<th>权限标识</th>
				<th>关联的菜单</th>
				<th>描述</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		   <!--  <s:iterator value="recordList" status="st"> -->
		   <!-- jstl c:forEach http://gundumw100.iteye.com/blog/473382-->
		   <c:forEach items="${pageImpl.content}" var="pmsAction" varStatus="status">
				<tr target="sid_user" rel="${pmsAction.id}">
				    <td>${status.count}</td> 
					<td>${pmsAction.actionName }</td>
					<td>${pmsAction.action }</td>
					<td>${pmsAction.menuName}</td>
					<td>${pmsAction.remark}</td>
					<td>
						<fmt:formatDate value="${pmsAction.createTime }" pattern="yyyy-MM-dd"/>
					</td>
					<td>
					<!-- <p:permission value="pms:action:edit"> -->
					<shiro:hasPermission name="pms:action:edit"> 
						[<a href="pmsPermission_pmsActionEdit.action?id=${pmsAction.id}" title="修改权限" target="dialog" width="550" height="300" rel="input"  style="color:blue">修改</a>]
					</shiro:hasPermission>
					<!-- </p:permission>
					<p:permission value="pms:action:delete"> -->
					<shiro:hasPermission name="pms:action:edit"> 
						&nbsp;[<a href="pmsPermission_pmsActionDel.action?id=${pmsAction.id}" title="删除权限【${pmsAction.action }】" target="ajaxTodo" style="color:blue">删除</a>]
					</shiro:hasPermission>
					<!-- </p:permission> -->
					</td>
				</tr>
			</c:forEach>	
			<!-- </s:iterator> -->
		</tbody>
	</table>
     <!-- 分页条 -->
    <%@include file="../../config/pageBar.jsp"%>
</div>
    