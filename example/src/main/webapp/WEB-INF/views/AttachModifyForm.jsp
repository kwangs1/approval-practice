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
	<div class="uploadResult"> 
		<ul></ul>
	</div>
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
//파일 관련
var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
var maxSize = 5242880;
//유효성 및 용량 검사
function checkException(fileName, fileSize){
	if(fileSize >= maxSize){
		alert("파일 용량이 5MB를 초과하였습니다");
		$('.uploadFile').val('');
		return false;
	}
	if(regex.test(fileName)){
		alert("exe,sh,zip,alz의 확장자가 들어간 파일은 업로드가 되지 않습니다.");
		$('.uploadFile').val('');
		return false;
	}
	return true;
}
//파일 업로드
$("input[type='file']").change(function(e){
	var formData = new FormData();
	var inputFile = $("input[name='uploadFile']");
	var files = inputFile[0].files;
	
	for(var i=0; i<files.length; i++){
		if(!checkException(files[i].name, files[i].size)){
			return false;
		}
		formData.append("uploadFile",files[i]);
	}
	$.ajax({
		url: '<c:url value="/uploadFile.do"/>',
		processData: false,
		contentType: false,
		data: formData,
		type: 'post',
		dataType: 'json',
		success: function(result){
			showUploadResult(result);
		}
	})	
});
//UI상 보여질 부분
function showUploadResult(uploadResultAttr){
	if(!uploadResultAttr || uploadResultAttr.length == 0){
		return;
	}
	var str ="";
	var uploadUL = $('.uploadResult ul');
	//업로드 된 첨부파일에 대한./.
	$(uploadResultAttr).each(function(i, obj){
	    var fileCallPath =  encodeURIComponent( obj.uploadPath+"/"+ obj.uuid +"_"+obj.fileName);         
	    var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
		str += "<li data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'>";
		str += "<div><span style='cursor:pointer;' class='download'>" + obj.fileName + "</span>&nbsp;&nbsp;";
		str += "<button type='button' class='delete' onclick='ApprDocDeleteFiles()'>❌</button><br>";
		str += "</div></li>";
	})
	uploadUL.append(str);
	ApprDocInsertFiles();
}
function ApprDocInsertFiles(){
	var formData = new FormData();
	//showUploadResult 함수를 통해 첨부파일 추가된 부분에 대해 반복문을 통해 넘길데이터 설정
	$('.uploadResult ul li').each(function(i,obj){
		var obj_ = $(obj);
		
		var uuid = obj_.data("uuid");
		var uploadPath = obj_.data("path");
		var fileName = obj_.data("filename");
		var fileType = obj_.data("type");
		
		formData.append("attach["+ i +"].uuid",uuid);
		formData.append("attach["+ i +"].uploadPath",uploadPath);
		formData.append("attach["+ i +"].fileName",fileName);
		formData.append("attach["+ i +"].fileType",fileType);
	})
    formData.append("appr_seq", appr_seq);
	$.ajax({
		type:'post',
		url: "<c:url value='/ApprDocInsertFiles'/>",
		data: formData,
		processData: false,
		contentType: false,
		success: function(response){
			console.log(response);
			window.location.reload();
		},
		error: function(status, xhr){
			console.log(status);
			console.log(xhr);
		}
	});
}
</script>
</body>
</html>