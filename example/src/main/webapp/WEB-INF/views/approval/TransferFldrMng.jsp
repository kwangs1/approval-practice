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
<link rel="stylesheet" href="<c:url value='/resources/css/loading.css'/>"/>
<link rel="stylesheet" href="<c:url value='/resources/css/btn.css'/>"/>
<style>
input:focus{outline:none;}
input{ border:none;}
</style>
<body>
<div class="loading" id="loading"style="display:none">
	<div class="spinner">
		<span></span>
		<span></span>
		<span></span>
	</div>
	<p>이관 중..</p>
</div>
<ul class="tree">
    <li>
      <a href="/kwangs">홈으로</a>
    </li>
  <c:forEach var="item" items="${docfldrSidebar}">
  <c:choose>
     <c:when test="${item.applid != 7020 && item.applid != 7040}">
   	 <li>
    	<p class="disable">${item.fldrname}</p>
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
	<h2>단위과제관리(${Info.fldrname})</h2>
</header>
   <main> 
	<button class="button" onclick="MoveFldrMng()">이동</button>
	<br><br>
        <section class="additional-info">
            <ul>
            	<li>선택된 함명: <input type="text" name="fldrname" value="${Info.fldrname}" readonly="readonly"/></li>
            	<li>
            		<input type="radio" name="biztranstype" id="biz_1" value="1" checked="checked">조직변동
            		<input type="radio" name="biztranstype" id="biz_2" value="2">업무이관
            	</li>
                <li>인계부서:<input type="text" value="${procDeptName.deptname}" readonly="readonly"/> 
                	<input type="hidden" name="fromdeptid" value="${Info.procdeptid}"/></li>
                
                <li>인계부서: <a href="#" class="deptLink" style="text-decoration: none;">🕵🏻</a>
                	<input type="text" class="deptLink" id="todeptname" style="background-color:#d5dee8;" readonly="readonly"/>
                	<input type="hidden" name="todeptid" id="todeptid" style="background-color:#d5dee8;"/></li>
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
var checkValue = $('input[type=radio][name=biztranstype]:checked').val();

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

function loadDocFrame(fldrid,procdeptid){
	var url = '<c:url value="TransferFldrMng"/>'
	url += '?fldrid='+fldrid+'&prcodeptid='+procdeptid
	window.location.href = url;
}

window.addEventListener('message', function(e){
	var data = e.data;
	var deptname = data.deptname;
	var deptid = data.deptid;
	
	$('#todeptname').val(deptname);
	$('#todeptid').val(deptid);
});

$(document).on('click','.deptLink',function(){
	window.open("<c:url value='/dept/joinUseDept'/>", 'deptLink','width=550, height=350');
})

function MoveFldrMng(){
	var url = '<c:url value="/approval/DocFldrMng"/>'+'?fldrid='+ fldrid + '&procdeptid='+ procdeptid;
	var loading = document.getElementById('loading')
	loading.style.display = 'flex';
	
	var paramData ={
		fldrid: fldrid,
		procdeptid: procdeptid,
		fromdeptid: procdeptid,
		todeptid: $('#todeptid').val(),
		biztranstype: checkValue
	}
	
	$.ajax({
		type: 'post',
		url: '<c:url value="/folder/MoveFldrMng"/>',
		data: paramData,
		success: function(response){
			setTimeout(function(){ 
				alert("해당 기록물철이 이관되었습니다.");
				location.href= url;
			},3000)			
		},
		error: function(xhr,error,status){
			console.log(error);
			console.log(status);
			console.log(xhr.responseText);
		}
	})
}
</script>
</body>
</html>