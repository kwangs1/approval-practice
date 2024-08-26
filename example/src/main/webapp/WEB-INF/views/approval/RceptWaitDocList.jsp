<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="<c:url value='/resources/css/loading.css'/>"/>
 <style>
.disable{color:gray;}
.pagination {list-style: none; display: flex;}
.pagination li {margin: 0 5px;}
.pagination a {text-decoration: none; color: #333; padding: 5px 10px; border: 1px solid #ccc; border-radius: 3px;}
.ing{width:30px; height:30px;}
</style>
</head>
<body>
<%@ include file="../approval/apprFrame.jsp" %>
  <br><br>
<div class="cd1">
  <h2>접수대기(${FolderCnt.rceptwaitcnt})</h2> 
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
 <table class="table table-bordered">
    <thead>
      <tr>
      	<th style="width:15px;"><input type="checkbox" id="selectAll"></th>
        <th>제목</th>
        <th>기안자</th>
		<th>발송일시</th>
		<th>발송부서</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach var="list" items="${list}">
      <tr>
      	<td><input type="checkbox" name="appr_seq" class="seq" value="${list.appr_seq }"/></td>
        <td>   
      		<c:if test="${list.appr_seq == orgappr_seq}">
				<img class="ing" src="<c:url value='/resources/img/ing.gif'/>" alt="접수중.."/>&nbsp;&nbsp;
			</c:if>
        	<a href="#" class="apprInfo" data-apprseq="${list.appr_seq}">${list.title }</a>
        </td>
        <td>${list.draftername }</td>
        <td>${list.senddate }</td>
        <td>${list.sendername }</td>
	  </tr>
    </c:forEach>
    </tbody>
  </table>
 <ul class="pagination">
		<c:if test="${pageMaker.prev}">
			<li>
				<a href='<c:url value="RceptWaitDocList?page=${pageMaker.startPage-1}"/>'>◀</a>
			</li>
		</c:if>
		<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="pageNum">
			<li>
				<a href='<c:url value="RceptWaitDocList${pageMaker.makeSearch(pageNum)}"/>'>&nbsp;&nbsp;${pageNum}&nbsp;&nbsp;</a>
			</li>
		</c:forEach>
		<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
			<li>
				<a href='<c:url value="RceptWaitDocList${pageMaker.makeSearch(pageMaker.endPage+1)}"/>'>▶</a>
			</li>
		</c:if>
	</ul>
</div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
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
});
function fncSearch(){
	self.location = "RceptWaitDocList" + "${pageMaker.makeQuery(1)}" + "&searchType=" + $("select option:selected").val() 
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
	var url = '<c:url value="RceptWaitDocList"/>'+ "${pageMaker.makeSearch(1)}";
	window.location.href = url;
}

$('a.apprInfo').on('click', function(event) {
    event.preventDefault();
    var appr_seq = $(this).attr("data-apprseq"); 
    var url = "<c:url value='/approval/apprInfo'/>" + "?appr_seq=" + appr_seq;
    window.open(url, "Info", "width=1024px, height=768px");
});

var checkboxes = document.getElementsByName('appr_seq');
//"전체 선택" 체크박스를 클릭했을 때 모든 체크박스를 선택 또는 해제하는 함수
$('#selectAll').change(function(){
	for(var i = 0; i < checkboxes.length; i++){
	  checkboxes[i].checked = this.checked;
	}
});
</script>
</body>
</html>