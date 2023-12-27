<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<c:forEach var="user" items="${user}">
	<a href="#" class="userLink" data-id="${user.id}" data-name="${user.name}" data-pos="${user.pos}">
		${user.name} [${user.pos}]</a>
</c:forEach>
<hr>
<div id="selectedUsers"></div>
<button onclick="confirmSelection()">확인</button>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
var selectedUsers = [];
$('a.userLink').on('click',function(e){
	e.preventDefault();
	
	var id = $(this).data('id');
	var name = $(this).data('name');
	var pos = $(this).data('pos');
	<%--
	window.opener.postMessage({
		id : id, name : name, pos : pos},'*'
	);--%>
	selectedUsers.push({ id: id, name: name, pos: pos });

    // 동적으로 UI 업데이트
    updateSelectedUsersUI();
})
function updateSelectedUsersUI() {
    // 선택된 유저 정보를 기반으로 UI 업데이트
    var selectedUsersDiv = $('#selectedUsers');
    selectedUsersDiv.empty(); // 기존 내용을 비움

    // 배열에 있는 각 유저 정보를 순회하면서 UI에 추가
    for (var i = 0; i < selectedUsers.length; i++) {
       var user = selectedUsers[i];
        selectedUsersDiv.append('<p>' + user.name + ' [' + user.pos + ']</p>');
      }
  }
function confirmSelection(){
	window.opener.postMessage({ users: selectedUsers }, '*');
	window.close();
}
</script>
</body>
</html>