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
  <a href="javascript:history.back()">홈으로</a> | 
  <a href="javascript:approval_pop()">기안하기</a> | 
  <a href="javascript:BundleApproval()">일괄결재</a>  
  <!-- 결재선 정보 가져옴[ajax를 통해 해당 문서 결재선의 정보값을 넘겨주기 위해서]  -->
  <c:forEach var="participantInfo" items="${participantInfo}">
	<c:if test="${participantInfo.approvaltype == 4 && participantInfo.approvalstatus == 4098}">
		<input type="hidden" id="appr_seq" value="${participantInfo.appr_seq}" />
		<input type="hidden" id="participant_seq" value="${participantInfo.participant_seq}"/>
		<input type="hidden" id="id" value="${participantInfo.id}"/>
		<input type="hidden" id="approvalstatus" value="${participantInfo.approvalstatus}"/>
		<input type="hidden" id="approvaltype" value="${participantInfo.approvaltype}"/>
	</c:if>
  </c:forEach>
  
  <table class="table table-bordered">
    <thead>
      <tr>
      	<th style="width:10px;"><input type="checkbox" id="selectAll"></th>
        <th>제목</th>
        <th>기안자</th>
        <th>기안날짜</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach var="list" items="${list}"> <%-- 결재 테이블 데이터 --%>
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
	var ary = []; // 결재 시퀀스 배열
	<%-- --%>
	var participant_seq = $('#participant_seq').val();
	var id = $('#id').val();
	var approvalstatus = $('#approvalstatus').val();
	var approvaltype = $('#approvaltype').val();
	var selectParticipant = []; //전제 값 배열
	
	for(var i = 0; i < checkboxes.length; i++){
		if(checkboxes[i].checked){
			var appr_seq = checkboxes[i].value;
			ary.push(appr_seq);
		}
	}
	
	
	if(ary.length == 0 || ary.length == null){
		alert("선택된 문서가 존재하지 않습니다.");
		return;
	}else{
		
		for(var j =0; j < ary.length; j++){
			selectParticipant.push({
				appr_seq : ary[j],
	            participant_seq: participant_seq, 
	            id: id,
	            approvalstatus: approvalstatus,
	            approvaltype: approvaltype
			})
		}
		
		console.log("일괄 결재 데이터 : "+ JSON.stringify(selectParticipant));
		
		$.ajax({
			type: 'post',
			url: '${path}/participant/participantCheck',
			contentType: 'application/json',
			data: JSON.stringify(selectParticipant),
			success: function(response){
				console.log("일괄 결재 ajax 요청 데이터 ",response);
				alert("결재가 완료되었습니다.");
				window.location.reload();
			},
			error : function(xhr,error,status){
				alert("결재에 실패하였습니다.");
				console.log(xhr);
				console.log(error);
				console.log(status);
			}
		}); //end ajax
	}// end if-else
}
//"전체 선택" 체크박스를 클릭했을 때 모든 체크박스를 선택 또는 해제하는 함수
$('#selectAll').change(function(){
  var checkboxes = document.getElementsByName('appr_seq');
  for(var i = 0; i < checkboxes.length; i++){
    checkboxes[i].checked = this.checked;
  }
});
</script>
</body>
</html>