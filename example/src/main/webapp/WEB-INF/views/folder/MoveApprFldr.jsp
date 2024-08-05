<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<c:url value='/resources/css/loading.css'/>"/>
<link rel="stylesheet" href="<c:url value='/resources/css/f_tree.css'/>"/>
<style>
.tab_menu .list{list-style-type:none; padding:0; margin:0; display:flex;}
.tab_menu .list li {margin-right: 10px;}
.box {border: 1px solid black; margin: 30px; padding: 5px;}
a {text-decoration: none;}
</style>
</head>
<body>
	<input type="hidden" name="bizunitcd" id="bizunitcd" />
	<input type="hidden" id="currentFldrDepth" value="${Info.fldrdepth}" />
<div class="loading" id="loading"style="display:none">
	<div class="spinner">
		<span></span>
		<span></span>
		<span></span>
	</div>
	<p>기록물철 이동 중..</p>
</div>

	<button onclick="move()">이동</button>
	<button onclick="window.close()">닫기</button>
<hr>
	<div>
		문서함명 | ${Info.fldrname}
		<input type="hidden" id="parfldrid" name="parfldrid"/> 
		<input type="hidden" id="parfldrname" name="parfldrname"/><br>
		<br>
		기준문서함 | <input type="text" id="fldrname" name="fldrname" /> 
		<select id="fldrdepth" name="fldrdepth"></select>
	</div>

<hr>
<div class="box">
<ul class="tree">
	<c:forEach var="folder" items="${list}">
      <c:if test="${folder.parfldrid eq null}">
        <li id="folder${folder.fldrid}" id="folder">
	     <span class="folder-name">${folder.fldrname}</span>    
		         
          <%-- 단위과제 하위 start --%>
          <c:forEach var="subfolder" items="${list}">
            <c:if test="${subfolder.parfldrid eq folder.fldrid}">
              <ul class="folder">
                <li id="subfolder${subfolder.fldrid}">
			     <span class="folder-name">
			     	<a href="#" class="bizunitF" data-fldrid="${subfolder.fldrid}" data-applid="${subfolder.applid}"
			     		data-fldrname="${subfolder.fldrname}" data-fldrdepth="${subfolder.fldrdepth}" data-bizunitcd="${subfolder.bizunitcd}">
						${subfolder.fldrname}
					</a>				 
			     	</span>
					
					<c:forEach var="apprfolder" items="${subfolder.apprfolders}">
						<ul class="af-list"> 
							<a href="#" class="apprF" data-fldrid="${subfolder.fldrid}"  data-bizunitcd="${subfolder.bizunitcd}" data-fldrname="${subfolder.fldrname}" 
							data-applid_af="${apprfolder.applid}" data-fldrdepth_af="${apprfolder.fldrdepth}" data-fldrname_af="${apprfolder.fldrname}">
								${apprfolder.fldrname}
							</a>												
						</ul>
					 </c:forEach>
					 
                </li>
              </ul>
            </c:if>
          </c:forEach>
          <%-- end --%>      
        </li>
      </c:if>
    </c:forEach>
</ul>
</div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
//기록물철 부모
$('a.bizunitF').on('click',function(){
	var fldrid = $(this).data('fldrid');
	var fldrname = $(this).data('fldrname');
	var fldrdepth = $(this).data('fldrdepth');
	var applid = $(this).data('applid');
	var bizunitcd = $(this).data('bizunitcd');
	
	var event = new CustomEvent('msg',
	{ detail: 
		{fldrid: fldrid,
		 fldrname: fldrname,
		 fldrdepth: fldrdepth,
		 applid: applid,
		 bizunitcd : bizunitcd}
	});
	window.dispatchEvent(event);
});

//기록물철[fldrid,fldrname,bizunitcd는 단위과제거]
$('a.apprF').on('click',function(){
	var fldrid = $(this).data('fldrid');
	var fldrname = $(this).data('fldrname');
	var fldrdepth = $(this).data('fldrdepth_af');
	var applid = $(this).data('applid_af'); 
	var bizunitcd = $(this).data('bizunitcd'); 
	var fldrname_af = $(this).data('fldrname_af');
	
	var event = new CustomEvent('msg',
	{ detail: 
		{fldrid: fldrid,
		 fldrname: fldrname,
		 fldrdepth: fldrdepth,
		 applid: applid,
		 bizunitcd: bizunitcd,
		 fldrname_af : fldrname_af //인풋 태그 기록물철 클릭 시 값 다른게 넣기
		}
	});
	window.dispatchEvent(event);
});

window.addEventListener('msg',function(e){
	var fldrid  = e.detail.fldrid;
	var fldrname = e.detail.fldrname;
	var fldrname_af = e.detail.fldrname_af;
	var fldrdepth = e.detail.fldrdepth;
	var bizunitcd = e.detail.bizunitcd;
	
	document.getElementById('parfldrid').value = fldrid;
	document.getElementById('parfldrname').value = fldrname;
	document.getElementById('bizunitcd').value = bizunitcd;
	
	//option
	var selectElement = document.getElementById('fldrdepth');
	var txt = ['위로','아래로'];
	
	if(e.detail.applid === 7020){
		document.getElementById('fldrname').value = fldrname_af;
		selectElement.innerHTML = '';
		for(var i=0; i<txt.length; i++){
			var option = document.createElement('option');
			option.innerHTML = txt[i];		
			option.value = (fldrdepth === 2) ? fldrdepth : fldrdepth -1;
			selectElement.appendChild(option);
		}				
		console.log("7020 depth? "+option.value);	
		
		$(function(){
			//기록물철 부모클릭하고 다시 기록물철 클릭하면 기존에 있던 값이 제거가안되, 중복으로 찍혀 .off 추가..
			$('#fldrdepth').off('change').on('change',function(){
				console.log('change');
				var value = $(this).find('option:selected').text();
				if(value === '위로'){
					option.value = (fldrdepth === 2) ? fldrdepth : fldrdepth -1;
					console.log("top 7020 depth? "+option.value);	
				}else{
					option.value = fldrdepth +1;	
					console.log("bottom 7020 depth? "+value+"__ "+option.value);			
				}
			});
		});

	}else
		if(e.detail.applid === 7010){
			document.getElementById('fldrname').value = fldrname;
			selectElement.innerHTML = '';
			var option = document.createElement('option');
			option.innerHTML = '하부로';
			selectElement.appendChild(option);
			option.value = fldrdepth +1;
			console.log("7010 depth? "+option.value);
		}
});

function move(){
	var loading = document.getElementById('loading')
	loading.style.display = 'flex';
	var param = 
	{
		bizunitcd: $('#bizunitcd').val(),
		fldrid: '<c:out value="${Info.fldrid}"/>',
		ownerid: '<c:out value="${Info.ownerid}"/>',
		parfldrid: $('#parfldrid').val(),
		parfldrname: $('#parfldrname').val(),
		fldrdepth: $('#fldrdepth').val(),
		currentFldrDepth: $('#currentFldrDepth').val()
	}
	$.ajax({
		type: 'post',
		url: '<c:url value="/folder/MoveApprFldr"/>',
		data: param,
		success: function(response){
			console.log(response);
			setTimeout(function(){ 
				alert("기록물철이 이동 되었습니다.");
				window.close();
				window.opener.location.reload();
			},3000)			
		},
		error: function(xhr,status,error){
			console.log(error);
			console.log(xhr.responseText);
		}
	});
}
</script>
</body>
</html>