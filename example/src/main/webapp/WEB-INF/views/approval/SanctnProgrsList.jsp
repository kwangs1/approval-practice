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
  <h2>결재진행<a href="/kwangs">🏠</a></h2>
  <hr>
  <%@ include file="../common/apprMenu.jsp" %>
  <hr> 
  <a href="javascript:RetireAppr()">회수</a>
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
    <input type="hidden" name="drafterid" id="drafterid" value="${list.drafterid}" />
      <tr>
      	<td><input type="checkbox" name="appr_seq" class="seq" value="${list.appr_seq }" /></td>
        <td><a href="${path}/approval/apprInfo?appr_seq=${list.appr_seq}">${list.title }</a></td>
        <td>${list.draftername }</td>
        <td>${list.regdate }</td>
      </tr>   
	        <%-- 결재대기에 걸린 결재선 정보 가져오려고 --%>
	  <c:forEach var="participant" items="${participantInfo}" varStatus="loop">
		<c:if test="${participant.status == 1000 && participant.signerid == list.drafterid}">
			<input type="hidden" id="appr_seq" value="${participant.appr_seq}" />
			<input type="hidden" id="participant_seq_${loop.index }" value="${participant.participant_seq}"/>
			<input type="hidden" id="signerid" value="${participant.signerid}"/>
			<input type="hidden" id="approvalstatus" value="${participant.approvalstatus}"/>
			<input type="hidden" id="approvaltype" value="${participant.approvaltype}"/>
			<input type="hidden" id="deptid" value="${participant.deptid}"/>
			<input type="hidden" id="status" value="${participant.status}"/>
		</c:if> 
	  </c:forEach>
    </c:forEach>
    </tbody>
  </table>
</div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
var checkboxes = document.getElementsByName('appr_seq');

<%-- 결재 진행에 걸린 결재선 정보--%>
var signerid = $('#signerid').val();
var approvalstatus = $('#approvalstatus').val();
var approvaltype = $('#approvaltype').val();
var status = $('#status').val();
var drafterid = $('#drafterid').val();
var deptid = $('#deptid').val();
var userid = '<c:out value="${user.id}"/>';
//"전체 선택" 체크박스를 클릭했을 때 모든 체크박스를 선택 또는 해제하는 함수
$('#selectAll').change(function(){
	for(var i = 0; i < checkboxes.length; i++){
	  checkboxes[i].checked = this.checked;
	}
});

//회수
function RetireAppr(){
	var RetireDoc = [];
	var checkedIndexes = []; // 이미 처리된 문서의 인덱스 저장용 배열
	
	for(var i=0; i<checkboxes.length; i++){
		if(checkboxes[i].checked){            
			var appr_seq = checkboxes[i].value;
			var participant_seq = $('#participant_seq_'+i).val();
			
			RetireDoc.push({
				appr_seq : appr_seq,
				participant_seq : participant_seq,
				signerid : signerid,
				deptid : deptid,
				approvalstatus : approvalstatus,
				status : status
			});
		}
		console.log("RetireAppr SendData "+JSON.stringify(RetireDoc))
	}
	
	if(RetireDoc == 0 || RetireDoc.length == null){
		alert("선택된 문서가 없습니다.");
		return;
	}else if(drafterid !== userid){
		alert("문서의 기안자만 회수가 가능합니다");
		return;
	}else{
		$.ajax({
			type:'post',
			url: '<c:url value="/participant/RetireAppr"/>',
			contentType: 'application/json',
			data: JSON.stringify(RetireDoc),
			success: function(response){
				console.log(response);
				alert("회수 하였습니다.");
				window.location.reload();
			},
			error: function(error){
				alert("회수 하는 과정에서 오류가 발생하였습니다.");
				console.log(error);
			}
		})
	}
}
</script>
</body>
</html>