<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath }" />    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<div class="container">
  <h2>결재</h2> 
  <a href="javascript:approval_pop()">기안하기</a>
  <a href="javascript:history.back()">홈으로</a>      
  <table class="table table-bordered">
    <thead>
      <tr>
        <th>제목</th>
        <th>기안자</th>
        <th>기안날짜</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach var="list" items="${list}">
      <tr>
        <td>${list.title }</td>
        <td>${list.name }</td>
        <td>${list.regdate }</td>
      </tr>   
    </c:forEach>
    </tbody>
  </table>
</div>

<script type="text/javascript">
function approval_pop(){
	window.open("${path}/approval/apprView","approval","width=1024px, height=768");
}
</script>
</body>
</html>