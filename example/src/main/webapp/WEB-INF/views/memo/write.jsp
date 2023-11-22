<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var='path' value='${pageContext.request.contextPath }'/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<style>
textarea{
	width: 100%;
	min-height: 5rem;
	overflow-y: hidden;
	resize: none;
	border: none;
	outline: none;
}
</style>
<body>
<form id='frm'>
	<div>
		<button onclick='submit()'>저장</button>
	</div>
	<div>
		<textarea name="content" id="content" onkeydown="resize(this)" onkeyup="resize(this)"></textarea> 
	</div>
</form>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
$(document).ready(function(){
	document.getElementById('content').focus();

});

function resize(obj) {
    obj.style.height = '1px';
    obj.style.height = (12 + obj.scrollHeight) + 'px';
}

function submit(){
	var frmObj = document.getElementById('frm');
	frmObj.action = "${path}/memo/write";
	frmObj.method = 'post';
	
	frmObj.submit();
}

document.addEventListener('keydown', function(event){
	if((event.ctrlKey || event.metaKey) && event.key === 's'){
		event.preventDefault();
		submit();
	}
})
</script>
</body>
</html>