<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<link rel="stylesheet" href="<c:url value='/resources/css/tree.css'/>"/>
<body>
<button onclick="javascript:window.close();">닫기</button></br>
  <ul class="tree">
  <%-- 최상위 부서 start --%>
    <c:forEach var="dept" items="${joinUseDept}">
      <c:if test="${dept.parid eq null}">
        <li id="dept${dept.deptid}">
          <a href="#" class="deptLink" data-deptname="${dept.deptname}" data-deptid="${dept.deptid}">${dept.deptname}</a>
          <%-- 최상위 하위 부서 start --%>
          <c:forEach var="subDept" items="${joinUseDept}">
            <c:if test="${subDept.parid eq dept.deptid}">
              <ul>
                <li id="subDept${subDept.deptid}">
                <a href="#" class="deptLink" data-deptname="${subDept.deptname}" data-deptid="${subDept.deptid}">${subDept.deptname}</a>
                  
                  <%-- 최상위 하위 - 하위 부서 start --%>
                  <c:forEach var="grandDept" items="${joinUseDept}">
                    <c:if test="${grandDept.parid eq subDept.deptid}">
                      <ul>
                        <li id="grandDept${grandDept.deptid}">
                        <a href="#" class="deptLink" data-deptname="${grandDept.deptname}" data-deptid="${grandDept.deptid}">${grandDept.deptname}</a>
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

 
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
<%-- tree start --%>  
$(document).ready(function() {
  // 처음에 모든 자식 요소를 감춥니다.
    $('ul.tree ul').hide();

  // 루트 요소와 자식 요소에 클릭 이벤트를 추가합니다.
    $('ul.tree li').click(function(e) {
     if (e.target.tagName !== 'INPUT') {
          e.stopPropagation();
         	$(this).children('ul').toggle();
        }
     });
});
 <%-- tree and --%>  
 <%-- 부서명,ID 데이터 넘기기 --%>
$('a.deptLink').on('click',function(e){
    e.preventDefault();
    	
    var deptname = $(this).data('deptname');
    var deptid = $(this).data('deptid');
    	
    window.opener.postMessage({deptname:deptname, deptid:deptid},"*");
    window.close();
});
</script>
</body>
</html>