<%-- 权限模块:角色管理:添加或修改页面 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../config/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="pmsRole_pmsRoleUpdate.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
		    <input type="hidden" name="navTabId" value="listPmsRole">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="id" value="${id}">
			<!-- <s:hidden id="roleId" name="id" /> -->
			
			<p>
				<label>角色名称：</label>
				<!-- <s:textfield name="roleName" cssClass="required" minlength="3" maxlength="90" size="30" /> -->
				<input type="text" name="roleName" class="required" minlength="3" maxlength="90" size="30" value="${roleName }">
			</p>
			<p style="height:50px;">
				<label>角色描述：</label>
				<!-- <s:textarea rows="5" cols="27" name="remark" cssClass="required" minlength="3" maxlength="300" ></s:textarea> -->
				<textarea rows="5" cols="32" name="remark" class="required" minlength="3" maxlength="300">${remark}</textarea>
			</p>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>