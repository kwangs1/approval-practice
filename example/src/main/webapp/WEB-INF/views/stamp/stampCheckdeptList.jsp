<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<c:url value='/resources/css/u_tree.css'/>"/>
<style>
.stampFileList ul li {list-style: none;}
.table{border:1px solid black;}
.active{color:green};
</style>
</head>
<body onload="init()">

<button onclick="stampUpload($('.deptLink.active').data('deptid'));">저장</button>
<br><br>
	<label for="file" class="btn-upload" style="cursor:pointer;">파일 업로드</label>
		
	<input type="file" id="file" name="uploadFile" class="uploadFile"
	multiple="multiple" style="display:none;"/>

	<div class="uploadResult">
		<ul>
		</ul>
	</div>
	
<div class="table">
<ul class="tree">
  <%-- 최상위 부서 start --%>
    <c:forEach var="dept" items="${list}">
      <c:if test="${dept.parid eq null}">
        <li id="dept${dept.deptid}">
          <a href="javascript:StampCheckList('${dept.deptid}')" class="deptLink" data-deptid="${dept.deptid}">${dept.deptname}</a>
          <%-- 최상위 하위 부서 start --%>
          <c:forEach var="subDept" items="${list}">
            <c:if test="${subDept.parid eq dept.deptid}">
              <ul>
                <li id="subDept${subDept.deptid}">
                <a href="javascript:StampCheckList('${subDept.deptid}')" class="deptLink" data-deptid="${subDept.deptid}">${subDept.deptname}</a>
                  
                  <%-- 최상위 하위 - 하위 부서 start --%>
                  <c:forEach var="grandDept" items="${list}">
                    <c:if test="${grandDept.parid eq subDept.deptid}">
                      <ul>
                        <li id="grandDept${grandDept.deptid}" class="lastTree">
                        <a href="javascript:StampCheckList('${grandDept.deptid}')" class="deptLink" data-deptid="${grandDept.deptid}">${grandDept.deptname}</a>
                        </li>
                      </ul>
                    </c:if>
                  </c:forEach>
                  <%-- 최상위 하위 - 하위 부서 end --%>
                </li>
              </ul>
            </c:if>
          </c:forEach>
          <%-- 최상위 하위 부서 end --%>
        </li>
      </c:if>
    </c:forEach>
    <%-- 최상위 부서 end --%>
  </ul>
</div>
<br>
<div class="table">
	<div class="stampCheckList"></div>
</div>
	<div class="stampFileList">
		<ul></ul>
	</div>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="<c:url value='/resources/js/stampUpload.js'/>"></script>
<script>
function init(){
	var selectedDeptId = $('.deptLink').data('deptid');
	if(selectedDeptId){
		StampCheckList(selectedDeptId);
	}
}
function write(){
	var url = '<c:url value="/stamp/write"/>';
	window.open(url,'stamp','width=500px, height=500px');
}

function StampCheckList(id){
	$.ajax({
		url: '<c:url value="/stamp/list"/>',
		type: 'get',
		dataType:'json',
		success: function(data){
			var htmls="";
			var hasData = false;
			
			$('a.deptLink').off('click').on('click',function(){
				$('a.deptLink').removeClass('active');
				$(this).addClass('active');
				var checkDept = $(this).data('deptid');
				
				for(var i in data){
					var deptid = data[i].id;
					var fno = data[i].fno;
					var type = data[i].type;
					var name = data[i].name;
					

					var localName = deptid+"."+name;
					if(checkDept === deptid){
						hasData = true;
						htmls += "<div>"
						htmls += "<a href='javascript:void(0)' class='Link' data-name='"+name+"' data-id='"+deptid+"'>";
						htmls += name+"</a>&nbsp;";
						htmls += "<button onclick='StampDeleted(\"" + deptid + "\", \"" + name + "\", " + fno + ")'>제거</button>&nbsp;";
						htmls += "<button onclick='download(\"" + localName + "\")'>다운</button></div><br>";
					}
				}//end for 
				if(hasData){
					$('.stampCheckList').html(htmls);
					$('.stampFileList').html('');
				}else{
					$('.stampCheckList').html('<p>해당 부서에는 관인이 존재하지 않습니다.</p>');
					$('.stampFileList').html('');
				}
				
				getAttachList();
			});//end a.deptLink click function	
			
			if(id){
				$('a.deptLink[data-deptid="' + id + '"]').trigger('click');
			}
		},
		error: function(xhr, status, error){
			console.log(xhr);
			console.log(status);
			console.log(error);
		}
	})
}
var lastClickedName = null;
function getAttachList(){
	$.getJSON("/kwangs/stamp/getAttachList", function(arr){
		$('a.Link').off('click').on('click',function(){
			var name = $(this).data('name');
			
			if(lastClickedName === name){
				$('.stampFileList').html('');
				lastClickedName = null
				return;
			}
			lastClickedName = name;
			console.log(lastClickedName);
			
			var str = "";
			$(arr).each(function(i, obj){	
				if(name === obj.name){
					var localName = obj.id+"."+obj.name;
					var fileCallPath = encodeURIComponent("s_" + localName);
					str += "<li style='list-style: none;'"
					str += "data-deptid='"+obj.id+"' data-fno='"+obj.fno+"' data-name='"+obj.name+"' data-type='"+obj.type+"'>";
					str += "<img src='/kwangs/display?name="+fileCallPath+"'>";
					str += "</div></li>";	
				}
			});//end each
			$('.stampFileList').html(str);
		});
	});
}
function stampUpload(id){
	 var formData = new FormData();
	 $('.uploadResult ul li').each(function(i,obj){
	  var obj_ = $(obj);
	  
	  var id = obj_.data("deptid");
	  var fno = 0;
	  var type = obj_.data("type");
	  var name = obj_.data("name");
	  
	  formData.append("stamp["+ i +"].id",id);
	  formData.append("stamp["+ i +"].fno",fno);
	  formData.append("stamp["+ i +"].type",type);
	  formData.append("stamp["+ i +"].name",name);
	 })
	if(uploadedFiles.length === 0){
		alert("파일을 업로드 후 저장을 해주세요!");
		return false;
	}
	 $.ajax({
		 url: '<c:url value="/stamp/write.do"/>',
		 type: 'post',
		 data: formData,
		 processData: false,
		 contentType: false,
		 success: function(response){
		    console.log(response);
		    alert("관인이 등록되었습니다.");
		    isSubmitted = true;
		    $('.uploadResult ul').empty();
			if(id){
				StampCheckList(id);
			}
		 },
		 error: function(xhr, status){
			alert("관인 등록에 실패하였습니다.");
		    console.log(xhr);
		    console.log(status);
		 }
	 })
}

function StampDeleted(id,name,fno){
	if(confirm("삭제 하시겠습니까?")){
		alert('삭제가 되었습니다.');
		$.ajax({
			url: '<c:url value="/stamp/InfoDeleteInfo"/>',
			type: 'post',
			data: {id: id, name: name, fno: fno},
			success: function(response){
				console.log(response);
				console.log(id+" : "+name+" : "+fno);
				console.log("deleted deptid? "+id);
				if(id){
					StampCheckList(id);
				}
			},
			error: function(xhr,status,error){
				console.log(xhr);
				console.log(error);
			}
		});
	}else{
		return false;
	}
}

function download(name){
	var url = '<c:url value="/stamp/download"/>';
	url += '?name='+name;
	self.location= url;
}
</script>
</body>
</html>