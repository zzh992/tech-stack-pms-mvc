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
	<form id="form" method="post" action="pmsUser_pmsUserSave.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
		    <input type="hidden" name="navTabId" value="listPmsOperator">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			
			<input type="hidden" name="loginNamess" id="loginNamess" />
			<input type="hidden" name="loginPwdss" id="loginPwdss" />
			<input type="hidden" name="selectVal" id="selectVal" />
			<!-- <s:hidden id="selectVal" name="selectVal"></s:hidden> -->
			
			<p style="width:99%">
				<label></label>
				<span style="color:red;">提示：用户添加后将不可删除，登录名不可修改，请确保添加信息的准确性！</span>
			</p>
			<p style="width:99%">
				<label>用户登录名：</label>
				<input type="text" accept="loginName" class="required" maxlength="30" size="30" />
			</p>
			<!-- <s:if test="id==null"> -->
			<c:if test="${id eq null }">
			<p style="width:99%">
				<label>密码：</label>
				<input type="password" accept="loginPwd" class="required" maxlength="20" size="30" />
				<span class="info"></span>
			</p>
			</c:if>
			<!-- </s:if> -->
			<p style="width:99%">
				<label>用户类型：</label>
				普通用户
			</p>
			<p style="width:99%;height:50px;">
				<label>描述：</label>
				<!-- <s:textarea name="desc" cssClass="required" maxlength="100" rows="3" cols="30"></s:textarea> -->
				<textarea rows="3" cols="30" name="desc" class="required" minlength="3" maxlength="300"></textarea>
			</p>
			<p></p>
			<fieldset style="width:99%">
				<legend>选择角色<font color="red">*</font></legend>
				<!-- <s:iterator value="rolesList" status="st"> -->
				<c:forEach items="${rolesList}" var="pmsRole" varStatus="status">
					<c:if test="${pmsRole.roleType eq RoleTypeEnum.USER.value}">
						<label>
							<input class="selectOperatorRole" type="checkbox" name="selectRole" value="${pmsRole.id }">${pmsRole.roleName }
						</label>
					</c:if>
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
	function submitForm() {
		var str = "";
		$(":checkbox:checked").each(function() {
			if ($(this).hasClass('selectOperatorRole')){
				// 加样式判断，避免与其他复选框冲突
				str += $(this).val() + ",";
			}
		});
		$("#selectVal").val(str);
		
		$("#loginNamess").val($("input[accept='loginName']").val());
		$("#loginPwdss").val($("input[accept='loginPwd']").val());
		$("#form").submit();
	}
</script>