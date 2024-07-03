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
  <h2>결재진행(${FolderCnt.appringcnt})</h2>
  <a href="javascript:RetireAppr()">회수</a> |
  <a href="javascript:Resubmission_Pop()">재기안</a> |
  <a href="javascript:DeleteDocument()">삭제</a>
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
        <th>기안일시</th>
        <th>처리현황</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach var="list" items="${list}" varStatus="loop">
    <input type="hidden" name="drafterid" id="drafterid" value="${list.drafterid}" />
    <input type="hidden" id="ApprStatus_${loop.index}" value="${list.status}" />
	        <%-- 결재대기에 걸린 결재선 정보 가져오려고 --%>
	  <c:forEach var="participant" items="${participantInfo}" varStatus="loop">
		<c:if test="${participant.appr_seq == list.appr_seq && participant.signerid == user.id}">
			<input type="hidden" id="appr_seq_${loop.index }" value="${participant.appr_seq}" />
			<input type="hidden" id="participant_seq_${loop.index }" value="${participant.participant_seq}"/>
			<input type="hidden" id="signerid_${loop.index }" value="${participant.signerid}"/>
			<input type="hidden" id="approvalstatus_${loop.index }" value="${participant.approvalstatus}"/>
			<input type="hidden" id="approvaltype_${loop.index }" value="${participant.approvaltype}"/>
			<input type="hidden" id="deptid_${loop.index }" value="${participant.deptid}"/>
			<input type="hidden" id="status_${loop.index }" value="${participant.status}"/>
		</c:if> 
	  </c:forEach>
	      <tr>
	      	<td><input type="checkbox" name="appr_seq" class="seq" value="${list.appr_seq }" /></td>
	        <td><a href="#" class="apprInfo" data-apprseq="${list.appr_seq}">${list.title }</a></td>
	        <td>${list.draftername}</td>
	        <td>${list.regdate }</td>
			<c:choose>
				<c:when test="${list.status == 4096}">
					<td>회수</td>
				</c:when>
				<c:otherwise>
					<td>진행</td>
				</c:otherwise>
			</c:choose>
	      </tr>    	  	
    </c:forEach>
    </tbody>
  </table>
 <ul class="pagination">
		<c:if test="${pageMaker.prev}">
			<li>
				<a href='<c:url value="SanctnProgrsList?page=${pageMaker.startPage-1}"/>'>◀</a>
			</li>
		</c:if>
		<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="pageNum">
			<li>
				<a href='<c:url value="SanctnProgrsList${pageMaker.makeSearch(pageNum)}"/>'>&nbsp;&nbsp;${pageNum}&nbsp;&nbsp;</a>
			</li>
		</c:forEach>
		<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
			<li>
				<a href='<c:url value="SanctnProgrsList${pageMaker.makeSearch(pageMaker.endPage+1)}"/>'>▶</a>
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
	self.location = "SanctnProgrsList" + "${pageMaker.makeQuery(1)}" + "&searchType=" + $("select option:selected").val() 
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
	var url = '<c:url value="SanctnProgrsList"/>'+ "${pageMaker.makeSearch(1)}";
	window.location.href = url;
}

var checkboxes = document.getElementsByName('appr_seq');
<%-- 결재 진행에 걸린 결재선 정보
var signerid = $('#signerid').val();
var approvalstatus = $('#approvalstatus').val();
var approvaltype = $('#approvaltype').val();
var status = $('#status').val();
var drafterid = $('#drafterid').val();--%>
var deptid = $('#deptid').val();
var userid = '<c:out value="${user.id}"/>';
//"전체 선택" 체크박스를 클릭했을 때 모든 체크박스를 선택 또는 해제하는 함수
$('#selectAll').change(function(){
	for(var i = 0; i < checkboxes.length; i++){
	  checkboxes[i].checked = this.checked;
	}
});

//회수
function RetireAppr(){
	var RetireDoc = [];
	
	for(var i=0; i<checkboxes.length; i++){
		if(checkboxes[i].checked){            
			var appr_seq = checkboxes[i].value;
			var participant_seq = $('#participant_seq_' + i).val();
			var signerid = $('#signerid_' + i).val();
			var deptid = $('#deptid_' + i).val();
			var status = $('#status_' + i).val();
			
			var approvalStatus = $('#ApprStatus_'+i).val();
			var drafterid = $('#drafterid').val();
			
			if(drafterid !== userid){
				alert("문서의 기안자만 회수가 가능합니다");
				return;
			}else if(approvalStatus == 4096){
				alert("이미 회수처리가 된 문서입니다.");
				return;
			}
			RetireDoc.push({
				appr_seq : appr_seq,
				participant_seq : participant_seq,
				signerid : signerid,
				deptid : deptid,
				status : status
			});
		}
	}
	
	if(RetireDoc == 0 || RetireDoc.length === 0){
		alert("선택된 문서가 없습니다.");
		return;
	}else {
		$.ajax({
			type:'post',
			url: '<c:url value="/participant/RetireAppr"/>',
			contentType: 'application/json',
			data: JSON.stringify(RetireDoc),
			success: function(response){
				console.log(response);
				console.log(JSON.stringify(RetireDoc));
				alert("회수 하였습니다.");
				window.location.reload();
			},
			error: function(error){
				alert("회수 하는 과정에서 오류가 발생하였습니다.");
				console.log(error);
			}
		})
	}
}
function Resubmission_Pop(){
	var Resubmission_Ary= [];
	
	for(var i=0; i < checkboxes.length; i++){
		if(checkboxes[i].checked){
			var appr_seq = checkboxes[i].value;
			//문서 상태값
			var ApprStatus = $('#ApprStatus_' + i).val();
			Resubmission_Ary.push({appr_seq : appr_seq});
		}
	}
	
	if(Resubmission_Ary == 0 || Resubmission_Ary.length == null){
		alert("재기안 할 문서를 선택하여주세요.");
		return
	}else if(Resubmission_Ary.length > 1){
		alert("하나의 문서만 선택해주세요.");
		return;
	}else if(ApprStatus != 4096){
		alert("회수한 문서만 재기안이 가능합니다.");
		return;
	}
	url = '<c:url value="/approval/Resubmission"/>';
	url += "?appr_seq="+appr_seq;
	
	window.open(url,"Resubmission","width=1024px, height=768px");
}

$('a.apprInfo').on('click', function(event) {
    event.preventDefault();
    
    var appr_seq = $(this).attr("data-apprseq"); 
    var url = "<c:url value='/approval/apprInfo'/>" + "?appr_seq=" + appr_seq;
    window.open(url, "Info", "width=1024px, height=768px");
});

function DeleteDocument(){
	var ary = [];
	for(var i=0; i < checkboxes.length; i++){
		var appr_seq = checkboxes[i].value;
		var ApprStatus = $('#ApprStatus_'+i).val();
		
		ary.push({appr_seq: appr_seq});
		if(ApprStatus != 4096){
			alert('회수된 문서만 삭제가 가능합니다.');
			return;
		}
	}
	
	if(ary == 0 || ary.length == null){
		alert('선택된 문서가 없습니다.');
		return;
	}else{
		$.ajax({
			type: 'post',
			url: '<c:url value="/approval/DeleteDoc"/>',
			contentType: 'application/json',
			data: JSON.stringify(ary),
			success: function(response){
				console.log(response);
				alert("해당 문서가 삭제되었습니다");
				window.location.reload();
			},
			error: function(xhr,status){
				console.log(xhr);
				console.log(status);
			}
		});			
	}
}
</script>
</body>
</html>