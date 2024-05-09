/**
 * 문서에 관련한 쿠키값
 */
function saveCookie(docattr){
	var expires = new Date();
	expires.setDate(expires.getDate()+1);
	
	document.cookie = "docattr="+ docattr + "; expires=" + expires.toUTCString() + '; path=/';
}

function getCookie(name){
	var nameEQ = name + "=";
	var cookies = document.cookie.split(";");
	
	for(var i =0; i < cookies.length; i++){
		var cookie = cookies[i].trim();
		//쿠키 앞 공백 제거
		while(cookie.charAt(0) === ' '){
			cookie = cookie.substring(1, cookie.length);
		}
		//현재 쿠키가 가져올 쿠키인지 확인
		if(cookie.indexOf(nameEQ) === 0){
			//쿠키 찾으면 값만 반환
			return cookie.substring(nameEQ.length, cookie.length);
		}
	}
	return null;
}

function deleteCookie(name){
	document.cookie = name + "=; expires= Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
}