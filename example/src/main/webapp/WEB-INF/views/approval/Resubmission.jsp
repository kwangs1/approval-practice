<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body onload="pop()">
	<button onclick="pop()">결재선 정보</button>
	<!-- 동적으로 생성 될 input box 위치 -->
		<div id="inputs"></div>
<hr>
  <button onclick="Resubmission()" class="button">재기안</button>
  <button onclick="window.close()" class="button">닫기</button>
<hr>
<body>

휴가 기간: <input type="date" name="startdate" id="startdate" value="${info.startdate}"/> ~
	<input type="date" name="enddate" id="enddate" value="${info.enddate}"/>
<br><br>

<textarea name="title" id="title" rows="1" cols="50" placeholder="제목입력" autofocus="autofocus">${info.title }</textarea>
<br><br>

<textarea name="content" id="content" rows="10" cols="80" placeholder="내용입력">${info.content }</textarea>
<br><br>

<c:forEach var="pInfo" items="${pInfo}" varStatus="loop">
	<input type="hidden" id="participant_seq_${loop.index}" value="${pInfo.participant_seq}"/>
	<input type="hidden" id="signerid_${loop.index}" value="${pInfo.signerid}"/>
	<input type="hidden" id="approvaltype_${loop.index}" value="${pInfo.approvaltype}"/>
</c:forEach>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="${path}/resources/js/ReceiveFlowInfo.js"></script>
<script>
var appr_seq = '<c:out value="${info.appr_seq}"/>';
var drafterid = '<c:out value="${info.drafterid}"/>';
var status = '<c:out value="${info.status}"/>';

var param = {appr_seq : appr_seq, drafterid: drafterid, status: status }
function Resubmission(){
	$.ajax({
		type: 'post',
		url: '<c:url value="/approval/Resubmission"/>',
		data: param,
		success: function(){
			ResubmissionFlowStatusUpd();
			ResubmissionParticipantWrite();
			alert('해당 문서 재기안 되었습니다.');
			window.close();
			opener.location.reload();
		},
		error: function(error){
			alert('재기안 실패 하였습니다.');
			console.log(error);
		}
	})
}

function ResubmissionFlowStatusUpd(){
	var flowSigner = [];
	
	$('input[id^="participant_seq_"]').each(function(){
		var index = $(this).attr('id').split("_")[1];
		var participant_seq = $(this).val();
		var signerid = $(this).next().val();
		var approvaltype = $(this).next().next().val();
		
		flowSigner.push({
			appr_seq : appr_seq,
			participant_seq : participant_seq,
			signerid : signerid,
			approvaltype: approvaltype
		});		
	})
	
	$.ajax({
		type: 'post',
		url: '<c:url value="/participant/ResubmissionFlowStatusUpd"/>',
		data: JSON.stringify(flowSigner),
		contentType: 'application/json',
		success: function (response){
			console.log(response);
			console.log(JSON.stringify(flowSigner));
		},
		error: function(error){
			console.log(error);
		}
	})
}

function pop() {
	window.open("${path}/dept/flowUseInfo","pop","width=768, height=400");
}

function ResubmissionParticipantWrite() {
	var line_seq = $('#line_seq').val();
	//결재선 데이터
    var paticipant = [];

    $('#inputs .user-container').each(function () {
        var userContainer = $(this);
        var deptid = userContainer.find('input[name^="deptid_"]').val();
        var deptname = userContainer.find('input[name^="deptname_"]').val();
        var signername = userContainer.find('input[name^="signername_"]').val();
        var signerid = userContainer.find('input[name^="signerid_"]').val();
        var pos = userContainer.find('input[name^="pos_"]').val();
        var status = userContainer.find('select[name^="status_"]').val();

        paticipant.push({
        	deptid: deptid,
        	deptname: deptname,
        	signername: signername,
            signerid: signerid,
            pos: pos,
            status: status,
            appr_seq : appr_seq
        });
    });
    
    console.log('Send data: ', JSON.stringify(paticipant));
    
	
    $.ajax({
        type: 'post',
        url: '<c:url value="/participant/ResubmissionParticipantWrite"/>',
        data: JSON.stringify(paticipant),
        contentType: 'application/json',
        success: function (response) {
            console.log(response);
        },
        error: function (xhr, status, error) {
            console.log(xhr);
            console.log(status);
            console.log(error);
        }
    });
}
</script>
</body>
</html>