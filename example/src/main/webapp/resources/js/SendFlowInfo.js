/**
 * 결재선 지정 후 보내기
 */

var selectedUsers = [];
//로그인한 사용자는 해당 페이지 열 때 무조건 결재선에 바로 지정되게
$(document).ready(function(){
	if(uId){
		$('a.userLink[data-id="'+ uId +'"]').each(function(){
			 var deptid = $(this).data('deptid');
			 var deptname = $(this).data('deptname');
			 var id = $(this).data('id');
			 var name = $(this).data('name');
			 var pos = $(this).data('pos');	

			 selectedUsers.push({ deptid: deptid, deptname: deptname, id: id, name: name, pos: pos });
			 updateSelectedUsersUI();			
		})
	}
});

$('a.userLink').on('click',function(e){
	e.preventDefault();
	
	var deptid = $(this).data('deptid');
	var deptname = $(this).data('deptname');
	var id = $(this).data('id');
	var name = $(this).data('name');
	var pos = $(this).data('pos');	
    
	//배열에 담아 전송
	selectedUsers.push({ deptid: deptid, deptname: deptname, id: id, name: name, pos: pos });
    // 유저 클릭시 동적으로 화면에 표시되게 하기위해 함수 호출
    updateSelectedUsersUI();
})
function updateSelectedUsersUI() {
    // 선택된 유저 정보를 기반으로 UI 업데이트
    var selectedUsersDiv = $('#selectedUsers');
    selectedUsersDiv.empty(); // 기존 내용을 비움
    

    // 배열에 있는 각 유저 정보를 순회하면서 UI에 추가
    for (var i = 0; i < selectedUsers.length; i++) {
       var user = selectedUsers[i];
       //팝업 화면에서의 결재선 상태값 설정 
       var status = user.status || (i === selectedUsers.length - 1 ? 4000 : (i > 0 ? 2000 : 1000));
       
    	//오른쪽 유저화면에서의 위아래 이동버튼 및 삭제버튼
       var userDiv = $('<div class="userDiv"></div>');
       userDiv.append('<p>' + user.name + ' [' + user.pos + '] ' + getStatusDropdownHTML(i, status) + '</p>');

       // 위로 이동 버튼 생성
       if (i > 0) {
           var moveUpButton = $('<button onclick="moveUserUp(' + i + ')">↑</button>');
           userDiv.append(moveUpButton);
       }

       // 아래로 이동 버튼 생성
       if (i < selectedUsers.length - 1) {
           var moveDownButton = $('<button onclick="moveUserDown(' + i + ')">↓</button>');
           userDiv.append(moveDownButton);
       }

       // 삭제 버튼 생성
       var deleteButton = $('<button onclick="deleteUser(' + i + ')">❌</button>');
       userDiv.append(deleteButton);

   		// 전체 div를 selectedUsersDiv에 추가
       selectedUsersDiv.append(userDiv);
      }
  }
 
// 유저 화면단에서 임의로 status 값도 설정하여 전송하려고 만듬.
function getStatusDropdownHTML(index, defaultStatus) {
    // status 선택을 위한 dropdown의 HTML을 반환
    var dropdownHTML = '<select name="status_' + index + '">' +
        '<option value="1000" ' + (defaultStatus === 1000 ? 'selected' : '') + '>기안</option>' +
        '<option value="2000" ' + (defaultStatus === 2000 ? 'selected' : '') + '>검토</option>' +
        '<option value="3000" ' + (defaultStatus === 3000 ? 'selected' : '') + '>협조</option>' +
        '<option value="4000" ' + (defaultStatus === 4000 ? 'selected' : '') + '>결재</option>' +
        '</select>';
    return dropdownHTML;
}

function confirmSelection(){
    // 각 유저별로 select 태그의 값을 가져와서 status값도 전송
    for (var i = 0; i < selectedUsers.length; i++) {
        var status = $('[name="status_' + i + '"]').val();
        selectedUsers[i].status = status;
    }
	window.opener.postMessage({ users: selectedUsers }, '*');
	window.close();
}

//유저를 배열에서 삭제하는 함수
function deleteUser(index) {
    selectedUsers.splice(index, 1); //배열요소 하나만 삭제
    updateSelectedUsersUI();
}

// 유저를 위로 이동하는 함수 - 스왑
function moveUserUp(index) {
    if (index > 0) { 
        var temp = selectedUsers[index];
        selectedUsers[index] = selectedUsers[index - 1];
        selectedUsers[index - 1] = temp;
        updateSelectedUsersUI();
    }
}

// 유저를 아래로 이동하는 함수 - 스왑
function moveUserDown(index) {
    if (index < selectedUsers.length - 1) {
        var temp = selectedUsers[index];
        selectedUsers[index] = selectedUsers[index + 1];
        selectedUsers[index + 1] = temp;
        updateSelectedUsersUI();
    }
}