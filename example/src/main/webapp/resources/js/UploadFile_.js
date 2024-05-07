/**
 *  기안 상신 이전 업로드&등록&수정&삭제 [첨부파일]
 */
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
			url : "/kwangs/uploadFile",
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
			var fileCallPath = encodeURIComponent("/"+obj.uuid+"_"+obj.fileName);
			var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
			
			str += "<li style='list-style: none;'"
			str += "data-uuid='"+obj.uuid+"' data-path='"+obj.uploadPath+"' data-filename='"+obj.fileName+"' data-type='"+obj.fileType+"' data-size='"+obj.filesize+"' ><div>";
			str += "<span style='cursor: pointer;' class='download'> " + obj.fileName + "</span>&nbsp;&nbsp;";
			str += "<button type='button' class='deleteBtn' data-file=\'"+fileCallPath+"\' data-type='file'>❌</button><br>";
			str += "</div>";
			str + "</li>";
			
		});
		uploadUL.append(str);
	}
	//업로드된 파일의 데이터를 담을 JS
	function UploadFileAppend(formData){
		var attachcnt = 0;
		$(".uploadResult ul li").each(function(i, obj) {
			 var jobj = $(obj);
			 var uuid = jobj.data("uuid");
		     var uploadPath = jobj.data("path");
		     var fileName = jobj.data("filename");
		     var fileType = jobj.data("type");
		     var filesize = jobj.data("size");

		     // 파일 정보를 FormData에 추가
		     formData.append("attach[" + i + "].uuid", uuid);
		     formData.append("attach[" + i + "].uploadPath", uploadPath);
		     formData.append("attach[" + i + "].fileName", fileName);
		     formData.append("attach[" + i + "].fileType", fileType);
		     formData.append("attach[" + i + "].filesize", filesize);
		     attachcnt++;
		});
		formData.append("attachcnt",attachcnt);
	}
	//파일 삭제 js
	$('.uploadResult').on('click','.deleteBtn',function(e){
		var targetFile = $(this).data("file");
		var type = $(this).data("type");
		var targetLi = $(this).closest("li");
		
		$.ajax({
			url: "/kwangs/deleteFile",
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
		var path = encodeURIComponent("/"+liObj.data("uuid")+"_"+liObj.data("filename"));
		self.location="/kwangs/download"+'?fileName='+path;
	});