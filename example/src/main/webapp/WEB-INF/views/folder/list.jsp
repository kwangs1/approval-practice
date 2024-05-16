<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<c:url value='/resources/css/u_tree.css'/>"/>
<body>
<a href="FolderAdd">폴더등록</a> | 
<a href="javascript:window.close()">닫기</a><br>
  <ul class="tree">
  <%-- 최상위 폴더 start --%>
    <c:forEach var="superfldr" items="${list}">
      <c:if test="${superfldr.parfldrid eq null}">
        <li id="superfldr${superfldr.fldrid}">
          <a href="info.do?fldrid=${superfldr.fldrid}" >${superfldr.fldrname}</a>
          
          <%-- 최상위 하위 폴더 start --%>
          <c:forEach var="subfldr" items="${list}">
            <c:if test="${subfldr.parfldrid eq superfldr.fldrid}">
              <ul>
                <li id="subfldr${subfldr.fldrid}">
                <a href="info.do?fldrid=${subfldr.fldrid}">${subfldr.fldrname}</a>     
                          
                  <%-- 최상위 하위 - 하위 폴더 start --%>
                  <c:forEach var="grandfldr" items="${list}">
                    <c:if test="${grandfldr.parfldrid eq subfldr.fldrid}">
                      <ul>
                        <li id="grandfldr${grandfldr.fldrid}" class="lastTree">
                        <a href="info.do?fldrid=${grandfldr.fldrid}" >${grandfldr.fldrname}</a>
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
	    $('ul.tree ul').show();

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
	});
 <%-- tree and --%>  
 </script>
</body>
</html>