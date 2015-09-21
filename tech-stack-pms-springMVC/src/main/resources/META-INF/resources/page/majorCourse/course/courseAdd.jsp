<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../config/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="course_courseSave.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		
		<input type="hidden" name="navTabId" value="courseList">
		<input type="hidden" name="callbackType" value="closeCurrent">
		<input type="hidden" name="forwardUrl" value="">
		<div class="pageFormContent" layoutH="60" >
			<p>
				<label>课程名称：</label>
				<input type="text" class="required"  size="30" name="courseName" />
			</p>
			
			<p>
				<label>学分：</label>
				<input type="text" class="required number" size="30" name="score" />
			</p>
			
			<p>
				<label>课程类型：</label>
				<c:forEach items="${courseTypeEnumList }" var="courseTypeEnum" varStatus="index">
					<input type="radio" name="courseType" value="${courseTypeEnum.value}" 
					<c:if test="${index.index eq 0  }">checked="checked"</c:if> 
					 />${courseTypeEnum.desc}
				</c:forEach>
			</p>
			
			<p>
				<label>描述：</label>
				<textarea name="descride" id="descride" cols="30" rows="4" ></textarea>
			</p>
			
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" id="addSave" >添加</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>

<script type="text/javascript">


</script>
