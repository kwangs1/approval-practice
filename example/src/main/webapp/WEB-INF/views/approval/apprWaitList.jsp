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
<form method="post">
	<input type="hidden" name="participant_seq" value="${participantInfo.participant_seq}"/>
</form>
<div class="container">
  <h2>결재</h2> 
  <a href="javascript:history.back()">홈으로</a> | 
  <a href="javascript:approval_pop()">기안하기</a> | 
  <a href="javascript:BundleApproval()">일괄결재</a>      
  <table class="table table-bordered">
    <thead>
      <tr>
      	<th style="width:10px;"></th>
        <th>제목</th>
        <th>기안자</th>
        <th>기안날짜</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach var="list" items="${list}">
      <tr>
      	<td><input type="checkbox" name="appr_seq" value="${list.appr_seq }" /></td>
        <td><a href="${path}/approval/apprInfo?appr_seq=${list.appr_seq}">${list.title }</a></td>
        <td>${list.name }</td>
        <td>${list.regdate }</td>
      </tr>   
    </c:forEach>
    </tbody>
  </table>
</div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
function approval_pop(){
	window.open("${path}/approval/apprView","approval","width=1024px, height=768");
}

function BundleApproval(){
	var checkboxes = document.getElementsByName('appr_seq');
	var ary = [];
	
	for(var i = 0; i < checkboxes.length; i++){
		if(checkboxes[i].checked){
			var seq = checkboxes[i].value;
			ary.push(seq);
		}
	}
	
	if(ary.length == 0 || ary.length == null){
		alert("선택된 문서가 존재하지 않습니다.");
		return;
	}else{
		var appr_seq = ary.join(',');
		console.log(appr_seq);
		
		$.ajax({
			url: '${path}/participant/participantCheck',
			type: 'post',
			data: JSON.stringify({appr_seq : appr_seq}),
			
			success: function(){
				alert("결재가 완료되었습니다.");
				window.location.reload();
			},
			error : function(xhr,error,status){
				console.log(xhr);
				console.log(error);
				console.log(status);
			}
		}); //end ajax
	}// end if-else
}
</script>
</body>
</html>