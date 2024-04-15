<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<%@ include file="../approval/apprFrame.jsp" %>
  <br><br>
<div class="cd1">
  <h2>결재대기</h2> 
  <a href="javascript:BundleApproval()">일괄결재</a>
 <table class="table table-bordered">
    <thead>
      <tr>
      	<th style="width:10px;"><input type="checkbox" id="selectAll"></th>
        <th>제목</th>
        <th>기안자</th>
        <th>기안날짜</th>
        <th>처리구분</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach var="list" items="${list}">
      <tr>
      	<td><input type="checkbox" name="appr_seq" class="seq" value="${list.appr_seq }"/></td>
        <td><a href="#" class="apprInfo" data-apprseq="${list.appr_seq}">${list.title }</a></td>
        <td>${list.draftername }</td>
        <td>${list.regdate }</td>
		<td>  
	        <%-- 결재대기에 걸린 결재선 정보 가져오려고 --%>
	  <c:forEach var="participant" items="${participantInfo}" varStatus="loop">
		<c:if test="${participant.approvaltype == 4 && participant.approvalstatus == 4098}">
			<input type="hidden" id="appr_seq" value="${participant.appr_seq}" />
			<input type="hidden" id="participant_seq_${loop.index }" value="${participant.participant_seq}"/>
			<input type="hidden" id="signerid" value="${participant.signerid}"/>
			<input type="hidden" id="approvalstatus" value="${participant.approvalstatus}"/>
			<input type="hidden" id="approvaltype" value="${participant.approvaltype}"/>
		</c:if> 
		<c:if test="${participant.approvaltype == 4 && list.appr_seq eq participant.appr_seq}">
			${participant.statusname}
		</c:if>	
	  </c:forEach>
	   </td>
	  </tr>
    </c:forEach>
    </tbody>
  </table>
</div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
$('a.apprInfo').on('click', function(event) {
    event.preventDefault();
    var appr_seq = $(this).attr("data-apprseq"); 
    var url = "<c:url value='/approval/apprInfo'/>" + "?appr_seq=" + appr_seq;
    window.open(url, "Info", "width=1024px, height=768px");
});

var checkboxes = document.getElementsByName('appr_seq');
//"전체 선택" 체크박스를 클릭했을 때 모든 체크박스를 선택 또는 해제하는 함수
$('#selectAll').change(function(){
	for(var i = 0; i < checkboxes.length; i++){
	  checkboxes[i].checked = this.checked;
	}
});

//일괄결재
function BundleApproval(){
	var ary = []; // 결재 시퀀스 배열
	<%-- 결재 대기에 걸린 결재선 정보--%>
	var signerid = $('#signerid').val();
	var approvalstatus = $('#approvalstatus').val();
	var approvaltype = $('#approvaltype').val();
	var selectParticipant = []; //전제 값 배열
	
	for(var i = 0; i < checkboxes.length; i++){
		if(checkboxes[i].checked){
			var appr_seq = checkboxes[i].value;
			//결재선 시퀀스 id중복으로 인하여 값을 제대로 가져오지 못하여 _ +i 로 구분지어 가져옴
			var participant_seq = $('#participant_seq_' + i).val();
			console.log(participant_seq);
			
			ary.push(appr_seq);
			selectParticipant.push({
				appr_seq: appr_seq,
				participant_seq: participant_seq,
				signerid: signerid,
				approvalstatus: approvalstatus,
				approvaltype: approvaltype
			});
		}
	}
	
	
	if(ary.length == 0 || ary.length == null){
		alert("선택된 문서가 존재하지 않습니다.");
		return;
	}
		console.log("일괄 결재 데이터 : "+ JSON.stringify(selectParticipant));
		
		$.ajax({
			type: 'post',
			url: '<c:url value="/participant/BulkAppr"/>',
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
	}
</script>
</body>
</html>