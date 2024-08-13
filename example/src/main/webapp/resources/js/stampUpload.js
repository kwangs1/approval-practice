/**
 * 관인 업로드 스크립트
 */

var regex = /\.(jpg|jpeg|gif|bmp|png)$/i;
var maxSize = 5242880;
function checkException(fileName, fileSize){
	if(fileSize >= maxSize){
		alert("파일 용량이 5MB를 초과하였습니다.");
		$('uploadFile').val('');
		return false;
	}
	if(!regex.test(fileName)){
		alert("이미지파일 외 파일은 업로드가 되지 않습니다.");
		$('uploadFile').val('');
		return false;
	}
	return true;
}

var uploadedFiles = [];
$('input[type="file"]').change(function(e){
	var formData = new FormData();	
	var inputFile = $('input[name="uploadFile"]');
	var files = inputFile[0].files;
	var selectedDeptId = $('a.deptLink.active').data('deptid');
	
	for(var i=0; i<files.length; i++){
		if(!checkException(files[i].name, files[i].size)){
			return false;
		}
		if(files.length > 1){
			alert("파일은 하나만 업로드 해주세요!");
			return false;
		}
		formData.append("uploadFile",files[i]);
	}
	formData.append('id',selectedDeptId);

	for(let [key,value] of formData.entries()){
		console.log('1.formData key? value? '+`${key} : ${value}`);
	}
	$.ajax({
		url: "/kwangs/stampUpload",
		processData: false,
		contentType: false,
		data: formData,
		type: 'post',
		dataType: 'json',
		success:function(result){
			console.log("fileupload Ajax Success "+result);
			console.log(result);
			showUploadResult(result);
			for(var i =0; i < result.length; i++){
				uploadedFiles.push({id: result[i].id, name: result[i].name});
				console.log(uploadedFiles)
			}
		}
	})
});

function showUploadResult(uploadResultArr){
	if(!uploadResultArr || uploadResultArr.length == 0){return;}
	
	var uploadUL = $('.uploadResult ul');
	var str = '';
	for(var i=0; i< uploadResultArr.length; i++){
		console.log("Upload Result Array: "+uploadResultArr[i].id);
	}
	
	$(uploadResultArr).each(function(i, obj){
		console.log("Object: "+obj.id);
		if(obj.type === 0){
			var localName = obj.id+"."+obj.name;
			var fileCallPath = encodeURIComponent("s_" + localName);
			str += "<li style='list-style: none;' data-deptid='"+obj.id+"' data-name='"+obj.name+"' data-type='"+obj.type+"'><div>";
			str += "<img src='/kwangs/display?name="+fileCallPath+"'>";
		    str += "<button onClick='deleteFile(\""+obj.id+"\",\""+obj.name+"\")'>X</button>";
			str += "</div></li>"
		}
	})
	uploadUL.append(str);
}

//저장 안하고 창 닫을 시 로컬경로에 업로드 된 파일 삭제 
var isDeleting =false;
var isSubmitted = false;
window.onbeforeunload = function(e){
	if(isSubmitted) return;
	if(uploadedFiles.length === 0 || isDeleting){
		return;
	}
	//var confirmationMessage = '페이지를 나가시겠습니까? 파일이 삭제될 수 있습니다.';

	//setTimeout(function(){
		for(var i=0; i < uploadedFiles.length; i ++){
			var name = uploadedFiles[i].name;
			var id = uploadedFiles[i].id;
		
			name=id+"."+name;
			$.ajax({
				url: '/kwangs/stamp/deleteFile',
				type: 'post',
				data: {name: name},
				success: function(response){
					console.log(response);
					console.log("File deleted: "+name);
				},
				error:function(xhr, status, error){
					console.log("Error deleting file: "+name);
				}
			});
		}		
	//},100);
	//e.returnValue = confirmationMessage;
	//return confirmationMessage;
	
}
//버튼 클릭 삭제
function deleteFile(id,name){
	console.log("delete file");
	
	$.ajax({
		url: '/kwangs/stamp/deleteFile',
		type: 'post',
		data: {name: name},
		success: function(result){
			$('li[data-name="'+name+'"]').remove();
			uploadedFiles.splice(0);
			window.location.reload();
		},
		error: function(xhr,status,error){
			console.log(xhr);
			console.log(error);
		}
	})
};