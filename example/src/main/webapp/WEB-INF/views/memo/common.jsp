<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<style>
.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 120px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  padding: 12px 16px;
  z-index: 1;
}

.dropdown:hover .dropdown-content {
  display: block;
}

.dropdown-content:hover{
  color: red;
}
a {
  text-decoration: none;
}
</style>
</head>
<body>
<div class="dropdown">
  <span>파일</span>&nbsp;&nbsp;|&nbsp;
  <div class="dropdown-content">
		<a href="javascript:clean()">새로 만들기</a><br>
		<a href="javascript:newOpen()" class="new">새 창열기</a><br>
		<a href="javascript:SaveAs()" class="as">다른이름 저장</a><br>
		<a href="#" onclick="return printPage()">인쇄하기</a><br>
		<a href="#" onclick="window.close()">끝내기</a>
  </div>
</div>

<div class="dropdown">
  <span>편집</span>
  <div class="dropdown-content">
		<a href="javascript:clean()">실행 취소</a><br>
		<a href="javascript:ctrlX()">잘라내기</a><br>
  		<a href="javascript:ctrlC()">복사</a><br>
		<a href="javascript:ctrlA()">전체 복사</a><br>
		<a href="javascript:del()">영역 삭제</a><br>
		<a href="javascript:change()">문자 바꾸기</a><br>
		<a href="javascript:TimeAndDate()">시간/날짜</a>
  </div>
</div>
</body>
</html>