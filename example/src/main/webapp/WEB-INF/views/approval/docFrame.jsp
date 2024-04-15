<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<link rel="stylesheet" href="<c:url value='/resources/css/docfldrSidebar.css'/>"/>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<style>
.disable{color:gray;}
.pagination {list-style: none; display: flex;}
.pagination li {margin: 0 5px;}
.pagination a {text-decoration: none; color: #333; padding: 5px 10px; border: 1px solid #ccc; border-radius: 3px;}
</style>
<body>
<%@ include file="../common/apprMenu.jsp" %><br><br>

<ul class="tree">
    <li>
      <a href="/kwangs">홈으로</a>
    </li>
  <c:forEach var="item" items="${docfldrSidebar}">
  <c:choose>
  	<c:when test="${item.applid != 8010 && item.applid != 7020}">
    <li>
    	<p class="disable">${item.fldrname}</p>
    </li>  		
  	</c:when>
  	<c:otherwise>
  	 <li>
       <a href="#" data-fldrid = "${item.fldrid}"
      	onClick ="loadDocFrame('${user.deptid}','${item.ownerid}','${item.fldrid}','${item.fldrname}','${item.applid}')">
      ${item.fldrname}</a>
    </li>	
  	</c:otherwise>
  </c:choose>
  </c:forEach>
</ul>
<%-- 검색 --%>
<div class="search" align="center">
    <select id="searchType" name="searchType">
		<option value="t"<c:out value="${scri.searchType eq 't' ? 'selected' : ''}"/>>제목</option>
		<option value="d"<c:out value="${scri.searchType eq 'd' ? 'selected' : ''}"/>>문서번호</option>
		<option value="u"<c:out value="${scri.searchType eq 'u' ? 'selected' : ''}"/>>기안자</option>
    </select>
	<input type="text" name="keyword" id="keywordInput" value="${scri.keyword}" 
		onkeydown="javascript:if (event.keyCode == 13) {fncSearch();}"/>
	<button onclick="fncSearch()" type="button">검색</button>
   
	<select name="perPageNum" id="perPageNum" onchange="loadPage(1)">
	 <option value="10"
	 <c:if test="${scri.getPerPageNum() == 10 }">selected="selected"</c:if>>10개</option>
	 <option value="15"
	 <c:if test="${scri.getPerPageNum() == 15 }">selected="selected"</c:if>>15개</option>
	 <option value="20"
	 <c:if test="${scri.getPerPageNum() == 20 }">selected="selected"</c:if>>20개</option>
	</select>
</div>
<div class="cd1">
<h1 id="title_txt"><c:out value="${fldrname}"/></h1>
<table class="table table-bordered">
	<thead>
	<tr>
		<th><input type="checkbox" id="selectAll"/></th>
		<th>문서번호</th>
		<th>제목</th>
		<th>기안자</th>
		<th>결재자</th>
		<th>결재완료일자</th>
	</tr>
	</thead>
	<tbody id="docListBody">
		<c:forEach var="item" items="${docframe}">
		<tr>
			<td><input type="checkbox" name="appr_seq" class="seq" id="appr_seq" value="${item.appr_seq}" />
			&nbsp;&nbsp;<a href="#" data-apprseq="${item.appr_seq}" class="Simpleinfo">🔍</a></td>
			<td>${item.docregno}</td>
			<td><a href="#" data-apprseq="${item.appr_seq}" class="Docinfo">${item.title}</a></td>
			<td>${item.draftername}</td>
			<td>${item.finalapprover}</td>
			<td>${item.approvaldate}</td>
		</tr>		
		</c:forEach>
	</tbody>
</table>
	<ul class="pagination">
		<c:if test="${pageMaker.prev}">
			<li>
				<a href='<c:url value="docFrame?page=${pageMaker.startPage-1}"/>'>◀</a>
			</li>
		</c:if>
		<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="pageNum">
			<li>
				<a href='<c:url value="docFrame${pageMaker.makeSearch(pageNum)}"/>'>&nbsp;&nbsp;${pageNum}&nbsp;&nbsp;</a>
			</li>
		</c:forEach>
		<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
			<li>
				<a href='<c:url value="docFrame${pageMaker.makeSearch(pageMaker.endPage+1)}"/>'>▶</a>
			</li>
		</c:if>
	</ul>
</div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="<c:url value='/resources/js/pagingCookie.js'/>"></script>
<script>
$(document).ready(function(){
	//쿠키 값 가져오기..
	var getPerPageNum = getCookie("perPageNum");
	if(getPerPageNum !== null){
		$('#perPageNum').val(getPerPageNum);
	}
	
	//검색어 및 타입 값 검색 시 초기화 안되도록... 
	var urlParams = new URLSearchParams(window.location.search);
	var searchType = urlParams.get('searchType');
	var keyword = urlParams.get('keyword');
	
	if(searchType == null && keyword == null){
		$('#keywordInput').val('');
		$('#searchType').val("t");
	}else{
		$('#keywordInput').val(decodeURIComponent(keyword));
		$('#searchType').val(searchType);		
	}
	//사이드 메뉴 클릭 시 글자색 변경 고정.
	var clickFldrId = urlParams.get('fldrid');
	$('.tree li a').each(function(){
		var fldrid = $(this).data('fldrid');
		if(clickFldrId === fldrid){
			$(this).css('color','green');
		}
	})
});

function fncSearch(){
	self.location = "docFrame" + "${pageMaker.makeQuery(1)}" + "&searchType=" + $("select option:selected").val() 
	+ "&keyword=" + encodeURIComponent($('#keywordInput').val());
}

$('#perPageNum').change(function(){
	var selectedVal = $(this).val();
	saveCookie(selectedVal);
})
function loadPage(pageNum){
	var searchType = $('#searchType').val();
	var keyword = $('#keywordInput').val();
	var perPageNum = $('#perPageNum').val();

	var url = '<c:url value="docFrame"/>'+ "${pageMaker.makeLoadPage(1)}";
	
	if(searchType !== ''){
		url += '&searchType=' + searchType;
	}
	if(keyword !== ''){
		url += '&keyword=' +keyword;
	}
	url += '&perPageNum=' + perPageNum;
	
	window.location.href = url;
}

$('a.Docinfo').on('click', function(event) {
    event.preventDefault();
    
    var appr_seq = $(this).attr("data-apprseq"); 
    var url = "<c:url value='/approval/apprInfo'/>" + "?appr_seq=" + appr_seq;
    window.open(url, "Info", "width=1024px, height=768px");
});

$('#selectAll').change(function(){
	var checkboxes = document.getElementsByName("apprid");
	for(var i =0; i < checkboxes.length; i++){
		checkboxes[i].checked = this.checked;
	}
})
function loadDocFrame(drafterdeptid,ownerid,fldrid,fldrname,applid){
	var url = '<c:url value="docFrame"/>'
	url += '?drafterdeptid='+drafterdeptid+'&ownerid='+ownerid+'&fldrid='+fldrid+'&fldrname='+fldrname+'&applid='+applid
			
	window.location.href = url;
	setCookie_f(url);
}
</script>
</body>
</html>