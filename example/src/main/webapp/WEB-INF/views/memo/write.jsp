<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<style>
textarea{
	width: 100%;
	min-height: 5rem;
	overflow-y: hidden;
	resize: none;
	border: none;
	outline: none;
}
</style>
<body>
<div>
	<input type="hidden" name="title" />
	<textarea name="content" onkeydown="resize(this)" onkeyup="resize(this)"></textarea> 
</div>

<script>
function resize(obj) {
    obj.style.height = '1px';
    obj.style.height = (12 + obj.scrollHeight) + 'px';
}
</script>
</body>
</html>