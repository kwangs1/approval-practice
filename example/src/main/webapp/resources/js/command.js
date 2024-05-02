/**
 * 공통 커맨드 스크립트 파일
 */
//공통 텍스트 영역 변수
var content = document.getElementById('content');

//실행 취소
function undo(){
	content.value ='';
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

//복사
function ctrlC(){
	document.execCommand('copy');
}

//전체선택
function ctrlA(){
	content.select();
	document.execCommand('copy');
}

//잘라내기
function ctrlX(){	
	//드래그 시작 부분
	var start = content.selectionStart;
	//끝 부분
	var end = content.selectionEnd;
	
	//잘라내기 한 후 남은 영역들 그냥 두기 
	var before = content.value.substring(0, start);
	var after = content.value.substring(end);
	
	// 복사할 텍스트
    var cutText = content.value.substring(start, end);
	
	content.value = before + after;
	
	/** 
	프로미스를 사용한 비동기 코드
	then: 작업이 성공하면 호출되는 콜백함수
	catch: 작업이 실패한 경우 호출되는 콜백함수
	
	Clipboard API 사용
	*/
	navigator.clipboard.writeText(cutText)
        .then(() => {
            console.log('Text successfully copied to clipboard');
        })
        .catch(err => {
            console.error('Unable to copy text to clipboard', err);
        });
}

//영역 삭제
function del(){
	var start = content.selectionStart;
	var end = content.selectionEnd;
	
	var before = content.value.substring(0,start);
	var after = content.value.substring(end);
	
	content.value = before + after;
}

//문자열 바꾸기
function change(){
	var start = content.selectionStart;
	var end = content.selectionEnd;
	
	var selected = content.value.substring(start,end);
	var replacement = prompt('어떤 문자로 변경하시겠습니까?');
	
	//사용자가 취소하지 않은경우에만 변경
	if(replacement !== null){
		content.value = content.value.substring(0, start) +
		replacement +
		content.value.substring(end);
	}
	
	//커서 위치 조정(변경된 텍스트 길이만큼 이동)
	content.setSelectionRange(start, start + replacement.length);
}

//인쇄
function printPage(){
	window.print();
}
function beforePrint(){
	initBodyHtml = document.body.innerHTML;
	document.body.innerHTML = document.getElementById('content').innerHTML;
}
function afterPrint(){
	document.body.innerHTML = initBodyHtml;
}
window.onbeforeprint = beforePrint;//프린터 출력 이전 화면
window.onafterprint = afterPrint;//프린터 출력 이후 이벤트
