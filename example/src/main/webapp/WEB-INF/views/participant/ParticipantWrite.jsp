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
	<button onclick="pop()">결재선 정보</button>
	<c:if test="${info.status != 4096 }">	
		<button onClick="Appr_Btn();">상신</button>
	</c:if>
	<c:if test="${info.status == 4096 }">
  		<button onclick="Resubmission()" class="button">재기안</button>
  	</c:if>
	<button onClick="window.close()">닫기</button>
	<br><br>
	<!-- 동적으로 생성 될 input box 위치 -->
		<div id="inputs"></div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="${path}/resources/js/ReceiveFlowInfo_.js"></script>
<script>
function pop() {
	window.open("${path}/dept/flowUseInfo","pop","width=768, height=400");
}

$(document).ready(function(){
	var id = '<c:out value="${user.id}"/>';
	$.ajax({
		url: "<c:url value='/getXmlData'/>",
		type: "get",
		data: {id : id},
		dataType: 'json',
		success: function(data){
			for(var i =0; i<data.length; i++){
				var participant = data[i];
				drawParticipant(participant);
			}
		},
		error: function(xhr, status, error){
			console.log(xhr);
			console.log(error);
			console.log(status);
		}
	});//end ajax	
	
	function drawParticipant(participant) {
		  // participant 정보를 이용하여 화면에 출력하는 코드 작성
		  // 예시: #inputFlow .container에 리스트 아이템을 추가하여 출력
		  var $container = $("<div>", { class: "user-container" });
		  var $deptid = $("<input>", { type: "hidden", name: "deptid_" }).val(participant.deptid);
		  var $deptname = $("<input>", { type: "hidden", name: "deptname_" }).val(participant.deptname);
		  var $signerid = $("<input>", { type: "hidden", name: "signerid_" }).val(participant.signerid);
		  var $signername = $("<input>", { type: "text", name: "signername_" }).val(participant.signername);
		  var $pos = $("<input>", { type: "hidden", name: "pos_" }).val(participant.pos);
		  var $status = $("<select>", { name: "status_" }).append(
		   $("<option>", { value: "1000" }).text("기안"),
		   $("<option>", { value: "2000" }).text("검토"),
		   $("<option>", { value: "3000" }).text("협조"),
		   $("<option>", { value: "4000" }).text("결재")
		  ).val(participant.status);

		  var $userDiv = $("<div>").append($("<span>").addClass("position").text($pos.val()),$signername);
		  $container.append($deptid, $deptname, $signerid, $userDiv, $pos, $status);
		  $("#inputs").append($container);
		 }
})

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