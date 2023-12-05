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
<title>${read.title}</title>
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
<input type='hidden' id='mno' name='mno' value="${read.mno}"/>
<input type='hidden' id='title' name='title' value="${read.title}"/>
	<div>
		<a href="javascript:newOpen()">새 창</a>
		<a href="javascript:SaveAs()">다른이름으로 저장</a>
		<a href="#" onclick="return printPage()">인쇄하기</a>

	</div>
	<div>
		<textarea name="content" id="content" onkeydown="resize(this)" onkeyup="resize(this)">${read.content}</textarea> 
	</div>
</form>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
function printPage(){
	window.print();
}
function beforePrint(){
	initBodyHtml = document.body.innerHTML;
	document.body.innerHTML = document.getElementById('content').innerHTML;
}
function afterPrint(){
	document.body.innerHTML = initBodyHtml;
}
window.onbeforeprint = beforePrint;//프린터 출력 이전 화면
window.onafterprint = afterPrint;//프린터 출력 이후 이벤트


$(document).ready(function(){
	document.getElementById('content').focus();
});

function resize(obj) {
    obj.style.height = '1px';
    obj.style.height = (12 + obj.scrollHeight) + 'px';
}

function submit(){
	var content = document.getElementById('content').value;
	var mno = document.getElementById('mno').value;
	
	$.ajax({
		type: 'post',
		url: '${path}/memo/update',
		contentType: 'application/json',//JSON 형식으로 전송 지정.
		data: JSON.stringify({content: content, mno: mno}),//JSON.stringify를 사용하여 JSON 형식으로 변환.
		success: function(){
			alert("저장이 완료되었습니다.");
			
			//부모창[list.jsp]에 작성 완료 시 ajaxList 호출.
            if (window.opener && !window.opener.closed) {
                window.opener.ajaxList();
            }
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

function SaveAs(){
	var t = document.getElementById('title').value;
	var mno = document.getElementById('mno').value;
	
	var title = prompt('바꿀 제목을 입력해주세요.');
	
	if(title == null || title.trim() == ''){
		title = t;
	}else{
		alert('저장이 완료되었습니다.');
		t = title;
	}
	$.ajax({
		type: 'post',
		url: '${path}/memo/TitleUpdate',
		contentType: 'application/json',
		data: JSON.stringify({title: title, mno: mno}),
		success: function(){
            if (window.opener && !window.opener.closed) {
                window.opener.ajaxList();
            }
		},
		error: function(xhr, status, error){
			console.log(xhr);
			console.log(status);
			console.log(error);
		}
	})
}

function newOpen(){
    window.open("${path}/memo/write", "memo", "width=1024, height=768, left=400");
}
</script>
</body>
</html>