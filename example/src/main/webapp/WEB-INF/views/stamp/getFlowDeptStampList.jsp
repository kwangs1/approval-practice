<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<button id="sendBtn">확인</button><br>
<c:forEach var="stamp" items="${stamp}">
	<a href="javascript:void(0)" class="stamp" data-name="${stamp.name}" data-id="${stamp.id}">${stamp.name}</a><br>
</c:forEach>

<div class="stampFileList"></div>


<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
$(document).ready(function(){
	getAttachList();
});

var lastClickedName = null;
function getAttachList(){
	$.getJSON("/kwangs/stamp/getAttachList",function(arr){
		$('a.stamp').off('click').on('click',function(){
			var name = $(this).data('name');
			
			if(lastClickedName === name){
				$('.stampFileList').html('');
				lastClickedName = null
				return;
			}
			lastClickedName = name;
			console.log(lastClickedName);
			
			var str = "";
			$(arr).each(function(i, obj){	
				if(name === obj.name){
					var localName = obj.id+"."+obj.name;
					var fileCallPath = encodeURIComponent("s_" + localName);
					str += "<li style='list-style: none;'"
					str += "data-deptid='"+obj.id+"' data-fno='"+obj.fno+"' data-name='"+obj.name+"' data-type='"+obj.type+"'>";
					str += "<img src='/kwangs/display?name="+fileCallPath+"'>";
					str += "</div></li>";	
				}
			});//end each
			$('.stampFileList').html(str);
		});
	});
}

//전송부분..
var selectedData = null; //선택된 데이터 저장 변수
function initialize(){
	var stamp = document.querySelectorAll('.stamp'); //.stamp 요소에 클릭이벤트 리스너 추가 [body에서 for문을 통해 나온 값을 배열에 담는다.]
	for(var i=0; i <stamp.length; i++){
		stamp[i].addEventListener('click',function(){
			selectedData = {id :this.getAttribute('data-id') , name :this.getAttribute('data-name')};
			console.log("selectedData? "+selectedData.id+", "+selectedData.name);
		})
	}
	//버튼 클릭 시 데이터 전송.
	document.getElementById("sendBtn").addEventListener('click',function(){
		if(selectedData){
			window.opener.postMessage(selectedData,"*");
			window.close();
		}else{
			alert("No data Selected.");
			window.close();
		}		
	});
}
//페이지 완전히 로드 된 후 함수 호출.
document.addEventListener('DOMContentLoaded',initialize);
</script>
</body>
</html>