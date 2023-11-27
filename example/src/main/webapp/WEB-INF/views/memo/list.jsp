<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
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
	<tbody id="list">
	
	</tbody>
</table>

<script>
function write() {
    window.open("${path}/memo/write", "memo", "width=1024, height=768, left=500");
}

$('document').ready(function(){
    list();
});

function list() {
    $.ajax({
        type: 'get',
        url: '${path}/memo/list',
        dataType: 'json',
        success: function(res) {
        	const data = res['list'];
            var htmls = "";
            
            for (const i in data) {
                let title = data[i].title;
                let regdate = data[i].regdate;

                htmls += "<tr><td>" + title + "</td><td>" + regdate + "</td></tr>";
            }
            $('#list').html(htmls);
           console.log('success');
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
