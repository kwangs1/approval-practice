<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<link rel="stylesheet" href="<c:url value='/resources/css/docfldrSidebar.css'/>"/>
<style>
.disable{color:gray;}
</style>
<body>
<%@ include file="../common/apprMenu.jsp" %><br><br>
<ul class="tree">
    <li>
      <a href="/kwangs">홈으로</a>
    </li>
  <c:forEach var="item" items="${ApprfldrSidebar}">
   <c:choose>
  	<c:when test="${item.applid == 1000}">
    <li>
    	<p class="disable">${item.fldrname}</p>
    </li>  		
  	</c:when>
  	<c:otherwise>
  	 <li>     
      <a href="#" data-applid ="${item.applid}"  
      onClick ="loadApprFrame('${user.deptid}','${user.id}','${item.ownerid}','${item.fldrid}','${item.fldrname}','${item.applid}')">
      ${item.fldrname}</a>
    </li>	
  	</c:otherwise>
  </c:choose>
  </c:forEach>
</ul>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="<c:url value='/resources/js/pagingCookie.js'/>"></script>
<script>
$(document).ready(function(){
	var g_applid = getCookie_a("applid");
	
	$('.tree li a').each(function(){
		var applid = $(this).attr('data-applid');
		if(applid == g_applid){
			$(this).css('color','green');
		}
	});
	
})

function loadApprFrame(drafterdeptid,id,ownerid,fldrid,fldrname,applid){
	if(applid === '2010'){
		var url = '<c:url value="/approval/apprWaitList"/>'
		url += '?drafterdeptid='+drafterdeptid+'&id='+id+'&ownerid='+ownerid+'&fldrid='+fldrid+'&fldrname='+fldrname+'&applid='+applid
		window.location.href = url;
		setCookie_f2(url);
	}
	else if(applid === '2020'){
		var url = "<c:url value='/approval/SanctnProgrsList'/>";
		url += '?drafterdeptid='+drafterdeptid+'&id='+id+'&ownerid='+ownerid+'&fldrid='+fldrid+'&fldrname='+fldrname+'&applid='+applid
		window.location.href = url;
		setCookie_f2(url);
	}
	setCookie_a(applid);
}
</script>
</body>
</html>