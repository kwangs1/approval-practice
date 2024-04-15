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
  <%@ include file="../approval/ApprFrame.jsp" %>
  <br><br>
<div class="cd1">
  <h2>결재진행</h2>
  <a href="javascript:RetireAppr()">회수</a> |
  <a href="javascript:Resubmission_Pop()">재기안</a>
 <table class="table table-bordered">
    <thead>
      <tr>
      	<th style="width:10px;"><input type="checkbox" id="selectAll"></th>
        <th>제목</th>
        <th>기안자</th>
        <th>기안일시</th>
        <th>처리현황</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach var="list" items="${list}">
    <input type="hidden" name="drafterid" id="drafterid" value="${list.drafterid}" />
    <input type="hidden" id="a_status" value="${list.status}" />
	        <%-- 결재대기에 걸린 결재선 정보 가져오려고 --%>
	  <c:forEach var="participant" items="${participantInfo}" varStatus="loop">
		<c:if test="${participant.status == '1000' && participant.signerid == user.id}">
			<input type="hidden" id="appr_seq" value="${participant.appr_seq}" />
			<input type="hidden" id="participant_seq_${loop.index }" value="${participant.participant_seq}"/>
			<input type="hidden" id="signerid" value="${participant.signerid}"/>
			<input type="hidden" id="approvalstatus" value="${participant.approvalstatus}"/>
			<input type="hidden" id="approvaltype" value="${participant.approvaltype}"/>
			<input type="hidden" id="deptid" value="${participant.deptid}"/>
			<input type="hidden" id="status" value="${participant.status}"/>
		</c:if> 
	  </c:forEach>
	      <tr>
	      	<td><input type="checkbox" name="appr_seq" class="seq" value="${list.appr_seq }" /></td>
	        <td><a href="#" class="apprInfo" data-apprseq="${list.appr_seq}">${list.title }</a></td>
	        <td>${list.draftername}</td>
	        <td>${list.regdate }</td>
			<c:choose>
				<c:when test="${list.status == 4096}">
					<td>회수</td>
				</c:when>
				<c:otherwise>
					<td>진행</td>
				</c:otherwise>
			</c:choose>
	      </tr>    	  	
    </c:forEach>
    </tbody>
  </table>
</div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
var a_status = $('#a_status').val();
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
	
	for(var i=0; i<checkboxes.length; i++){
		if(checkboxes[i].checked){            
			var appr_seq = checkboxes[i].value;
			var participant_seq = $('#participant_seq_' + i).val();
			RetireDoc.push({
				appr_seq : appr_seq,
				participant_seq : participant_seq,
				signerid : signerid,
				deptid : deptid,
				status : status
			});
		}
	}
	
	if(RetireDoc == 0 || RetireDoc.length === 0){
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
				console.log(JSON.stringify(RetireDoc));
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
function Resubmission_Pop(){
	var appr_seq = $('.seq').val();
	var Resubmission_Ary= [];
	
	for(var i=0; i < checkboxes.length; i++){
		if(checkboxes[i].checked){
			var appr_seq = checkboxes[i].value;
			Resubmission_Ary.push({appr_seq : appr_seq});
		}
	}
	
	if(Resubmission_Ary == 0 || Resubmission_Ary.length == null){
		alert("재기안 할 문서를 선택하여주세요.");
		return
	}else if(Resubmission_Ary.length > 1){
		alert("하나의 문서만 선택해주세요.");
		return;
	}else if(a_status != 4096){
		alert("회수한 문서만 재기안이 가능합니다.");
		return;
	}
	url = '<c:url value="/approval/Resubmission"/>';
	url += "?appr_seq="+appr_seq;
	
	window.open(url,"Resubmission","width=1024px, height=768px");
}

$('a.apprInfo').on('click', function(event) {
    event.preventDefault();
    
    var appr_seq = $(this).attr("data-apprseq"); 
    var url = "<c:url value='/approval/apprInfo'/>" + "?appr_seq=" + appr_seq;
    window.open(url, "Info", "width=1024px, height=768px");
});

</script>
</body>
</html>