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

function saveOpinionToCookie(owner,credate,content){
	var opinionData = {
		owner: owner,
		credate: credate,
		content: content
	}
	var opinionString = encodeURIComponent(JSON.stringify(opinionData));
	document.cookie ='opinion= ' + opinionString + '; path=/;';
}

function getOpinionToCookie(name){
	var value= '; '+ document.cookie;
	var parts = value.split('; ' + name + '=');
	if(parts.length === 2) return decodeURIComponent(parts.pop().split(';').shift());
	return null;
}

function loadOpinionFromCookie(){
	var opinionCookie = getOpinionToCookie('opinion');
	console.log('Cookie value: ',opinionCookie);
	if(opinionCookie){
		try{
			var opinionData = JSON.parse(opinionCookie);
			console.log("Parsed opinnion data: ",opinionData)
			return opinionData;
		}catch(e){
			console.error("Failed to parse cookie data",e);
		}
	}else{
		console.warn('No opinion cookie found');
	}
}

function deleteCookie(name){
	document.cookie = name + "=; expires= Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
}