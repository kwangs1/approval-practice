/**
 * 공통 커맨드 스크립트 파일
 */

//실행 취소
function undo(){
	document.getElementById('content').value ='';
}

//시간/날짜
function TimeAndDate(){
	var today = new Date();
	
	year = today.getFullYear();
	month = today.getMonth()+1;
	date = today.getDate();
	
	h = today.getHours();
	m = today.getMinutes();
	
	var content = document.getElementById('content');
	content.value += year+ '-' + month +'-'+ date +' '+ h+':'+m;
	content.focus();
}