<%-- 权限模块:用户管理:添加或修改页面 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../config/taglib.jsp"%>
<style>
<!--
.pageFormContent fieldset label{
	width: 200px;
}
-->
</style>
<div class="pageContent">
	<form>
		<div class="pageFormContent" layoutH="60">
			<p style="width:99%">
				<label>用户登录名：</label>
				<!-- <s:textfield name="loginName" readonly="true" size="30" /> -->
				<input type="text" name="loginName" readonly="true" size="30" value="${loginName }">
			</p>
			<p style="width:99%">
				<label>创建时间：</label>
				<fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</p>
			<p style="width:99%">
				<label>类型：</label>
				<c:choose>
					<c:when test="${type eq UserTypeEnum.USER.value }">普通用户</c:when>
					<c:when test="${type eq UserTypeEnum.ADMIN.value }">超级管理员</c:when>
					<c:otherwise>--</c:otherwise>
				</c:choose>
			</p>
			<p style="width:99%;height:50px;">
				<label>描述：</label>
				<!-- <s:textarea name="remark" rows="3" cols="50" readonly="true"></s:textarea> -->
				<textarea rows="3" cols="50" name="remark" readonly="true">${remark}</textarea>
			</p>
			<p></p>
			
			<p></p>
			<fieldset style="width:99%">
				<legend>关联的角色</legend>
				<!-- <s:iterator value="rolesList" status="st" var="v"> -->
				<c:forEach items="${rolesList}" var="v" varStatus="status">
					<c:choose>
						<c:when test="${v.roleType eq RoleTypeEnum.ADMIN.value && type eq UserTypeEnum.ADMIN.value}">
							<label>
								<input type="checkbox" <c:if test="${type eq UserTypeEnum.ADMIN.value}">disabled="disabled"</c:if> <c:if test="${fn:contains(owenedRoleIds, v.id)}">checked="checked"</c:if>  class="required" name="selectRole" id="${v.id }">${v.roleName }
							</label>
						</c:when>
						<c:when test="${v.roleType eq RoleTypeEnum.USER.value}">
							<label>
								<input type="checkbox" disabled="disabled" class="required" name="selectRole" <c:if test="${fn:contains(owenedRoleIds, v.id)}">checked="checked"</c:if>  id="${v.id }">${v.roleName }
							</label>
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
				</c:forEach>
				<!-- </s:iterator> -->
			</fieldset>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
	//回显
	/* $(document).ready(function() {
		var str = "${owenedRoleIds}";
		var array = new Array();
		array = str.split(",");
		for ( var i = 0; i < array.length; i++) {
			$("#" + array[i]).attr("checked", "checked");
		}
	}); */
</script>