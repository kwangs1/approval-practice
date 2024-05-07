/**
 * 기안 상신 이후 업로드&등록&수정&삭제 [첨부파일]
 */
//첨부파일 다운로드
$(".uploadFileList").on('click','.files',function(e){
	var liObj = $(this).closest("li");
	var path = encodeURIComponent("/"+liObj.data("uuid")+"_"+liObj.data("filename"));
	self.location="/kwangs/InfoFileDownload"+'?appr_seq='+appr_seq+'&fileName='+path;
})
//파일삭제
function ApprDocDeleteFiles(){
	if(confirm("삭제하시겠습니까?")){
		$('.uploadFileList').on('click','.delete',function(){
			var liObj = $(this).closest("li");
			var targetFile = encodeURIComponent("/"+liObj.data("uuid")+"_"+liObj.data("filename"));
			var type = $(this).data("type");
			var fileName = liObj.data('filename');	
			var path = liObj.data('path');
			
			$.ajax({
				url: "/kwangs/ApprDocDeleteFiles",
				type: 'post',
				data: {fileName :fileName, appr_seq :appr_seq},
				dataType: 'text',
				success: function(response){
					alert("파일이 삭제가 되었습니다.");
					$.ajax({
						url: "/kwangs/InfoDeleteFile",
						type: 'post',
						data: {fileName: targetFile, type: type, appr_seq: appr_seq, uploadPath: path},
						dataType: 'text',
						success: function(response){
							liObj.remove();
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
	formData.append("appr_seq",appr_seq);
	formData.append("uploadPath",$('#path').val());
	$.ajax({
		url: "/kwangs/InfoUploadFile",
		processData: false,
		contentType: false,
		data: formData,
		type: 'post',
		dataType: 'json',
		success: function(result){
			window.opener.location.reload()
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
	    var fileCallPath =  encodeURIComponent("/"+ obj.uuid +"_"+obj.fileName);         
	    var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
		str += "<li data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.fileType+"'  data-size='"+obj.filesize+"'>";
		str += "<div><span style='cursor:pointer;' class='download'>" + obj.fileName + "</span>&nbsp;&nbsp;";
		str += "<button type='button' class='delete' onclick='ApprDocDeleteFiles()'>❌</button><br>";
		str += "</div></li>";
	})
	uploadUL.append(str);
}
function ApprDocInsertFiles(formData){
	//showUploadResult 함수를 통해 첨부파일 추가된 부분에 대해 반복문을 통해 넘길데이터 설정
	var attachcnt = 0;
	$('.uploadResult ul li').each(function(i,obj){
		var obj_ = $(obj);
		
		var uuid = obj_.data("uuid");
		var uploadPath = obj_.data("path");
		var fileName = obj_.data("filename");
		var fileType = obj_.data("type");
		var filesize= obj_.data("size");
		
		formData.append("attach["+ i +"].uuid",uuid);
		formData.append("attach["+ i +"].uploadPath",uploadPath);
		formData.append("attach["+ i +"].fileName",fileName);
		formData.append("attach["+ i +"].fileType",fileType);
		formData.append("attach["+ i +"].filesize",filesize);
		attachcnt++;
	})
    //formData.append("appr_seq", appr_seq);
	formData.append("attachcnt", attachcnt);
	$.ajax({
		type:'post',
		url: "/kwangs/ApprDocInsertFiles",
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
/*
* 상세보기 부분
*/
//해당 문서에 대한 업로드한 첨부파일 리스트
function getAttachList(){
	$.getJSON("/kwangs/getAttachList",{appr_seq: appr_seq}, function(arr){
		var str = "";
		
		$(arr).each(function(i,attach){
			str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"' data-size='"+attach.filesize+"'>";
			str += "<div><span style='cursor: pointer;'>" + attach.fileName + "</span>";
			str += "</div></li><br>";
		})
		$('.InfoUploadResult ul').html(str);
	});	
}
//첨부파일 다운로드
$('.InfoUploadResult ul').on('click','li',function(e){
	var liObj = $(this);
	var path = encodeURIComponent("/"+liObj.data("uuid")+"_"+liObj.data("filename"));
	self.location="/kwangs/InfoFileDownload"+'?appr_seq='+appr_seq +'&fileName='+path;
});