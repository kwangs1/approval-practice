<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<link rel="stylesheet" href="${path}/resources/info.css">
<body>
<div class="post-container">
  <h1 class="post-title">${info.title}</h1>
  <p class="post-info">기안자: ${info.name} | 작성일: ${info.regdate }</p>
  <p class="post-info">신청날짜: ${info.startdate} ~ ${info.enddate}</p>
  <div class="post-content">
    <p>${info.content }</p>
  </div>
  <button onclick="apprCheck()" class="button">결재</button>
  <button onclick="history.back()" class="button">뒤로가기</button>
</div>

<script>

</script>
</body>
</html>