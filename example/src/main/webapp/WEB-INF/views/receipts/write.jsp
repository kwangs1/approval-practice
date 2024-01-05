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
	<button onclick="pop()">유저 목록</button>
	<!-- 동적으로 생성 될 input box 위치 -->
		<div id="inputs"></div>
		<input type="hidden" id="recId" name="id" value="${user.id}" />
		<input type="hidden" id="recName" name="name" value="${user.name}" />
	<a href="javascript:void(0);" onclick="participant();">상신</a>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
function pop(){
	window.open("${path}/user/list","pop","width=300, height=300");
}

window.addEventListener('message',function(e){
	var data = e.data;
	var users  = data.users;
	console.log(data.users);
	//user list에서 선택한 값을 전달 받아 동적으로 생성
	 var inputs = $('#inputs');
	 inputs.empty();
	//동적으로 생성 시 name값이 중복되면 하나의 값으로 보기에 반복문을 통해 i의 값으로 구분
	//즉 인덱스 번호라 생각하면 됨.
		for (var i = 0; i < users.length; i++) {
			var userContainer = $('<div class="user-container">');
			userContainer.append('<input type="text" name="name_' + i + '" value="' + users[i].name + '" />');
			userContainer.append('<input type="hidden" name="id_' + i + '" value="' + users[i].id + '" />');
			userContainer.append('<input type="hidden" name="pos_' + i + '" value="' + users[i].pos + '" />');
			
			var statusDropdown = $('<select name="status_' + i + '">');
			statusDropdown.append('<option value ="1000">기안</option>');
			statusDropdown.append('<option value ="2000">검토</option>');
			statusDropdown.append('<option value ="3000">협조</option>');
			statusDropdown.append('<option value ="4000">결재</option>');
			
			userContainer.append(statusDropdown);
			inputs.append(userContainer);
		}
		
		
});

function participant() {
	//결재선 데이터
    var paticipant = [];

    $('#inputs .user-container').each(function () {
        var userContainer = $(this);
        var name = userContainer.find('input[name^="name_"]').val();
        var id = userContainer.find('input[name^="id_"]').val();
        var pos = userContainer.find('input[name^="pos_"]').val();
        var status = userContainer.find('select[name^="status_"]').val();

        paticipant.push({
            name: name,
            id: id,
            pos: pos,
            status: status
        });
    });
    
    console.log('Send data: ', JSON.stringify(paticipant);
	
    $.ajax({
        type: 'post',
        url: '${path}/receipts/write',
        data: JSON.stringify(paticipant),
        contentType: 'application/json',
        success: function (response) {
            console.log('Ajax 요청: ' + response);
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