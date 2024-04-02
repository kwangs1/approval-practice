<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<link rel="stylesheet" href="<c:url value='/resources/css/loading.css'/>"/>
<body>
<div class="loading" id="loading" style="display: none">
	<div class="spinner">
	  	<span></span>
  		<span></span>
  		<span></span>
	</div>
	<p>파일을 업로드 중입니다..</p>
</div>
	<input type="file" name="file" id="file"/>
	<br><br>
	<button type="button" onclick="upload()">등록</button>
	<button type="button" onclick="window.close()">닫기</button>


<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
function upload(){
	var fileInput = document.getElementById('file');
	var file = fileInput.files[0];
	
	if(!file){
		alert("파일이 선택되지 않았습니다.");
		return false;
	}
	
	var fileName = file.name;
	var fileExt = fileName.substring(fileName.lastIndexOf('.')+1).toLowerCase();
	if(fileExt !== 'csv'){
		alert("CSV 파일을 선택해주세요.");
		return false;
	}
	
	var formData = new FormData();
	formData.append('file',file);
	$.ajax({
		type:"post",
		url:'<c:url value="/bizunit/uploadCSV"/>',
		data: formData,
		processData: false, //jquery가 데이터를 문자열로 변환하지 않도록
		contentType: false, //jquery가 올바른 multipart/form-data 헤더를 설정
		success:function(){
			var loading = document.getElementById('loading');
			loading.style.display = 'flex';
			
			setTimeout(function() {
				alert("파일이 업로드 되었습니다.");
				window.close();
				opener.location.reload();
			}, 3000);
		},
		error : function(xhr,status,error){
			console.log(xhr);
			console.log(status);
			console.log(error);
		}
	})
		
}
</script>
</body>
</html>