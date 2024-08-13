<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<c:forEach var="stamp" items="${stamp}">
	<a href="javascript:void(0)" class="stamp" data-name="${stamp.name}">${stamp.name}</a><br>
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
					str += "<img src='/kwangs/display.?name="+fileCallPath+"'>";
					str += "</div></li>";	
				}
			});//end each
			$('.stampFileList').html(str);
		});
	});
}
</script>
</body>
</html>