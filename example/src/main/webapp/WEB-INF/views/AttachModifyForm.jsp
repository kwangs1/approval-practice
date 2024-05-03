<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<c:url value='/resources/css/info.css'/>">
</head>
<style>
.btn-upload {
width: 150px; height: 30px; background: #fff; border: 1px solid rgb(77,77,77); border-radius: 10px;
font-weight: 500; cursor: pointer; display: flex; align-items: center; justify-content: center;
&:hover {background: rgb(77,77,77); color: #fff; }
}
ul li{list-style:none; padding-left:0px;}
ul{padding-left:0px;}
.files{cursor:pointer;}
</style>
<body>
<button onclick="window.close()">닫기</button>
<button onclick="window.close()">확인</button>
<div class="post-container">
	<div>
		<label for="file" class="btn-upload">파일 업로드</label>			
		<input type="file" id="file" name="uploadFile" class="uploadFile" 
		multiple="multiple" style="display:none;"/>
	</div>
		<br>
	<c:forEach var="attach" items="${attach}">
	<input type="hidden" id="path" value="${attach.uploadPath }"/>
	<div class="uploadFileList">
		<ul>
			<li data-uuid="${attach.uuid}" data-filename="${attach.fileName}" data-path="${attach.uploadPath}">
			<c:if test="${info.attachcnt >0}">
				<span class="files"  data-type="file">${attach.fileName}</span>
				<button class="delete" onClick="ApprDocDeleteFiles()">❌</button>		
			</c:if>
			</li>
		</ul>
	</div>
	</c:forEach>
	<div class="uploadResult"> 
		<ul></ul>
	</div>
</div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="<c:url value='/resources/js/InfoUploadFile.js'/>"></script>
<script>
var drafterid = '<c:out value="${info.drafterid}"/>';
var appr_seq = '<c:out value="${info.appr_seq}"/>';
</script>
</body>
</html>