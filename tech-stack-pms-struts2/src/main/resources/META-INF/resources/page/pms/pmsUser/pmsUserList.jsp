<%-- 权限模块:操作员管理:分页查看页面 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../config/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="pmsUser_pmsUserList.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="../../config/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					登录名：<input type="text" name="loginName" value="${loginName}" size="30" alt="精确查询"  />
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
			<!-- <p:permission value="pms:user:add"> -->
			<shiro:hasPermission name="pms:user:add"> 
				<li><a class="add" href="pmsUser_pmsUserAdd.action" target="dialog" rel="input" width="600" height="460" title="添加用户"><span>添加用户</span></a></li>
			</shiro:hasPermission>
			<!-- </p:permission> -->
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="120">
		<thead>
			<tr>
				<th>序号</th>
				<th>登录名</th>
				<th>类型</th>
				<th>操作</th><!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody>
		   <!--  <s:iterator value="recordList" status="st"> -->
		   <c:forEach items="${pageImpl.content}" var="pmsUser" varStatus="status">
		    	<%-- 普通操作员看不到超级管理员信息 --%>
		    	<c:if test="${(pmsUser.type eq UserTypeEnum.ADMIN.value && pmsUser.type eq UserTypeEnum.ADMIN.value) || (pmsUser.type eq UserTypeEnum.USER.value)}"> 
				<tr target="sid_user" rel="${pmsUser.id}">
				    <td>${status.count}</td>
					<td>${pmsUser.loginName }</td>
					<td>
						<c:forEach items="${UserTypeEnumList}" var="userType">
							<c:if test="${pmsUser.type ne null and pmsUser.type eq userType.value}">${userType.desc}</c:if>
						</c:forEach>
					</td>
					<td>
						<!-- <p:permission value="pms:user:view"> -->
						<shiro:hasPermission name="pms:user:view"> 
							[<a href="pmsUser_pmsUserView.action?id=${pmsUser.id}" title="查看【${pmsUser.loginName }】详情" target="dialog" width="600" height="400" style="color:blue">查看</a>]
						</shiro:hasPermission>
						<!-- </p:permission> -->
						<%-- <c:if test="${type eq UserTypeEnum.USER.value }"> --%>
							<!-- <p:permission value="pms:user:edit"> -->
							<shiro:hasPermission name="pms:user:edit"> 
								&nbsp;[<a href="pmsUser_pmsUserEdit.action?id=${pmsUser.id}" title="修改【${pmsUser.loginName }】" target="dialog" width="600" height="400" rel="userUpdate" style="color:blue">修改</a>]
							</shiro:hasPermission>
							<!-- </p:permission>
							<p:permission value="pms:user:edit"> -->
							<shiro:hasPermission name="pms:user:edit"> 
								&nbsp;[<a href="pmsUser_pmsUserResetPwd.action?id=${pmsUser.id}" title="重置【${pmsUser.loginName }】的密码" target="dialog" width="550" height="300" style="color:blue">重置密码</a>]
							</shiro:hasPermission>
							<!-- </p:permission>
							<p:permission value="pms:user:delete"> -->
							<shiro:hasPermission name="pms:user:delete"> 
								<c:if test="${pmsUser.type eq UserTypeEnum.USER.value }">
								&nbsp;[<a href="pmsUser_pmsUserDel.action?id=${pmsUser.id}" target="ajaxTodo" title="确定要删除吗？" style="color:blue">删除</a>]
								</c:if>
							</shiro:hasPermission>
							<!-- </p:permission> -->
						<%-- </c:if> --%>
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