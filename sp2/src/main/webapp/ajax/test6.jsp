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
		var n1=$("#num1").val();
		
		var o=$("#oper").val();
		
		var n2=$("#num2").val();
		
		var q="num1="+n1+"&num2="+n2+"&oper="+o;
		
		var url="test4_ok.jsp?"+q;

		//POST(text) 방식 AJAXㅋ
		//url 서버주소 , num1은 파라미터 이름(key) : 값
		$.post(url,{num1:n1, num2:n2, oper:o},function(data){
			$("#resultLayout").html(data)
		});
	});
});
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