<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<style>
body{font-family: Arial, sans-serif; margin:0; padding:0;}
.post{max-width:600px; border: 1px solid #ccc; border-radius: 5px;}
.postA{color: #666; font-size:14px; margin-bottom: 5px;}
</style>
<body>

<c:if test="${info.applid != 7000}">
<a href="subFolderAdd?fldrid=${info.fldrid}">폴더 추가</a> | 
</c:if>
<c:if test="${info.applid == 7010}">
	<a href="javascript:folderAddAndApprF()">기록물철 추가</a> |
</c:if>
<a href="edit?fldrid=${info.fldrid}">수정</a> |
<a href="javascript:window.close()">닫기</a>
<div class="post">
	<c:choose>
		<c:when test="${info.parfldrid eq null }">
			<p class="postA">상위폴더 | 없음</p>
		</c:when>
		<c:otherwise>
			<p class="postA">상위폴더 | ${info.parfldrname} </p>
		</c:otherwise>
	</c:choose>
	<p class="postA">폴더명 | ${info.fldrname} </p>
	<p class="postA">소유자 | ${info.ownerid} </p>
	
	<p class="postA">폴더유형 | ${info.appltype} </p>
	<p class="postA">생산년도 | ${info.year} </p>
	<p class="postA">종료년도 | ${info.endyear} </p>
</div>

<script>
var buCode = '${bInfo.bizunitcd}';
var deptid = '${bInfo.procdeptid}';
var usu = '<c:out value="${userId}"/>';
function folderAddAndApprF(){
	url = '<c:url value="/folder/folderAddAndApprF"/>';
	url += '?fldrid='+ '${info.fldrid}' + '&buCode=' + buCode + '&deptid=' + deptid + '&userid=' + usu;
	
	window.open(url, 'apprfolderIn', 'width=500px, height=500px');
}
</script>
</body>
</html>