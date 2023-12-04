<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
.detail{
	cursor:pointer;
}
</style>
</head>
<body>
<a href="javascript:write()">메모 작성</a>

<table>
	<thead>
		<tr>
			<th>제목</th>
			<th>날짜</th>
		</tr>
	</thead>
	<tbody id="ajaxList">
	
	</tbody>
</table>


<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
function write() {
    window.open("${path}/memo/write", "memo", "width=1024, height=768, left=500");
}

$(document).ready(function(){
	ajaxList();
});

function ajaxList() {
    $.ajax({
        type: 'get',
        url: '${path}/memo/ajaxList',
        dataType: 'json',
        success: function(data) {
        	console.log(data);
            var htmls = "";
            
            for (var i in data) {
            	var mno = data[i].mno;
            	var title = data[i].title;
            	var regdate = data[i].regdate;
            	
                htmls += "<tr>"
                htmls += "<td class='detail' data-mno='"+ mno +"'>" + title + "</td>"
                htmls += "<td>" + regdate + "</td></tr>";
            }
            $('#ajaxList').html(htmls);

            $('.detail').click(function(){
            	var mno = $(this).data('mno');
            	window.open('${path}/memo/read?mno='+mno,'read','width=1024, height=768, left=500')
            })
        },
        error: function(xhr, status, error) {
            console.log(xhr);
            console.log(status);
            console.log(error);
        }
    });
}
</script>
</body>
</html>
