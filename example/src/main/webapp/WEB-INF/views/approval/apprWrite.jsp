<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var = "path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<c:url value='/resources/css/loading.css'/>"/>
<style>
.btn-upload {
width: 150px; height: 30px; background: #fff; border: 1px solid rgb(77,77,77); border-radius: 10px;
font-weight: 500; cursor: pointer; display: flex; align-items: center; justify-content: center;
&:hover {background: rgb(77,77,77); color: #fff; }
}
.uploadResult ul li{list-style:none; padding-left:0px;}
.uploadResult ul{padding-left:0px;}
</style>
</head>
<body>
<div class="loading" id="loading"style="display:none">
	<div class="spinner">
		<span></span>
		<span></span>
		<span></span>
	</div>
	<p>상신 중..</p>
</div>
<%@ include file="../participant/ParticipantWrite.jsp" %>
<hr>

	<button onClick="Appr_Btn();">상신</button>
	<button onClick="window.close()">닫기</button>
<hr>
<input type="hidden" name="draftername" id="draftername" value="${user.name}" />
<input type="hidden" name="drafterid" id="drafterid" value="${user.id}" />

<input type="hidden" name="folderid" id="folderid"/>
<input type="hidden" name="bizunitcd" id="bizunitcd"/>
<input type="hidden" name="attachcnt" id="attachcnt"/>
<body>

휴가 기간: <input type="date" name="startdate" id="startdate"/> ~
	<input type="date" name="enddate" id="enddate" />
<br><br>

<textarea name="title" id="title" rows="1" cols="50" placeholder="제목입력" autofocus="autofocus"></textarea>
<br><br>

<textarea name="content" id="content" rows="10" cols="80" placeholder="내용입력"></textarea>
<br><br>

	<div>
		<div>
			<label for="file" class="btn-upload">파일 업로드</label>		
			<input type="file" id="file" name="uploadFile" class="uploadFile" 
			multiple="multiple" style="display:none;"/>
		</div>
		<br>		
		<div class="uploadResult">
			<ul>
			</ul>
		</div>
	</div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
var drafterdeptid = '<c:out value="${user.deptid}"/>';
var drafterdeptname = '<c:out value="${user.deptname}"/>';
var docregno = '<c:out value="${uInfo.abbreviation}"/>';
var id = '<c:out value="${user.id}"/>';
var attachcnt = $('#attachcnt').val();
//새로고침 방지
window.addEventListener('keydown',function(e){
	//78: ctrl+N , 82: ctrl+R , 116: F5 | ctrlKey : window , metaKey : Mac
	if((e.ctrlKey == true || e.metaKey == true && (e.keyCode == 78 || e.keyCode == 82)) || (e.keyCode == 116))
	{
        e.preventDefault();
        e.stopPropagation();
	}
})

window.onload = function(){
	var startDay = new Date();
	var endDay = new Date(startDay);
	endDay.setDate(startDay.getDate() +7);
	
	startDay = startDay.toISOString().slice(0,10);
	endDay = endDay.toISOString().slice(0,10);
	
	start = document.getElementById('startdate');
	end = document.getElementById('enddate');
	
	start.value = startDay;
	end.value = endDay;
	}

	window.addEventListener('message', function(e) {
		var data = e.data;
		var folderid = data.fldrid;
		var bizunitcd = data.bizunitcd;

		$('#folderid').val(folderid);
		$('#bizunitcd').val(bizunitcd);
	});

	function Appr_Btn() {
		// FormData 객체 생성 
		//[첨부파일 데이터와 묶어서 서버로 보내기 위해서 결재 데이터도 같이 FormData에 넣어줌.]
		var formData = new FormData(); 

	    // 기안 시 등록하는 필드 정보 추가
	    formData.append('draftername', $('#draftername').val());
	    formData.append('drafterid', $('#drafterid').val());
	    formData.append('title', $('#title').val());
	    formData.append('content', $('#content').val());
	    formData.append('startdate', $('#startdate').val());
	    formData.append('enddate', $('#enddate').val());
	    formData.append('drafterdeptid', drafterdeptid);
	    formData.append('drafterdeptname', drafterdeptname);
	    formData.append('docregno', docregno);
	    formData.append('folderid', $('#folderid').val());
	    formData.append('bizunitcd', $('#bizunitcd').val());
	    formData.append('attachcnt', attachcnt);

	    // 파일 정보 추가
	    UploadFileAppend(formData);
	  	/*
	  	 * 파일 등록 시 ajax에서는 일반적인 body에서의 form태그 안 enctype="multipart/form-data" 의 값을 설정하기위해 
	  	 * processData, contentType 값을 flase로 설정
	  	*/
		$.ajax({
			type : "post",
			url : "${path}/approval/apprWrite",
			data : formData,        
	        processData: false,
	        contentType: false,
			success : function(response) {
				participant();
			},
			error : function(xhr, status, error) {
				console.log(xhr);
				console.log(status);
				console.log(error);
			}
		})
	}
//업로드 파일 관련 JS
	var uploadUL = $(".uploadResult ul");
	var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	var maxSize = 5242880; //5MB

	function checkExtension(fileName, fileSize) {

		if (fileSize >= maxSize) {
			alert("파일 사이즈 초과");
			return false;
		}

		if (regex.test(fileName)) {
			alert("해당 종류의 파일은 업로드할 수 없습니다.");
			return false;
		}
		return true;
	}
	//작성뷰에서 파일 업로드 시 PC에 저장시키기 위한 JS
	$("input[type='file']").change(function(e){
		var formData = new FormData();
		var inputFile = $("input[name='uploadFile']");
		var files = inputFile[0].files;
		for (var i = 0; i < files.length; i++) {
			if (!checkExtension(files[i].name, files[i].size)) {
					return false;
				}
			formData.append("uploadFile", files[i]);
		}
		$.ajax({
			url : '<c:url value="/uploadFile"/>',
			processData : false,
			contentType : false,
			data : formData,
			type : 'POST',
			dataType : 'json',
			success : function(result) {
				console.log(result);
				showUploadResult(result); //업로드 결과 처리 함수 
				}
			}); //$.ajax	  
		});	
	//업로드 된 부분을 화면단에서 보여지게 하기 위한JS
	 function showUploadResult(uploadResultArr) {

		if (!uploadResultArr || uploadResultArr.length == 0) {
			return;
		}
		var str = "";
		$(uploadResultArr).each(function(i, obj) {
			var fileCallPath = encodeURIComponent(obj.uploadPath+"/"+obj.uuid+"_"+obj.fileName);
			var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
			
			str += "<li style='list-style: none;'"
			str += "data-uuid='"+obj.uuid+"' data-path='"+obj.uploadPath+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"' ><div>";
			str += "<span style='cursor: pointer;' class='download'> " + obj.fileName + "</span>&nbsp;&nbsp;";
			str += "<button type='button' class='deleteBtn' data-file=\'"+fileCallPath+"\' data-type='file'>❌</button><br>";
			str += "</div>";
			str + "</li>";
			
		});
		uploadUL.append(str);
		attachcnt = uploadResultArr.length;
	}
	//업로드된 파일의 데이터를 담을 JS
	function UploadFileAppend(formData){
		$(".uploadResult ul li").each(function(i, obj) {
			var str = "";
			 var jobj = $(obj);

		        var uuid = jobj.data("uuid");
		        var uploadPath = jobj.data("path");
		        var fileName = jobj.data("filename");
		        var fileType = jobj.data("type");

		        // 파일 정보를 FormData에 추가
		        formData.append("attach[" + i + "].uuid", uuid);
		        formData.append("attach[" + i + "].uploadPath", uploadPath);
		        formData.append("attach[" + i + "].fileName", fileName);
		        formData.append("attach[" + i + "].fileType", fileType);
			uploadUL.append(str);
			console.log(str);
		});
	}
	//파일 삭제 js
	$('.uploadResult').on('click','.deleteBtn',function(e){
		var targetFile = $(this).data("file");
		var type = $(this).data("type");
		var targetLi = $(this).closest("li");
		
		$.ajax({
			url: '<c:url value="/deleteFile"/>',
			type: 'post',
			data: {fileName: targetFile, type: type},
			dataType: 'text',
			success: function(response){
				targetLi.remove();
			}
		});
	})
	//다운로드 JS
	$('.uploadResult').on('click','.download',function(e){
		var liObj = $(this).closest("li");
		var path = encodeURIComponent(liObj.data("path")+"/"+liObj.data("uuid")+"_"+liObj.data("filename"));
		self.location='<c:url value="/download"/>'+'?id='+id+'&fileName='+path;
	});
</script>
</body>
</html>