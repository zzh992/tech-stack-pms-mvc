<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../config/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="student_myStudentInfoAuth.action" class="pageForm required-validate" onsubmit="return navTabSearch(this);" >
		<div class="pageFormContent" layoutH="60" >
			<p>
				<label>学号：</label>
				<input type="text" class="required number"  size="30" name="studentNo" value="${studentNo }" <c:if test="${majorId!=null&&majorId!=''}">readonly="true"</c:if> />
			</p>
			
			<p>
				<label>姓名：</label>
				<input type="text" class="required" size="30" name="name" value="${name }" <c:if test="${majorId!=null&&majorId!=''}">readonly="true"</c:if> />
			</p>
			
			<p>
				<label>性别：</label>
				<c:forEach items="${sexTypeEnumList }" var="sexTypeEnum" varStatus="index">
					<input type="radio" name="sex" value="${sexTypeEnum.value}" 
					<c:if test="${sex eq sexTypeEnum.value}">checked="checked"</c:if> <c:if test="${majorId!=null&&majorId!=''}">readonly="true"</c:if>
					 />${sexTypeEnum.desc}
				</c:forEach>
			</p>
			
			<p>
				<label>年龄：</label>
				<input type="text" class="required" size="30" name="age" value="${age }" <c:if test="${majorId!=null&&majorId!=''}">readonly="true"</c:if> />
			</p>
			
			<p>
				<label>出生日期：</label>
				<input name="birthday" value="<fmt:formatDate value="${birthday}" type="date" pattern="yyyy-MM-dd"/>" dateFmt="yyyy-MM-dd" type="text"  style="width: 77px;" class="date" <c:if test="${majorId!=null&&majorId!=''}">readonly="true"</c:if> /> 
				
			</p>
			
			<p>
				<label>入学年份：</label>
				<%-- <input name="inSchoolYear" value="${inSchoolYear }" dateFmt="yyyy" type="text"  style="width: 77px;" class="date" <c:if test="${majorId!=null&&majorId!=''}">readonly="true"</c:if> />  --%>
				<select name="inSchoolYear"  <c:if test="${inSchoolYear!=null&&inSchoolYear!=''}">disabled="disabled"</c:if> >
					<c:forEach var="i" begin="2008" end="2100" step="1"> 
				    	<option value="${i}" <c:if test="${inSchoolYear eq i }">selected="selected"</c:if>><c:out value="${i}" /></option>
				    </c:forEach><p> 
				</select>
			</p>
			
			<p>
				<label>专业：</label>
				<select name="majorId" id="major"  <c:if test="${majorId!=null&&majorId!=''}">disabled="disabled"</c:if> >
					<c:forEach items="${majorList }" var="major">
						<option value="${major.id}" <c:if test="${majorId eq major.id }">selected="selected"</c:if>>${major.majorName}</option>
					</c:forEach>
				</select>
			</p>
			<c:if test="${majorId==null||majorId==''}">
				<p>
					<label><button type="submit" id="addSave" >验证</button></label>
				</p>
			</c:if>
		</div>
		<%-- <c:if test="${majorId==null||majorId==''}">
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit" id="addSave" >验证</button></div></div></li>
				</ul>
			</div>
		</c:if> --%>
	</form>
</div>

<script type="text/javascript">


</script>
