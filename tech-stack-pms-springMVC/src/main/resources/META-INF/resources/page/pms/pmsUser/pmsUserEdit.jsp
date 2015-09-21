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
	<form id="form" method="post" action="pmsUser_pmsUserUpdate.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
		    <input type="hidden" name="navTabId" value="listPmsOperator">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			
			<input type="hidden" name="id" value="${id}">
			<input type="hidden" id="selectVal" name="selectVal">
			<!-- <s:hidden id="operatorId" name="id" /> -->
			<!-- <s:hidden id="selectVal" name="selectVal"></s:hidden> -->
			<p style="width:99%">
				<label>用户登录名：</label>
				<!-- <s:textfield name="loginName" cssClass="required" readonly="true" minlength="3" maxlength="30" size="30" /> -->
				<input type="text" name="loginName" class="required" readonly="true" minlength="3" maxlength="30" size="30" value="${loginName }">
			</p>
			<p style="width:99%">
				<label>用户类型：</label>
				<c:choose>
					<c:when test="${type eq UserTypeEnum.USER.value }">普通用户</c:when>
					<c:when test="${type eq UserTypeEnum.ADMIN.value }">超级管理员</c:when>
					<c:otherwise>--</c:otherwise>
				</c:choose>
			</p>
			<p style="width:99%;height:50px;">
				<label>描述：</label>
				<!-- <s:textarea name="remark" maxlength="100" rows="3" cols="30"></s:textarea> -->
				<textarea rows="3" cols="30" name="remark" class="required" minlength="3" maxlength="300">${remark}</textarea>
			</p>
			<p></p>
			<fieldset style="width:99%">
				<legend>选择角色<font color="red">*</font></legend>
				<!-- <s:iterator value="rolesList" status="st" var="v"> -->
				<c:forEach items="${rolesList}" var="v" varStatus="status">
					<c:choose>
						<c:when test="${v.roleType eq RoleTypeEnum.ADMIN.value && type eq UserTypeEnum.ADMIN.value}">
							<label>
								<input type="checkbox" <c:if test="${type eq UserTypeEnum.ADMIN.value}">disabled="disabled"</c:if> <c:if test="${fn:contains(owenedRoleIds, v.id)}">checked="checked"</c:if>
								class="selectOperatorRole" name="selectRole" id="roleId${v.id }" value="${v.id }">${v.roleName }
							</label>
						</c:when>
						<c:when test="${v.roleType eq RoleTypeEnum.USER.value}">
							<label>
								<input type="checkbox" <c:if test="${type eq UserTypeEnum.ADMIN.value}">disabled="disabled"</c:if> <c:if test="${fn:contains(owenedRoleIds, v.id)}">checked="checked"</c:if>
								class="selectOperatorRole" name="selectRole" id="roleId${v.id }" value="${v.id }">${v.roleName }
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
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitForm()">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
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
			$("#roleId" + array[i]).attr("checked", "checked");
		}
	}); */

	function submitForm() {
		var str = "";
		$(":checkbox:checked").each(function() {
			if ($(this).hasClass('selectOperatorRole')){
				// 加样式判断，避免与其他复选框冲突
				str += $(this).val() + ",";
			}
		});
		if(str == null || str == ""){
			alertMsg.error("用户关联的角色不能为空");
			return;
		}
		$("#selectVal").val(str);
		$("#form").submit();
	}
	
</script>