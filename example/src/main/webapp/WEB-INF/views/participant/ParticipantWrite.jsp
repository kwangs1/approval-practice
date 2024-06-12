<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
.user-container {float: left; align-items: center; margin-right: 5px;}
.user-container div{display: flex; flex-direction: column; margin-right: 10px;}
.user-container input[type="text"] {height: 30px; border: 1px solid #ccc; border-radius: 5px; padding: 5px; font-size: 14px;}
.position {font-weight: bold; font-size: 12px;}
select{display:none;}
#inputs { display: flex;flex-wrap: wrap; pointer-events : none;}
</style>
</head>
<body>
	<button onclick="pop()" class="button">결재정보</button>
	<c:if test="${info.status != 4096 && info.status != 256}">	
		<button onClick="Appr_Btn();" class="button">상신</button>
	</c:if>
	<c:if test="${info.status == 4096}">
  		<button onclick="Resubmission()" class="button">재기안</button>
  	</c:if>
  	<c:if test="${sendInfo.receiverid eq user.deptid}">
		<button onclick="RceptDocSanc()" class="button" >접수</button>
	</c:if>
	<button onClick="window.close()" class="button">닫기</button>
	<br><br>
	<!-- 동적으로 생성 될 input box 위치 -->
		<div id="inputs"></div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="${path}/resources/js/ReceiveFlowInfo_.js"></script>
<script>
var sendid = '<c:out value="${info.sendid}"/>';
var appr_seq = '<c:out value="${info.appr_seq}"/>';
var status = '<c:out value="${info.status}"/>';

function pop() {
	if(sendid == ''){
		window.open("${path}/dept/flowUseInfo","pop","width=768, height=400");	
	}else{	
		url = "${path}/dept/RceptflowUseInfo?appr_seq="+appr_seq;
		window.open(url,"pop","width=768, height=400");		
	}
}

function participant() {
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
            status: status
        });
    });
    
    console.log('Send data: ', JSON.stringify(paticipant));
    
	
    $.ajax({
        type: 'post',
        url: '${path}/participant/ParticipantWrite',
        data: JSON.stringify(paticipant),
        contentType: 'application/json',
        success: function (response) {
            console.log('Ajax 요청: ' + response);

			var loading = document.getElementById('loading')
			loading.style.display = 'flex';
			
			setTimeout(function(){
	            alert('상신이 완료되었습니다.');
	            window.close();
	            window.opener.location.reload();
			},3000)
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