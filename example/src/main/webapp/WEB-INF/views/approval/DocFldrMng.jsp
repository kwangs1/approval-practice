<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<link rel="stylesheet" href="<c:url value='/resources/css/docfldrSidebar.css'/>"/>
<link rel="stylesheet" href="<c:url value='/resources/css/DocFldrMng.css'/>"/>
<link rel="stylesheet" href="<c:url value='/resources/css/btn.css'/>"/>
<body>
<ul class="tree">
    <li>
      <a href="/kwangs">홈으로</a>
    </li>
  <c:forEach var="item" items="${docfldrSidebar}">
  <c:choose>
     <c:when test="${item.applid != 7020 && item.applid != 7040 && item.applid != 7010 && item.applid != 8010}">
   	 <li>
    	<p class="disable">${item.fldrname}</p>
    </li> 	
  	</c:when>
  	
  	<c:when test="${item.applid == 7020}">
	    <li>
	      <a href="#" data-fldrid = "${item.fldrid}" data-applid = "${item.applid}"
	      	onClick ="loadDocFrame('${item.fldrid}','${currUser.deptid}')">
	     &nbsp;&nbsp;&nbsp;${item.fldrname}</a>
	    </li>  		 		
  	</c:when>
  	
  	<c:otherwise>
    <li>
      <a href="#" data-fldrid = "${item.fldrid}" data-applid = "${item.applid}"
      	onClick ="loadDocFrame('${item.fldrid}','${currUser.deptid}')">
      ${item.fldrname}</a>
    </li>  		
  	</c:otherwise>
  </c:choose>
  </c:forEach>
</ul>

<div class="cd1">
<%@ include file="../common/FldrTabMenu.jsp" %>
<br>
	<header>
		<h2>단위과제관리(${Info.fldrname})
			<a href="javascript:loadDocFrameList()" style="text-decoration: none;">🔅</a>
		</h2>
	</header>
	
	<main>	 
	<c:if test="${Info.applid == 7010}">
		<button class="button" onclick="apprfolderIn_pop();">기록물철 추가</button>
	</c:if>
	<c:if test="${Info.applid == 7020}">
		<button class="button" onclick="FldrEdit();">변경</button>
	</c:if>
	<button class="button" onclick="MoveApprFldr();">이동</button>
	
	<c:if test="${Info.procstatus eq '0'}">
		<button class="button" onclick="FldrTransferYearEdit()">편철확인</button>
	</c:if>
	
	<c:if test="${Info.procstatus eq '1'}">
		<button class="button" onclick="CancelFldrStatus()">편철확인상태취소</button>
	</c:if>
	<br><br>
	     <section class="record-details">
	         <h2>기록물 제목</h2>
	         <p>${Info.fldrname}</p>
	     </section>
	     <section class="additional-info">
	         <h3>추가 정보</h3>
	         <ul>
	             <li>기록물철ID: ${Info.fldrid}</li>
	         <%-- --%>  
	         <c:if test="${Info.procstatus eq '0'}">
	             <li>편철상태: 편철진행</li>         
	         </c:if>
	         <c:if test="${Info.procstatus eq '1'}">
	             <li>편철상태: 편철확인</li>         
	         </c:if>
	         <c:if test="${Info.procstatus eq '2'}">
	             <li>편철상태: 편철확정</li>         
	         </c:if>
	         <c:if test="${Info.procstatus eq '3'}">
	             <li>편철상태: 문서이관</li>         
	         </c:if>
	         <%-- -- --%>
	         <c:if test="${Info.procstatus eq '0'}">
	             <li>편철가능여부: 가능</li>            
	         </c:if>           
	         <c:if test="${Info.procstatus eq '1'}">
	             <li>편철가능여부: 불가능</li>            
	         </c:if>
	         <%-- --%>
	             <li>단위과제코드: ${Info.bizunitcd}</li>
	             <li>처리과: ${Info.procdeptid}</li>
	             <li>기록물철담당자: ${Info.fldrmanagername}</li>
	             <li>보존기간: ${Info.keepingperiod}년</li>
	             <li>철연번: ${Info.bizunityearseq}</li>
	             <li>생산년도: ${Info.prodyear}</li>
	             <li>종료년도: ${Info.endyear}</li>
	         <%-- --%>
	         <c:if test="${Info.biztranstype eq '0'}">
	             <li>인수인계유형: 해당없음</li> 
	         </c:if>
	         <c:if test="${Info.biztranstype eq '1'}">
	             <li>인수인계유형: 조직변동(인수)</li> 
	         </c:if>
	         <c:if test="${Info.biztranstype eq '2'}">
	             <li>인수인계유형: 조직변동(인계)</li> 
	         </c:if>
	         <c:if test="${Info.biztranstype eq '3'}">
	             <li>인수인계유형: 업무이관(인수)</li> 
	         </c:if>
	         <c:if test="${Info.biztranstype eq '4'}">
	             <li>인수인계유형: 업무이관(인계)</li> 
	         </c:if>
	         <%-- --%>
	             <li>인계부서: ${Info.fromdeptid}</li>
	             <li>인계부서: ${Info.todeptid}</li>
	         </ul>
	     </section>
	 </main>
	 <footer>
	     <p>© 2024 기록물 관리 시스템</p>
	 </footer>
