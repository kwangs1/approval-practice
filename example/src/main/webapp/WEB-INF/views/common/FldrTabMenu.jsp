<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<c:url value='/resources/css/tabMenu.css'/>"/>
</head>
<body>
<div class="tab_menu">
	<ul class="list">
		<li class="tab1 is_on"><a href="javascript:DocFldrMng()" class="btn">함관리</a></li>
		<li class="tab2"><a href="javascript:TransferFldrMng()" class="btn">문서이동</a></li>
	</ul>
</div>
<br>

<script>
function TransferFldrMng(){
	url = '<c:url value="/approval/TransferFldrMng"/>';
	url += '?fldrid='+fldrid+'&procdeptid='+procdeptid;
	
	location.href=url;
}
function DocFldrMng(){
	url = '<c:url value="/approval/DocFldrMng"/>';
	url += '?fldrid='+fldrid+'&procdeptid='+procdeptid;
	
	location.href=url;
}
</script>
</body>
</html>