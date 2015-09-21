<%-- 权限模块:角色管理:分页查看页面 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../config/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="pmsRole_pmsRoleList.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="../../config/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					角色名称：<input type="text" name="roleName" value="${roleName}" size="30" alt="模糊查询"  />
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
			<!-- <p:permission value="pms:role:add"> -->
			<shiro:hasPermission name="pms:role:add"> 
				<li><a class="add" href="pmsRole_pmsRoleAdd.action" target="dialog" width="550" height="300" rel="input" title="添加角色"><span>添加角色</span></a></li>
			</shiro:hasPermission>
			<!-- </p:permission> -->
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="118">
		<thead>
			<tr>
				<th>序号</th>
				<th>角色名称</th>
				<th>角色类型</th>
				<th>描述</th>
				<th>创建时间</th>
				<th>操作</th><!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody>
		    <!-- <s:iterator value="recordList" status="st"> -->
		    <c:forEach items="${pageImpl.content}" var="pmsRole" varStatus="status">
		    	<%-- 普通操作员看不到超级管理员角色 --%>
		    	<c:if test="${(pmsRole.roleType eq RoleTypeEnum.ADMIN.value && type eq UserTypeEnum.ADMIN.value) || (pmsRole.roleType eq RoleTypeEnum.USER.value)}">
				<tr target="sid_user" rel="${pmsRole.id}">
				    <td>${status.count}</td>
					<td>${pmsRole.roleName }</td>
					<td>
						<c:forEach items="${RoleTypeEnumList}" var="roleTypeEnum">
							<c:if test="${pmsRole.roleType ne null and pmsRole.roleType eq roleTypeEnum.value}">${roleTypeEnum.desc}</c:if>
						</c:forEach>
					</td>
					<td>${pmsRole.remark}</td>
					<td>
						<!-- <s:date name="pmsRole.createTime" format="yyyy-MM-dd HH:mm:ss" /> -->
						<fmt:formatDate value="${pmsRole.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<!-- <p:permission value="pms:role:edit"> -->
						<shiro:hasPermission name="pms:role:edit"> 
							[<a href="pmsRole_assignPermissionUI.action?roleId=${pmsRole.id}" title="为角色【${pmsRole.roleName}】分配权限" target="dialog" width="950" style="color:blue">分配权限</a>]
						</shiro:hasPermission>
						<!-- </p:permission>
						<p:permission value="pms:role:edit"> -->
						<shiro:hasPermission name="pms:role:edit"> 
							&nbsp;[<a href="pmsRole_pmsRoleEdit.action?roleId=${pmsRole.id}" title="修改角色【${pmsRole.roleName}】" target="dialog" width="550" height="300" rel="input" style="color:blue">修改</a>]
						</shiro:hasPermission>
						<!-- </p:permission>
						<p:permission value="pms:role:delete"> -->
						<shiro:hasPermission name="pms:role:delete"> 
							<c:if test="${pmsRole.roleType eq RoleTypeEnum.USER.value}">
							&nbsp;[<a href="pmsRole_pmsRoleDel.action?roleId=${pmsRole.id}" title="删除角色【${pmsRole.roleName}】" target="ajaxTodo" style="color:blue">删除</a>]
							</c:if>
						</shiro:hasPermission>
						<!-- </p:permission> -->
					</td>
				</tr>
				</c:if>
			<!-- </s:iterator> -->
			</c:forEach>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="../../config/pageBar.jsp"%>
</div>
    