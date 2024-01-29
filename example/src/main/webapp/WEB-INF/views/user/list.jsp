<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
body {display: flex;}
#userList {flex: 1; border-right: 1px solid #ccc; padding-right: 10px; margin-right: 10px;}
#selectedUsers {flex: 1; padding-left: 10px;}
#selectedUsers p {margin-bottom: 10px;}
#userList a { display: block; margin-bottom: 10px;}
#confirmButton {margin-top: auto; /* 화면 아래로 내리기 */}
</style>
</head>
<body>
<div id="userList">
	<c:forEach var="user" items="${user}">
		<a href="#" class="userLink" data-id="${user.id}" data-name="${user.name}" data-pos="${user.pos}">
			${user.name} [${user.pos}]</a>
	</c:forEach>
</div>
<div id="selectedUsers"></div>
<a href="javascript:void(0)" id="confirmButton" onclick="confirmSelection()">확인</a>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
var selectedUsers = [];

$('a.userLink').on('click',function(e){
	e.preventDefault();
	
	var id = $(this).data('id');
	var name = $(this).data('name');
	var pos = $(this).data('pos');	
    
	//배열에 담아 전송
	selectedUsers.push({ id: id, name: name, pos: pos });
    // 유저 클릭시 동적으로 화면에 표시되게 하기위해 함수 호출
    updateSelectedUsersUI();
})
function updateSelectedUsersUI() {
    // 선택된 유저 정보를 기반으로 UI 업데이트
    var selectedUsersDiv = $('#selectedUsers');
    selectedUsersDiv.empty(); // 기존 내용을 비움
    

    // 배열에 있는 각 유저 정보를 순회하면서 UI에 추가
    for (var i = 0; i < selectedUsers.length; i++) {
       var user = selectedUsers[i];
       selectedUsersDiv.append('<p>' + user.name + ' [' + user.pos + '] ' + getStatusDropdownHTML(i) + '</p>');
      }
  }
 
//유저 화면단에서 임의로 status 값도 설정하여 전송하려고 만듬.
function getStatusDropdownHTML(index) {
    // status 선택을 위한 dropdown의 HTML을 반환
    var dropdownHTML = '<select name="status_' + index + '">' +
                       '<option value="1000">기안</option>' +
                       '<option value="2000">검토</option>' +
                       '<option value="3000">협조</option>' +
                       '<option value="4000">결재</option>' +
                       '</select>';
    return dropdownHTML;
}

function confirmSelection(){
    // 각 유저별로 select 태그의 값을 가져와서 status값도 전송
    for (var i = 0; i < selectedUsers.length; i++) {
        var status = $('[name="status_' + i + '"]').val();
        selectedUsers[i].status = status;
    }
	window.opener.postMessage({ users: selectedUsers }, '*');
	window.close();
}
</script>
</body>
</html>