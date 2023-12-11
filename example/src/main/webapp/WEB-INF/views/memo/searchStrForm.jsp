<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var='path' value='${pageContext.request.contextPath}' />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${search.title}</title>
</head>
<body>
	<form id="searchForm">
		<!-- 'content'로 고정된 select -->
		<select name="content" id="searchType">
			<option value="content">내용</option>
		</select> <input type="text" name="keyword" id="keyword" />
		<button type="button" onclick="searchStr()">검색</button>
	</form>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
function searchStr() {
   var searchType = $('#searchType').val();
   var keyword = $('#keyword').val();

   $.ajax({
      type: 'get',
      url: '${path}/memo/searchStr',
      data: { searchType: searchType, keyword: keyword },
      success: function(result) {
         processSearchResult(result);
      },
      error: function(xhr, status, error){
         console.log(xhr);
         console.log(status);
          console.log(error);
      }
  });
}

function processSearchResult(result) {
  // JSON 데이터를 객체로 파싱
  var searchResult = result;

  // 여기에서 검색 결과를 적절한 방식으로 표시하거나, 다른 동작을 수행
  console.log('Received search result:', searchResult);
  // 예시: 검색 결과를 화면에 표시하는 함수 호출
  displaySearchResult(searchResult);
}

function displaySearchResult(result) {
   // 여기에서 검색 결과를 적절한 방식으로 표시하거나, 다른 동작을 수행
   console.log('Displaying search result:', result);
   // 예시: 검색 결과를 화면에 표시하는 함수 호출 (read.jsp에 정의된 함수를 호출)
   window.opener.displaySearchResult(result);
}
</script>
</body>
</html>
