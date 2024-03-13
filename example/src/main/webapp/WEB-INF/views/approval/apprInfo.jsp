<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<link rel="stylesheet" href="${path}/resources/css/info.css">
<body>

<div class="post-container">
  <h1 class="post-title">${info.title}</h1>
  <p class="post-info">기안자: ${info.name} | 작성일: ${info.regdate }</p>
  <p class="post-info">신청날짜: ${info.startdate} ~ ${info.enddate}</p>
  <div class="post-content">
    <p>${info.content }</p>
  </div>
  <button onclick="FlowAppr()" class="button">결재</button>
  <button onclick="history.back()" class="button">뒤로가기</button>
</div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
var appr_seq = '<c:out value="${info.appr_seq}"/>';
var participant_seq = '<c:out value="${pInfo.participant_seq}"/>';
var approvaltype = '<c:out value="${pInfo.approvaltype}"/>';
var approvalstatus = '<c:out value="${pInfo.approvalstatus}"/>';
var id = '<c:out value="${userId}"/>';

var param = {	
	appr_seq : appr_seq,
	participant_seq : participant_seq,
	approvaltype : approvaltype,
	approvalstatus : approvalstatus,
	id : id}

function FlowAppr(){	
	$.ajax({
		type: 'post',
		url: '<c:url value="/participant/FlowAppr"/>',
		data: param,
		success: function(response){
			alert("결재 성공");
			console.log(response);
			window.location.href= "/kwangs/approval/apprWaitList?id="+id;
		},
		error: function(xhr,status,error){
			alert("결재 실패");
			console.log(xhr);
			console.log(status);
		}
	});
}
</script>
</body>
</html>