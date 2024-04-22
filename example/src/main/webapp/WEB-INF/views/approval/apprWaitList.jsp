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
 <style>
.disable{color:gray;}
.pagination {list-style: none; display: flex;}
.pagination li {margin: 0 5px;}
.pagination a {text-decoration: none; color: #333; padding: 5px 10px; border: 1px solid #ccc; border-radius: 3px;}
</style>
</head>
<body>
<%@ include file="../approval/apprFrame.jsp" %>
  <br><br>
<div class="cd1">
  <h2>결재대기</h2> 
  <a href="javascript:BundleApproval()">일괄결재</a>
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
      	<th style="width:10px;"><input type="checkbox" id="selectAll"></th>
        <th>제목</th>
        <th>기안자</th>
        <th>기안날짜</th>
        <th>처리구분</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach var="list" items="${list}">
      <tr>
      	<td><input type="checkbox" name="appr_seq" class="seq" value="${list.appr_seq }"/></td>
        <td><a href="#" class="apprInfo" data-apprseq="${list.appr_seq}">${list.title }</a></td>
        <td>${list.draftername }</td>
        <td>${list.regdate }</td>
		<td>  
	        <%-- 결재대기에 걸린 결재선 정보 가져오려고 --%>
	  <c:forEach var="participant" items="${participantInfo}" varStatus="loop">
		<c:if test="${participant.approvaltype == 4 && participant.approvalstatus == 4098}">
			<input type="hidden" id="appr_seq" value="${participant.appr_seq}" />
			<input type="hidden" id="participant_seq_${loop.index }" value="${participant.participant_seq}"/>
			<input type="hidden" id="signerid" value="${participant.signerid}"/>
			<input type="hidden" id="approvalstatus" value="${participant.approvalstatus}"/>
			<input type="hidden" id="approvaltype" value="${participant.approvaltype}"/>
		</c:if> 
		<c:if test="${participant.approvaltype == 4 && list.appr_seq eq participant.appr_seq}">
			${participant.statusname}
		</c:if>	
	  </c:forEach>
	   </td>
	  </tr>
    </c:forEach>
    </tbody>
  </table>
 <ul class="pagination">
		<c:if test="${pageMaker.prev}">
			<li>
				<a href='<c:url value="apprWaitList?page=${pageMaker.startPage-1}"/>'>◀</a>
			</li>
		</c:if>
		<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="pageNum">
			<li>
				<a href='<c:url value="apprWaitList${pageMaker.makeSearch(pageNum)}"/>'>&nbsp;&nbsp;${pageNum}&nbsp;&nbsp;</a>
			</li>
		</c:forEach>
		<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
			<li>
				<a href='<c:url value="apprWaitList${pageMaker.makeSearch(pageMaker.endPage+1)}"/>'>▶</a>
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
	self.location = "apprWaitList" + "${pageMaker.makeQuery(1)}" + "&searchType=" + $("select option:selected").val() 
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
	var url = '<c:url value="apprWaitList"/>'+ "${pageMaker.makeSearch(1)}";
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

//일괄결재
function BundleApproval(){
	var ary = []; // 결재 시퀀스 배열
	<%-- 결재 대기에 걸린 결재선 정보--%>
	var signerid = $('#signerid').val();
	var approvalstatus = $('#approvalstatus').val();
	var approvaltype = $('#approvaltype').val();
	var selectParticipant = []; //전제 값 배열
	
	for(var i = 0; i < checkboxes.length; i++){
		if(checkboxes[i].checked){
			var appr_seq = checkboxes[i].value;
			//결재선 시퀀스 id중복으로 인하여 값을 제대로 가져오지 못하여 _ +i 로 구분지어 가져옴
			var participant_seq = $('#participant_seq_' + i).val();
			console.log(participant_seq);
			
			ary.push(appr_seq);
			selectParticipant.push({
				appr_seq: appr_seq,
				participant_seq: participant_seq,
				signerid: signerid,
				approvalstatus: approvalstatus,
				approvaltype: approvaltype
			});
		}
	}
	
	
	if(ary.length == 0 || ary.length == null){
		alert("선택된 문서가 존재하지 않습니다.");
		return;
	}
		console.log("일괄 결재 데이터 : "+ JSON.stringify(selectParticipant));
		
		$.ajax({
			type: 'post',
			url: '<c:url value="/participant/BulkAppr"/>',
			contentType: 'application/json',
			data: JSON.stringify(selectParticipant),
			success: function(response){
				console.log("일괄 결재 ajax 요청 데이터 ",response);
				alert("결재가 완료되었습니다.");
				window.location.reload();
			},
			error : function(xhr,error,status){
				alert("결재에 실패하였습니다.");
				console.log(xhr);
				console.log(error);
				console.log(status);
			}
		}); //end ajax
	}
</script>
</body>
</html>