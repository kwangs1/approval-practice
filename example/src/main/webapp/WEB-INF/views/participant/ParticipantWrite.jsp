<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<button onclick="pop()">결재선 정보</button>
	<!-- 동적으로 생성 될 input box 위치 -->
		<div id="inputs"></div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="${path}/resources/js/RecFlowInfo.js"></script>
<script>
function pop() {
	window.open("${path}/dept/flowUseInfo","pop","width=768, height=400");
}

function participant() {
	//결재선 데이터
    var paticipant = [];

    $('#inputs .user-container').each(function () {
        var userContainer = $(this);
        var deptid = userContainer.find('input[name^="deptid_"]').val();
        var deptname = userContainer.find('input[name^="deptname_"]').val();
        var name = userContainer.find('input[name^="name_"]').val();
        var id = userContainer.find('input[name^="id_"]').val();
        var pos = userContainer.find('input[name^="pos_"]').val();
        var status = userContainer.find('select[name^="status_"]').val();

        paticipant.push({
        	deptid: deptid,
        	deptname: deptname,
            name: name,
            id: id,
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
            alert('상신이 완료되었습니다.');
            window.close();
            window.opener.location.reload();
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