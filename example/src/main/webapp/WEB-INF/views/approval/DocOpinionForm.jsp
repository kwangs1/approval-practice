<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
.btn{height: 85px; position:fixed;}
#ChildOpi{margin-right: 10px;}
</style>
</head>
<body>
<%@ include file="../approval/DocOpinionListForm.jsp" %>
<br>
<div class="container" id="container" style="display: none;">
	<textarea rows="5" cols="85" id="ChildOpi" autofocus="autofocus" placeholder="해당 문서에대한 의견을 기입하여 주세요."></textarea>
	<button class="btn"onClick="setParentText()">확인</button>
</div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
var signerid = window.opener.signerid && !window.opener.closed ?
		window.opener.signerid : '';
var childSendid = window.opener.childSendid && !window.opener.closed ?
		window.opener.childSendid : '';
var userid = '<c:out value="${user.id}"/>'

$(document).ready(function(){
	console.log('approvaltype:', approvaltype);
	console.log('signerid:', signerid);
	console.log('userid:', userid);
	if(approvalstatus == '4' || CheckOpinion === 'false' || sendtype === '2'){
		document.getElementById('container').style.display = 'block';
	}else{
		document.getElementById('container').style.display = 'none';
	}
})
function formatDate(date){
	var year = date.getFullYear();
	var month = String(date.getMonth()+1).padStart(2, '0');
	var day = String(date.getDate()).padStart(2, '0');
	var hours = String(date.getHours()).padStart(2, '0');
	var minutes = String(date.getMinutes()).padStart(2, '0');
	var seconds = String(date.getSeconds()).padStart(2, '0');
	
	var today = year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
	return today;
}
const now = new Date();

function setParentText(){
	if( $('#ChildOpi').val() == ''){
		alert("의견을 기입하여주세요.");
		return;
	}else{
		if(approvaltype === '4' && signerid === userid){

			$.ajax({
				url: '<c:url value="/approval/FlowOpinionAdd"/>',
				type: 'post',
				contentType: 'application/json',
				data: JSON.stringify({participant_seq: participant_seq, opinioncontent: $('#ChildOpi').val(),
						credate: formatDate(now), poststatus: poststatus}),
				success: function(response){
					console.log(formatDate(now));
					location.reload();
				},
				error: function(xhr, status, error){
					alert(error);
					console.log(xhr);
					console.log(status);
				}
			})
		}else if(poststatus === '2'){
			const content = document.getElementById('ChildOpi').value;
			const name = '<c:out value="${userName}"/>';
			opener.document.getElementById("parentOpi").value = content;
			opener.document.getElementById("credate").value = formatDate(now);
			saveOpinionToCookie(name,formatDate(now),content);
			if(window.opener && !window.opener.closed){
				window.opener.RceptDocReturn();
			}
			window.close();
		}else{
			const content = document.getElementById('ChildOpi').value;
			const name = '<c:out value="${userName}"/>';
			opener.document.getElementById("parentOpi").value = content;
			opener.document.getElementById("credate").value = formatDate(now);
			saveOpinionToCookie(name,formatDate(now),content);
			window.close();
		}	
	}
}

</script>
</body>
</html>