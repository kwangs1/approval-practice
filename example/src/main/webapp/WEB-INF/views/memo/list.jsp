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
.pagination {
    list-style: none;
    display: flex;
}

.pagination li {
    margin: 0 5px;
}

.pagination a {
    text-decoration: none;
    color: #333;
    padding: 5px 10px;
    border: 1px solid #ccc;
    border-radius: 3px;
}

.pagination a:hover {
    background-color: #eee;
}
</style>
</head>
<body>
<a href="javascript:write()">메모 작성</a>

<form id="searchForm">
    <select id="searchType" name="searchType">
      <option value="t"<c:out value="${scri.searchType eq 't' ? 'selected' : ''}"/>>제목</option>
      <option value="c"<c:out value="${scri.searchType eq 'c' ? 'selected' : ''}"/>>내용</option>
    </select>
    <input type="text" id="keyword" name="keyword">
    <input type="button" value="검색" onclick="search()" />
    
<select name="perPageNum" id="perPageNum" onchange="loadPage(1)">
	<option value="10"
	<c:if test="${scri.getPerPageNum() == 10 }">selected="selected"</c:if>>10개</option>
	<option value="15"
	<c:if test="${scri.getPerPageNum() == 15 }">selected="selected"</c:if>>15개</option>
	<option value="20"
	<c:if test="${scri.getPerPageNum() == 20 }">selected="selected"</c:if>>20개</option>
</select>
</form>


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
<div id="pagination"></div>

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
        data: $('#searchForm').serialize(),
        success: function(data) {
            var htmls = "";
            
            for (var i in data.List) {
            	var mno = data.List[i].mno;
            	var title = data.List[i].title;
            	var regdate = data.List[i].regdate;
            	
                htmls += "<tr>"
                htmls += "<td class='detail' data-mno='"+ mno +"'>" + title + "</td>"
                htmls += "<td>" + regdate + "</td></tr>";
            }
            $('#ajaxList').html(htmls);
            
            var paginationHtml = "";
                paginationHtml += "<ul class='pagination'>";
                
                if (data.pageMaker.prev) {
                    paginationHtml += "<li><a href='#' onclick='loadPage(" + (data.pageMaker.startPage - 1) + ")'> ◀ </a></li>";
                }
                
                for (var i = data.pageMaker.startPage; i <= data.pageMaker.endPage; i++) {
                    paginationHtml += "<li><a href='#' onclick='loadPage(" + i + ")'><i class='fa'>" + i + "</i></a></li>";
                }
                
                if (data.pageMaker.next && data.pageMaker.endPage > 0) {
                    paginationHtml += "<li><a href='#' onclick='loadPage(" + (data.pageMaker.endPage + 1) + ")'> ▶ </a></li>";
                }
                
                paginationHtml += "</ul>";
            

            $('#pagination').html(paginationHtml);

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

function loadPage(pageNumber){
    var keyword = $('#keyword').val();
    var searchType = $('#searchType').val();
    var perPageNum = $('#perPageNum').val();
    
    $.ajax({
        type: 'get',
        url: '${path}/memo/ajaxList',
        data: {
                page: pageNumber,
                keyword: keyword,
                searchType: searchType,
                perPageNum: perPageNum
            },
        dataType: 'json',
        success: function(data) {
            var htmls = "";
            
            for (var i in data.List) {
            	var mno = data.List[i].mno;
            	var title = data.List[i].title;
            	var regdate = data.List[i].regdate;
            	
                htmls += "<tr>"
                htmls += "<td class='detail' data-mno='"+ mno +"'>" + title + "</td>"
                htmls += "<td>" + regdate + "</td></tr>";
            }
            $('#ajaxList').html(htmls);
            
            var paginationHtml = "";
                paginationHtml += "<ul class='pagination'>";
                
                if (data.pageMaker.prev) {
                    paginationHtml += "<li><a href='#' onclick='loadPage(" + (data.pageMaker.startPage - 1) + ")'> ◀ </a></li>";
                }
                
                for (var i = data.pageMaker.startPage; i <= data.pageMaker.endPage; i++) {
                    paginationHtml += "<li><a href='#' onclick='loadPage(" + i + ")'><i class='fa'>" + i + "</i></a></li>";
                }
                
                if (data.pageMaker.next && data.pageMaker.endPage > 0) {
                    paginationHtml += "<li><a href='#' onclick='loadPage(" + (data.pageMaker.endPage + 1) + ")'> ▶ </a></li>";
                }
                
                paginationHtml += "</ul>";
            

            $('#pagination').html(paginationHtml);

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

function search() {
    ajaxList(); 
}
</script>
</body>
</html>