</div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="<c:url value='/resources/js/pagingCookie.js'/>"></script>
<script>
var fldrid = '<c:out value="${Info.fldrid}"/>';
var procdeptid = '<c:out value="${Info.procdeptid}"/>';
var buCode = '<c:out value="${Info.bizunitcd}"/>';
$(document).ready(function(){
	//쿠키 값 가져오기..
	var getPerPageNum = getCookie("perPageNum");
	if(getPerPageNum !== null){
		$('#perPageNum').val(getPerPageNum);
	}
	//사이드 메뉴 클릭 시 글자색 변경 고정.
	var urlParams = new URLSearchParams(window.location.search);
	var clickFldrId = urlParams.get('fldrid');
	$('.tree li a').each(function(){
		var fldrid = $(this).data('fldrid');
		if(clickFldrId === fldrid){
			$(this).css('color','green');
		}
	})
});
function loadDocFrameList(){
	var getLastDocUrl = getCookie("lastDocUrl");
	if(getLastDocUrl !== null){
		var docUrl = decodeURIComponent(getLastDocUrl);
		window.location.href = docUrl;
	}else{
		url = '<c:url value="docFrame?savedeptid=${deptId}"/>';
		window.location.href = url;
	}
}

function loadDocFrame(fldrid,procdeptid){
	var url = '<c:url value="DocFldrMng"/>'
	url += '?fldrid='+fldrid+'&prcodeptid='+procdeptid
	window.location.href = url;
}

function FldrEdit(){
	var url = '<c:url value="/folder/edit"/>';
	url += '?fldrid='+fldrid;
	window.open(url,"fldrEdit","width=500px, height=500px");
}

function FldrTransferYearEdit(){
	var url = '<c:url value="/folder/transferYear"/>'+'?fldrid='+fldrid;
	window.open(url,'transferyear','width=450px, height=190px');
}
function showAlertFromChild(message){
	alert(message);	
}
function CancelFldrStatus(){
	$.ajax({
		type:'post',
		url:'<c:url value="/folder/CancelFldrStatus"/>',
		data: {fldrid: fldrid},
		success: function(response){
			console.log(response);
			location.reload();
			alert("편철확인상태를 취소하였습니다.");
		},
		error: function(xhr,status,error){
			console.log(error);
			console.log(xhr.responseText);
		}
	});
}

function MoveApprFldr(){
	var url = '<c:url value="/folder/MoveApprFldr"/>';
	url += '?fldrid='+fldrid+'&procdeptid='+procdeptid;
	window.open(url,"MoveApprFldr","width=500px, height=500px");
}

function apprfolderIn_pop(){
	url = '<c:url value="/folder/folderAddAndApprF"/>';
	url += '?fldrid='+ fldrid + '&buCode=' + buCode + '&procdeptid=' + procdeptid;
	window.open(url, "apprfolderIn","width=500px, height=500px");
}
</script>
</body>
</html>