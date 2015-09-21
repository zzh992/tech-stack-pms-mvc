<%-- 权限模块:权限（功能点）管理:添加或修改页面 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../config/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="pmsPermission_pmsActionUpdate.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
		    <input type="hidden" name="navTabId" value="listPmsAction">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			
			<input type="hidden" name="actionId" value="${pmsAction.id }">
			
			<p>
				<label>权限名称：</label>
				<input type="text" name="actionName" value="${pmsAction.actionName }" size="30" />
			</p>
			<p>
				<label>权限标识：</label>
				<input type="text" value="${pmsAction.action }" readonly="true" size="30" />
			</p>
			<p style="height:50px;">
				<label>权限描述：</label>
				<textarea rows="3" cols="27" name="desc" class="required" minlength="3" maxlength="60">${pmsAction.remark}</textarea>
			</p>
			<p></p>
			<p>
				<label>关联菜单：</label>
				<input type="text" value="${pmsAction.menuName}"  readonly="true" >
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
