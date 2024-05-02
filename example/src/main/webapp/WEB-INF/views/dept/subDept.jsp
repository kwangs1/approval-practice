<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<form method="post" name="subDept">
	<!-- 파라미터값에 대한 상위부서 -->
	<input type="hidden" id="superDept" value="${info.deptname}" />
	<input type="hidden" name="parid" value="${info.deptid}" />
	
	<!-- 하위부서 값 -->
	<input type="hidden" name="org_deptname" />
	<input type="text" value="${info.deptname}" disabled="disabled"/></br>
	<input type="text" name="deptname" placeholder="부서명" autofocus="autofocus"/></br>
	<input type="text" name="deptcode" placeholder="부서코드" /></br>
	<input type="text" name="abbreviation" placeholder="부서약어"/></br>
	<input type="text" name="sendername" placeholder="발신명의"
		onkeydown="javascript: if (event.keyCode == 13){insertBtn()}"/></br>
	
	<button type="button" onclick="insertBtn()">생성</button>
	<button type="button" onclick="javascript:history.back()">뒤로가기</button>
</form>

<script>
function insertBtn(){
	var superDept = document.getElementById('superDept').value;
	var deptname = document.getElementsByName('deptname')[0].value;
	
	var org_deptname = getHierarchyDeptname(superDept, deptname)
	
	document.getElementsByName('org_deptname')[0].value = org_deptname
	
	document.subDept.action = "<c:url value= '/dept/subDept'/>";
	document.subDept.submit();
}

function getHierarchyDeptname(superDept, currentDeptname){
	if(superDept){
		return superDept + '/' + currentDeptname; 
	}else{
		return currentDeptname;
	}
}
</script>
</body>
</html>