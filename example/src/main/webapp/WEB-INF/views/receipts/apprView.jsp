<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var = "path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<%@ include file="../receipts/write.jsp" %>
<button onClick="Appr_Btn();">상신</button>
<hr>
<input type="hidden" name="name" id="ApprRecId" value="${user.name}" />
<input type="hidden" name="id" id="ApprRecName" value="${user.id}" />

<div class="container">
  <h2>수불장</h2>
  <table class="table" id="dataTable">
    <thead>
      <tr>
        <th>품명</th>
        <th>전재고</th>
        <th>입고처</th>
        <th>입고량</th>
        <th>소분</th>
        <th>제조</th>
        <th>현재고</th>
      </tr>
    </thead>
   <tbody>
      <tr>
      <!-- 
        <td><input type="text" name="productname" class="form-control"/></td>   
        <td><input type="text" name="fullstock" class="form-control" /></td>
        <td><input type="text" name="stock" class="form-control" /></td>
        <td><input type="text" name="stockquantity" class="form-control" /></td>
        <td><input type="text" name="subdivision" class="form-control" /></td>
        <td><input type="text" name="manufacturing" class="form-control" /></td>
        <td><input type="text" name="currentstock" class="form-control" /></td>
      -->
      </tr>
    </tbody>
  </table>
  <button class="btn btn-primary" onclick="addRow()">행 추가</button>
</div>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
function addRow() {
    var rowCount = $('#dataTable tbody tr').length + 1;

    var newRow = '<tr>';
    var fields = ["productname", "fullstock", "stock", "stockquantity", "subdivision", "manufacturing", "currentstock"];

    for (var i = 0; i < fields.length; i++) {
        newRow += '<td><input type="text" name="' + fields[i] + '[' + rowCount + ']" class="form-control"/></td>';
    }

    newRow += '</tr>';

    $('#dataTable tbody').append(newRow);
    
}

function Appr_Btn(){
	var recId = $('#ApprRecId').val();
	var recName = $('#ApprRecName').val();

	var approval = [];
    
    $('#dataTable tbody tr').each(function(index, row){
        var productname = $(row).find('input[name^="productname"]').val();
        var fullstock = $(row).find('input[name^="fullstock"]').val();
        var stock = $(row).find('input[name^="stock"]').val();
        var stockquantity = $(row).find('input[name^="stockquantity"]').val();
        var subdivision = $(row).find('input[name^="subdivision"]').val();
        var manufacturing = $(row).find('input[name^="manufacturing"]').val();
        var currentstock = $(row).find('input[name^="currentstock"]').val();

        approval.push({
            id : recId,
            name : recName,
            productname : productname,
            fullstock : fullstock,
            stock : stock,
            stockquantity : stockquantity,
            subdivision : subdivision,
            manufacturing : manufacturing,
            currentstock : currentstock
        });
    });
	
	   console.log('Send data: ', JSON.stringify(approval));
	
	$.ajax({
		type: "post",
		url: "${path}/receipts/apprView",
		data: JSON.stringify(approval),
        contentType: 'application/json',
		success: function(response){
			participant();
		},
        error: function (xhr, status, error) {
            console.log(xhr);
            console.log(status);
            console.log(error);
        }
	})
}

</script>
</body>
</html>