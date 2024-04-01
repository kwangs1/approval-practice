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
<form name="uploadCSV" method="post" enctype="multipart/form-data">
	<input type="file" name="file" id="file"/>
	<br><br>
	<button type="button" onclick="upload()" id="uploadButton">등록</button>
	<button type="button" onclick="window.close()">닫기</button>
</form>

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
		var loading = document.getElementById('loading');
		loading.style.display = 'flex';
	
		document.uploadCSV.action ='<c:url value="/bizunit/uploadCSV"/>';
		document.uploadCSV.submit();
		
		setTimeout(function() {
			window.close();
			opener.location.reload();
		}, 5000);
}

document.getElementById('uploadButton').addEventListener('click', function() {
	  upload();
});
</script>
</body>
</html>