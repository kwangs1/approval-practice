<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<link rel="stylesheet" href="<c:url value='/resources/css/f_tree.css'/>"/>
<body>
<input type="hidden" id="uId" value="${userid}"/>
<div id="btn">
	<button onclick="javascript:window.close();">닫기</button> 
	<button onclick="confirmSelection()">확인</button></br></br>
</div>
<%-- --%>
  <ul class="tree">
  <%-- 최상위 부서 start --%>
    <c:forEach var="dept" items="${flowUseInfo}">
      <c:if test="${dept.parid eq null}">
        <li id="dept${dept.deptid}">
	     <span class="dept-name">${dept.deptname}</span>
	
			<c:forEach var="user" items="${dept.users}">
			         <c:if test="${not empty user.name}">
			      		<ul class="user-list"> 
			            		😑 <a href="#" class="userLink" data-deptid="${user.deptid}" data-deptname="${user.deptname}"
			             			data-id="${user.id}" data-name="${user.name}" data-pos="${user.pos}">
			             	<c:out value="${user.name}" escapeXml="false" />
			             		</a>
			      		</ul>
			        </c:if>
			 </c:forEach>
          
          <%-- 최상위 하위 부서 start --%>
          <c:forEach var="subDept" items="${flowUseInfo}">
            <c:if test="${subDept.parid eq dept.deptid}">
              <ul>
                <li id="subDept${subDept.deptid}">
			     <span class="dept-name">${subDept.deptname}</span>
			
					<c:forEach var="user" items="${subDept.users}">
					         <c:if test="${not empty user.name}">
					      		<ul class="user-list"> 
					            		😑<a href="#" class="userLink" data-deptid="${user.deptid}" data-deptname="${user.deptname}"
					             			data-id="${user.id}" data-name="${user.name}" data-pos="${user.pos}">
					             	<c:out value="${user.name}" escapeXml="false" />
					             		</a>
					      		</ul>
					        </c:if>
					 </c:forEach>
   
                  <%-- 최상위 하위 - 하위 부서 start --%>
                  <c:forEach var="grandDept" items="${flowUseInfo}">
                    <c:if test="${grandDept.parid eq subDept.deptid}">
                      <ul>
                        <li id="grandDept${grandDept.deptid}">
					     <span class="dept-name">${grandDept.deptname}</span>
					
							<c:forEach var="user" items="${grandDept.users}">
							         <c:if test="${not empty user.name}">
							      		<ul class="user-list"> 
							            	😑<a href="#" class="userLink" data-deptid="${user.deptid}" data-deptname="${user.deptname}"
							             			data-id="${user.id}" data-name="${user.name}" data-pos="${user.pos}">
							             	<c:out value="${user.name}" escapeXml="false" />
							             		</a>
							      		</ul>
							        </c:if>
							 </c:forEach>
                        </li>
                      </ul>
                    </c:if>
                  </c:forEach>
                  <%-- 최상위 하위 - 하위 부서 end --%>
                  
                </li>
              </ul>
            </c:if>
          </c:forEach>
          <%-- 최상위 하위 부서 end --%>
          
        </li>
      </c:if>
    </c:forEach>
    <%-- 최상위 부서 end --%>
  </ul>
<div id="selectedUsers"></div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="<c:url value='/resources/js/SenderFlowInfo.js'/>"></script>
<script>
var uId = '<c:out value="${user}"/>';
<%-- 조직트리 --%>
$(document).ready(function() {
	// 처음에 모든 자식 요소를 감춥니다.
    $('ul.tree ul').hide();
	if(uId){
		//사용자가 속한 부서의 li엘리먼트를 찾아 해당 li와 그 부모들의 ul를 모두 보여줌
		$('ul.tree li').has('a[data-id="${user}"]').children('ul').show();
		$('a[data-id="${user}"]').closest('ul.tree li').addClass('expanded');
	}
	
  // 루트 요소와 자식 요소에 클릭 이벤트를 추가합니다.
    $('ul.tree li').click(function(e) {
     if (e.target.tagName !== 'INPUT') {
          e.stopPropagation();
         	$(this).children('ul').toggle();
         	
         	if($(this).hasClass('expanded')){
         		$(this).removeClass('expanded').addClass('collapsed');
         	}else{
         		$(this).removeClass('collapsed').addClass('expanded');
         	}
        }
     });
  
  $('.user-list').click(function(e){
	  e.stopPropagation();
  });  
	
$.ajax({
	type: 'get',
	url: '<c:url value="/getSaveFlowUseInfoTemp"/>',
	data: {id : uId},
	dataType: 'json',
	success: function(data){
		if(data.length === 0){
			if(uId){
				$('a.userLink[data-id="'+ uId +'"]').each(function(){
					 var deptid = $(this).data('deptid');
					 var deptname = $(this).data('deptname');
					 var id = $(this).data('id');
					 var name = $(this).data('name');
					 var pos = $(this).data('pos');	

					 selectedUsers.push({ deptid: deptid, deptname: deptname, id: id, name: name, pos: pos });
					 updateSelectedUsersUI();			
				})
			}
		}else{
			for(var i =0; i<data.length; i++){
				var user = data[i];
				drawParticipant(user);
				console.log("success call infoTemp");
				console.log(user);
			}
		}
	},
	error: function(error){
		console.error("Error seding clicked users to server:",error);
	}
});//end ajax	

function drawParticipant(user){
	  var deptid = user.deptid;
	  var deptname = user.deptname;
	  var id = user.id;
	  var name = user.name;
	  var pos = user.pos;

	  selectedUsers.push({ deptid: deptid, deptname: deptname, id: id, name: name, pos: pos });
	  updateSelectedUsersUI();	
}

}); 
</script>
</body>
</html>