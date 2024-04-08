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
</style>
<body>
<%@ include file="../common/apprMenu.jsp" %><br><br>

<ul class="tree">
    <li>
      <a href="/kwangs">홈으로</a>
    </li>
  <c:forEach var="item" items="${docfldrSidebar}">
  <c:choose>
  	<c:when test="${item.applid != 8010 && item.applid != 8000 && item.applid != 7000 && item.applid != 7010}">
  	 <li>
      <a href="#" class="loadfldrList" data-ownerid="${item.ownerid}" data-fldrid="${item.fldrid}"
      	data-fldrname="${item.fldrname}">${item.fldrname}</a> 	
    </li>
  	</c:when>
 
   	<c:when test="${item.applid == 8010}">
   	 <li>
      <a href="docFrame.do?deptid=${currUser.deptid}">${item.fldrname}</a> 	
    </li> 	
  	</c:when>
  	
  	<c:otherwise>
    <li>
    	<p class="disable">${item.fldrname}</p>
    </li>  		
  	</c:otherwise>
  	

  </c:choose>
  </c:forEach>
</ul>

<div class="cd1">
<h1 id="title_txt">기록물 등록대장</h1>
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
</div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
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

$('a.loadfldrList').click(function(e){
	e.preventDefault();
	var ownerid = $(this).data('ownerid');
	var fldrid = $(this).data('fldrid');
	var fldrname = $(this).data('fldrname');
	
	$('#title_txt').text(fldrname);
	
	var reqData = {ownerid :ownerid, fldrid :fldrid, fldrname :fldrname}
	$.ajax({
		type: 'get',
		url: '<c:url value="/folder/loadFldrDoc.do"/>',
		data: reqData,
		success: function(data){
			var htmls = '';
				
				for(var i in data){
					var apprid = data[i].apprid;
					var title = data[i].title;
					var draftername = data[i].draftername;
					var lastsignerid = data[i].lastsignerid;
					var drafterdate = data[i].drafterdate;
					var approvaldate = data[i].approvaldate;

				htmls+='<tr>'
				htmls+='<td><input type="checkbox" name="apprid" class="seq" id="apprid" value="'+ apprid+'" />';
				htmls+='&nbsp;&nbsp;'
				htmls+='<a href="#" data-apprid="'+apprid+'" class="Simpleinfo">🔍</a></td>';
				htmls+='<td>'+data[i].docregno+'</td>'
				htmls+='<td><a href="#" data-apprid="'+apprid+'" class="Docinfo">'+title+'</a></td>';
				htmls+='<td>'+draftername+'</td>';
				htmls+='<td>'+lastsignerid+'</td>';
				htmls+='<td>'+drafterdate+'</td>';
				htmls+='<td>'+approvaldate+'</td>';
				htmls+='</tr>';	
				}
					
			
		$('#docListBody').html(htmls);
		},
		error: function(xhr,error,status){
			console.log(xhr);
			console.log(error);
			console.log(status);
		}
	});
});	
</script>
</body>
</html>