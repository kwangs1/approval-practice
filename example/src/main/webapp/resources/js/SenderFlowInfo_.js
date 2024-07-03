/**
 * 결재선 지정 후 보내기
 */

var selectedUsers = []; //결재선 참여자
var selectedApFolder = []; //기록물철
var selectedDept = []; //발송 시 수신처에 대한 부서정보
var checkedValues = []; //문서 유형값
var selectedValue =[]; //셀렉트박스[발신부서id, 발신명의]

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

//편철 데이터 전송
var selectedFldrid = null;
var selectedFldrname = null;
var selectedBizunitcd = null;
$('a.afLink').on('click',function(e){
e.preventDefault();
	
selectedFldrid = $(this).data('fldrid');
selectedBizunitcd = $(this).data('bizunitcd');
selectedFldrname = $(this).data('fldrname');

selectedApFolder = { fldrid: selectedFldrid, fldrname: selectedFldrname, bizunitcd: selectedBizunitcd };
updateSelectedApFolder();
});
//수신부서 정보 전송
$('a.deptLink').on('click',function(e){
	e.preventDefault();
	
	selectDeptId = $(this).data('deptid');
	selectDeptName = $(this).data('deptname');
	selectDeptSender = $(this).data('sendername');
	
	selectedDept.push({deptid: selectDeptId,deptname: selectDeptName,sendername: selectDeptSender});
	console.log(selectedDept);
	updateselectedDept();
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
       if(SendID !== ''){
           userDiv.append('<p>' + user.name + ' [' + user.pos + '] ' + getStatusDropdownHTML2(i, status) + '</p>');	   
       }else{
           userDiv.append('<p>' + user.name + ' [' + user.pos + '] ' + getStatusDropdownHTML(i, status) + '</p>');   
       }

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
function updateSelectedApFolder(){
	var selDiv = $('#selectedApFolder');
	selDiv.empty(); //기존 내용 제거

	var userDiv = $('<div class="userDiv"></div>');
	userDiv.append('<p>'+ selectedApFolder.fldrname + '</p>');
			
	selDiv.append(userDiv);

}
//선택 된 수신부서
function updateselectedDept(){
	var selDiv = $('#selectedDept');
	selDiv.empty(); //기존 내용 제거
	for(var i =0; i < selectedDept.length; i++){
		var receivers = selectedDept[i];
		var userDiv = $('<div class="userDiv" id="userDiv"></div>');
		userDiv.append('<p>'+ receivers.sendername +'&nbsp;&nbsp;'+'<button onclick="deleteReceivers('+ i +')">X</button></p>');		
		selDiv.append(userDiv);
	}	
}
// 유저 화면단에서 임의로 status 값도 설정하여 전송하려고 만듬.
function getStatusDropdownHTML(index, defaultStatus) {
    // status 선택을 위한 dropdown의 HTML을 반환
    var dropdownHTML;
    if(index === 0){
        dropdownHTML= '<select name="status_' + index + '">' +
        '<option value="1000" ' + (defaultStatus === 1000 ? 'selected' : '') + '>기안</option>' +
        '<option value="2000" ' + (defaultStatus === 2000 ? 'selected' : '') + '>검토</option>' +
        '<option value="3000" ' + (defaultStatus === 3000 ? 'selected' : '') + '>협조</option>' +
        '<option value="4000" ' + (defaultStatus === 4000 ? 'selected' : '') + '>결재</option>' +
        '</select>';    	
    }else{
        dropdownHTML= '<select name="status_' + index + '">' +
        '<option value="2000" ' + (defaultStatus === 2000 ? 'selected' : '') + '>검토</option>' +
        '<option value="3000" ' + (defaultStatus === 3000 ? 'selected' : '') + '>협조</option>' +
        '<option value="4000" ' + (defaultStatus === 4000 ? 'selected' : '') + '>결재</option>' +
        '</select>';    	   	
    }

    return dropdownHTML;
}

function getStatusDropdownHTML2(index, defaultStatus) {
    // status 선택을 위한 dropdown의 HTML을 반환
    var dropdownHTML = '<select name="status_' + index + '">' +
        '<option value="4224" ' + (defaultStatus === 4224 ? 'selected' : '') + '>확인</option>' +
        '<option value="4112" ' + (defaultStatus === 4112 ? 'selected' : '') + '>참조</option>' +
        '</select>';    	
    return dropdownHTML;
}

function confirmSelection(){
	if(selectedUsers.length == 1 && selectedUsers[0].id.toString() !== uId){
		alert('결재자가 한명만 있을 경우, 기안자로 설정해야합니다.');
		return;
	}else if(selectedUsers.length > 0 && selectedUsers[0].id.toString() !== uId){
		alert('기안자는 문서를 작성하는 본인만이 가능합니다.');
		return;
	}
    // 각 유저별로 select 태그의 값을 가져와서 status값도 전송
    for (var i = 0; i < selectedUsers.length; i++) {
        var status = $('[name="status_' + i + '"]').val();
        selectedUsers[i].status = status;
    }
	//체크박스 값 부모창으로 보내기 위해
	var checkboxes = document.getElementsByName('docattr');
	for(var j=0; j<checkboxes.length; j++){
		if(checkboxes[j].checked){
			checkedValues.push(checkboxes[j].value);
			console.log(checkedValues);
		}
	}
	//셀렉트박스 값[발신명의, 발신부서ID]
	var deptid = $('#senderSelect').data('deptid');
	var sendername = $('#senderSelect').data('sendername');
	
	selectedValue= {deptid: deptid, sendername: sendername};
	var data = {
			users: selectedUsers,
			selectedApFolder: selectedApFolder,
			checkedValues: checkedValues,
			selectedDept: selectedDept,
			selectedValue: selectedValue
		}
		window.opener.postMessage(data,"*");
	
	if(SendID !== ''){
		$.ajax({
			type: 'post',
			url: '/kwangs/SaveRceptFlowUseInfoTemp',
			contentType: 'application/json',
			data: JSON.stringify(selectedUsers),
			success: function(){
				console.log("success");
                setTimeout(function() {
                    window.close();
                }, 500); // 1000 밀리초 (1초) 후에 창을 닫습니다.
			},
			error : function(error){
				console.error("Error seding clicked users to server:",error);
			}
		});		
	}else{
		$.ajax({
			type: 'post',
			url: '/kwangs/SaveFlowUseInfoTemp',
			contentType: 'application/json',
			data: JSON.stringify(selectedUsers),
			success: function(){
				console.log("success");
                setTimeout(function() {
                    window.close();
                }, 500); // 1000 밀리초 (1초) 후에 창을 닫습니다.
			},
			error : function(error){
				console.error("Error seding clicked users to server:",error);
			}
		});		
	}

	//window.close();
}

//유저를 배열에서 삭제하는 함수
function deleteUser(index) {
    selectedUsers.splice(index, 1); //배열요소 하나만 삭제
    updateSelectedUsersUI();
}
//수신처 삭제
function deleteReceivers(index){
	selectedDept.splice(index,1);
	updateselectedDept();
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