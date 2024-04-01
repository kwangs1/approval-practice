<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<style>
  table {
    width: 100%;
    border-top: 1px solid #444444;
    border-collapse: collapse;
  }
  th, td {
    border-bottom: 1px solid #444444;
    padding: 10px;
  }
</style>
<body>
<button onclick="window.close()">닫기</button>
<button onclick="write()">등록</button>
<button onclick="exportCSV()">파일 내보내기</button>
<button onclick="uploadCSV()">파일 업로드</button>
<table>
	<thead>
		<tr>
			<td>단위과제코드</td>
			<td>단위과제명</td>
			<td>단위과제설명</td>
			<td>담당부서</td>
			<td>생성날짜</td>
		</tr>
	</thead>
	<tbody>
	<c:forEach var="list" items="${list}">
		<tr>
			<td>${list.bizunitcd}</td>
			<td>${list.bizunitname}</td>
			<td>${list.bizunitdesc}</td>
			<td>${list.procdeptname}</td>
			<td>${list.bizopendate}</td>
		</tr>
	</c:forEach>
	</tbody>
</table>

<script>
function uploadCSV(){
	url = '<c:url value="/bizunit/uploadCSV"/>';
	window.open(url,'upload','width=400px, height=350px');
}

function exportCSV(){
	window.location.href = '<c:url value="/bizunit/exportCSV"/>';
}

function write(){
	url = '<c:url value="/bizunit/write"/>';
	var bizwindow = window.open(url,'upload','width=600px, height=600px');	
	if (bizwindow) {
		bizwindow.focus(); 
	}
}
</script>
</body>
</html>