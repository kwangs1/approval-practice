<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<style>
.table-cell{border-right: 1px solid #000;}
.table-cell:last-child{border-right:none;}
</style>
<body>
<div class="OpinionList"></div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="<c:url value='/resources/js/DocCookie.js'/>"></script>
<script>
var CheckOpinion = window.opener.CheckOpinion && !window.opener.closed ?
		window.opener.CheckOpinion : '';
var approvalstatus = window.opener.approvalstatus && !window.opener.closed ?
		window.opener.approvalstatus : '';
var approvaltype = window.opener.approvaltype && !window.opener.closed ?
		window.opener.approvaltype : '';
var participant_seq = window.opener.participant_seq && !window.opener.closed ?
		window.opener.participant_seq : '';
var poststatus = window.opener.poststatus && !window.opener.closed ?
		window.opener.poststatus : '';
var sendtype = window.opener.sendtype && !window.opener.closed ?
		window.opener.sendtype : '';
var drafterdeptid = window.opener.drafterdeptid && !window.opener.closed ?
		window.opener.drafterdeptid : '';
var appr_seq = window.opener.appr_seq && !window.opener.closed ?
		window.opener.apprid : '';
var deptid = '<c:out value="${user.deptid}"/>';

$(document).ready(function(){
		var appr_seq = window.opener.appr_seq && !window.opener.closed ?
						window.opener.appr_seq : '';
		$.ajax({
			url: '<c:url value="/approval/DocOpinionList"/>',
			type: 'get',
			data: {appr_seq: appr_seq},
			success: function(data){
				var htmls = '';
				for(var i in data){
					var id = data[i].opinionid;
					var type = data[i].opiniontype;
					var content = data[i].opinioncontent;
					var credate = data[i].credate;
					var name = data[i].signername;
					
					if(drafterdeptid === deptid){
						htmls += '<table>'
						htmls += '<thred><tr>'
						htmls += '<th class="table-cell">구분</th>'
						htmls += '<th class="table-cell">이름</th>'
						htmls += '<th class="table-cell">작성일자</th>'
						htmls += '<th class="table-cell">내용</th>'
						htmls += '</tr></thread>'
						htmls += '<tbody><tr>'
						if(type === 'P1'){
							htmls += '<td class="table-cell">일반</td>'
						}
						htmls += '<td class="table-cell">'+name+'</td>'
						htmls += '<td class="table-cell">'+credate+'</td>'
						htmls += '<td>'+content+'</td>'
						if(id === participant_seq && approvaltype === '4'){
							htmls += '<td><button onclick="OpinionDel(\'' + id + '\',\'' + content + '\',\'' + type + '\')" >X</button></td>'	
						}
						htmls += '</tr></tbody>';
						htmls += '</table>';	
					}
				}
				$('.OpinionList').html(htmls);
				CookieIsNotNullOpinion();
				adjustPopupSize();
			},
			error: function(xhr,status,error){
				console.log(error);
				console.log(status);
				console.log(xhr);
			}
		})
});
function adjustPopupSize(){
	var contentDiv = document.getElementsByClassName('OpinionList')[0];
	if(contentDiv){
		var tableRows = contentDiv.getElementsByTagName('tr');
		var baseHeight = 200;
		var adjustedheight = tableRows.length * 40;
		var newHeight = baseHeight + adjustedheight;
		window.resizeTo(window.innerWidth, newHeight);
	}else{
		console.log("contentDiv is null");
	}
}
function CookieIsNotNullOpinion(){
	var opinionData = loadOpinionFromCookie();
	var opinionListElement = $('.OpinionList');
	if(opinionData && opinionListElement.length){
		var html = '<table>'
		html += '<thred><tr>'
		html += '<th></th>'
		html += '<th class="table-cell">이름</th>'
		html += '<th class="table-cell">작성일자</th>'
		html += '<th class="table-cell">내용</th>'
		html += '</tr></thread>'
		html += '<tbody><tr>'
		html += '<td><input type="checkbox" id="checkbox" style="display:none;"/></td>'
		html += '<td class="table-cell">'+ (opinionData.owner || '') +'</td>'
		html += '<td class="table-cell">'+ (opinionData.credate || '') +'</td>'
		html += '<td>'+ (opinionData.content || '') +'</td>'
		html += '<td><button onclick="OpinionCookieDel()">X</button></td>'
		html += '</tr></tbody>';
		html += '</table>';
		
		console.log($('.OpinionList').html(html));
		opinionListElement.html(html);
	}else{
		console.warn('No opinion data to display');
	}
}

function OpinionCookieDel(){
	if(CheckOpinion === 'false'){
		opener.document.getElementById('parentOpi').value ='';
		opener.document.getElementById('credate').value = '';
		deleteCookie('opinion');	
	}
	location.reload();
}

function OpinionDel(id,content,type){
	var param = {
			participant_seq: id,
			opinioncontent: content,
			opiniontype: type	}
	$.ajax({
		url: '<c:url value="/approval/DocOpinionDel"/>',
		type: 'post',
		contentType: 'application/json',
		data: JSON.stringify(param),
		success: function(response){
			alert("의견을 삭제하였습니다.");
			location.reload();
		},
		error: function(xhr, error, status){
			console.log(error);
			console.log(status);
			console.log(xhr);
		}
	})
}
</script>
</body>
</html>