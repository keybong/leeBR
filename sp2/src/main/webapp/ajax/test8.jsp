<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>

<script type="text/javascript">
$(function(){
	$("#btnSend").click(function(){
		var q=$("form[name=calcForm]").serialize();//네임으로 접근 key 는 name
		var url="test4_ok.jsp";
		
		$.ajax({
			type:"post"
			,url:url
			,data:q
			,beforeSend:check
			,success:function(data){
				$("#resultLayout").html(data);
			}
			,error:function(e){
				console.log(e.responseText);
			}
		});
	});
});

function check(){
	if(! $("#num1").val()){
		$("#num1").focus();
		return false;
	}
	
	if(! $("#num2").val()){
		$("#num2").focus();
		return false;
	}
	
	return true;
}


</script>

</head>
<body>

<form name="calcForm">
	<input type="text" id="num1" name="num1">
	<select id="oper" name="oper">
		<option value="add">더하기</option>
		<option value="sub">빼기                </option>
		<option value="mul">곱하기</option>
		<option value="div">나누기                           </option>
	</select>
	<input type="text" id="num2" name="num2">
	<input type="button" value="결과" id="btnSend">
</form>
	<br>
	<hr>

	<div id="resultLayout"></div>



</body>
</html>