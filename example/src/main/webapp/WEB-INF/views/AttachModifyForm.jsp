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
<div class="post-container">
	<div>
		<label for="file" class="btn-upload">파일 업로드</label>			
		<input type="file" id="file" name="uploadFile" class="uploadFile" 
		multiple="multiple" style="display:none;"/>
	</div>
		<br>
	<div class="uploadResult">
		<ul></ul>
	</div>

	<c:forEach var="attach" items="${attach}">
	<div class="uploadFile">
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
</div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
var drafterid = '<c:out value="${info.drafterid}"/>';
var appr_seq = '<c:out value="${info.appr_seq}"/>';
//첨부파일 다운로드
$(".uploadFile").on('click','.files',function(e){
	var liObj = $(this).closest("li");
	var path = encodeURIComponent(liObj.data("path")+"/"+liObj.data("uuid")+"_"+liObj.data("filename"));
	self.location='<c:url value="/download"/>'+'?id='+drafterid+'&fileName='+path;
})
//파일삭제
function ApprDocDeleteFiles(){
	if(confirm("삭제하시겠습니까?")){
		$('.uploadFile').on('click','.delete',function(){
			var liObj = $(this).closest("li");
			var targetFile = encodeURIComponent(liObj.data("path")+"/"+liObj.data("uuid")+"_"+liObj.data("filename"));
			var type = $(this).data("type");
			var fileName = liObj.data('filename');	
			
			$.ajax({
				url: '<c:url value="/ApprDocDeleteFiles"/>',
				type: 'post',
				data: {fileName :fileName, appr_seq :appr_seq},
				dataType: 'text',
				success: function(response){
					alert("파일이 삭제가 되었습니다.");
					$.ajax({
						url: '<c:url value="/deleteFile"/>',
						type: 'post',
						data: {fileName: targetFile, type: type},
						dataType: 'text',
						success: function(response){
							targetLi.remove();
						}
					})//success end ajax
					window.location.reload();
					window.opener.location.reload();
				}//end success
			})//end ajax
		})	
	}
}
</script>
</body>
</html>