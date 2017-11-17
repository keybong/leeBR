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

<script type="text/javascript">
function createXMLHttpRequest(){
	var xmlReq=null;
	
	xmlReq = new XMLHttpRequest();
	
	return xmlReq;
}

var xmlHttp;
function sendOk(){
	var n1=document.getElementById("num1").value;
	var n2=document.getElementById("num2").value;
	var o=document.getElementById("oper").value;
	
	var url="test3_ok.jsp";
	var q="num1="+n1+"&num2="+n2+"&oper="+o;
	url+="?"+q;
	xmlHttp = createXMLHttpRequest();
	xmlHttp.onreadystatechange=callback;
	
	xmlHttp.open("get", url, true);
	xmlHttp.send(null);
}

function callback(){
	if(xmlHttp.readyState==4){//응답요청 완료
		if(xmlHttp.status==200){//성공한 경우
			print();
		}
	}
}

function print(){
	var s=xmlHttp.responseText;
	document.getElementById("resultLayout").innerHTML =s;
}
</script>

</head>
<body>


	<input type="text" id="num1" name="num1">
	<select id="oper">
		<option value="add">더하기</option>
		<option value="sub">빼기                </option>
		<option value="mul">곱하기</option>
		<option value="div">나누기                           </option>
	</select>
	<input type="text" id="num2">
	<input type="button" value="결과" onclick="sendOk();">
	<br>
	<hr>

	<div id="resultLayout"></div>



</body>
</html>