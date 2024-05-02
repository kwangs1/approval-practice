/**
 * 페이징 갯수 설정 쿠키에 저장 및 각종 폴더 쿠키 저장
 */
function saveCookie(perPageNum){
	var expires = new Date();
	expires.setDate(expires.getDate()+30);
	
	document.cookie = "perPageNum="+ perPageNum + "; expires=" + expires.toUTCString() + '; path=/';
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

function setCookie_a(applid){
	var expires = new Date();
	expires.setDate(expires.getDate()+30);
	
	document.cookie = 'applid=' + applid +'; expires=' + expires.toUTCString() + '; path=/';
}

function getCookie_a(name){
	var nameEQ = name + "=";
	var cookies = document.cookie.split(";");
	
	for(var i=0; i < cookies.length; i++){
		var cookie = cookies[i].trim();
		while(cookie.charAt[0] === ' '){
			cookie = cookie.substring(1, cookie.length);
		}
		if(cookie.indexOf(nameEQ) === 0){
			return cookie.substring(nameEQ.length, cookie.length)
		}
	}
	return null;
}

function setCookie_f(url){
	var expires = new Date();
	expires.setDate(expires.getDate()+30);
	
	document.cookie = "lastDocUrl=" + encodeURIComponent(url) +"; expires=" + expires.toUTCString() + "; path=/";
}

function getCookie_f(name){
	var nameEQ = name + "=";
	var cookies = document.cookie.split(";");
	
	for(var i=0; i < cookies.length; i++){
		var cookie = cookies[i].trim();
		while(cookie.charAt(0) === ' '){
			cookie = cookie.substring(1, cookie.length);
		}
		if(cookie.indexOf(nameEQ) === 0){
			return cookie.substring(nameEQ.length, cookie.length);
		}
	}
	return "";
}

function setCookie_f2(url){
	var expires= new Date();
	expires.setDate(expires.getDate()+30);
	
	document.cookie = 'lastApprUrl=' + encodeURIComponent(url) + '; expires=' + expires.toUTCString() + '; path=/';
}

function getCookie_f2(name){
	var nameEQ = name + "=";
	var cookies = document.cookie.split(";");
	
	for(var i=0; i < cookies.length; i++){
		var cookie = cookies[i].trim();
		while(cookie.charAt(0) === ' '){
			cookie = cookie.substring(1, cookie.length);
		}
		if(cookie.indexOf(nameEQ) === 0){
			return cookie.substring(nameEQ.length, cookie.length);
		}
	}
	return "";
}