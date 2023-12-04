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
<title></title>
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
<input type='hidden' id='title' name='title' />
	<div>
		<button onclick='reload()'>새로 만들기</button>
	</div>
	<div>
		<textarea name="content" id="content" onkeydown="resize(this)" onkeyup="resize(this)"></textarea> 
	</div>
</form>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
function reload(){
	window.location.reload();
}
$(document).ready(function(){
	document.getElementById('content').focus();
});

function resize(obj) {
    obj.style.height = '1px';
    obj.style.height = (12 + obj.scrollHeight) + 'px';
}

function submit(){
	var title = prompt("저장 할 제목을 입력해주세요.");
	if(title !== null && title !== ''){
		document.getElementById('title').value = title;
		document.title = title;
	}
	var content = document.getElementById('content').value;
	$.ajax({
		type: 'post',
		url: '${path}/memo/write',
		contentType: 'application/json',//JSON 형식으로 전송 지정.
		data: JSON.stringify({content: content, title: title}),//JSON.stringify를 사용하여 JSON 형식으로 변환.
		success: function(){
			alert("저장이 완료되었습니다.");
			
			//부모창[list.jsp]에 작성 완료 시 ajaxList 호출.
            if (window.opener && !window.opener.closed) {
                window.opener.ajaxList();
            }
			setTimeout(() => 
				window.close()
			,300);
            
		},
		error: function(xhr, status, error){
			console.log(xhr);
			console.log(status);
			console.log(error);
		}
	});
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