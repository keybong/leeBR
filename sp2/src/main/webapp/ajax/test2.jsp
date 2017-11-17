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
	//var n1=document.getElementById("num1").value;
	var n1=document.getElementsByName("num1")[0].value;
	//var n2=document.getElementById("num2").value;
	var n2=num2.value;
	//var o=document.getElementById("oper").value;
	var o=document.getElementsByTagName("select")[0].value;
	
	var url="test2_ok.jsp";
	var q="num1="+n1+"&num2="+n2+"&oper="+o;
	
	xmlHttp = createXMLHttpRequest();
	xmlHttp.onreadystatechange=callback;
	
	xmlHttp.open("post", url, true);
	xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xmlHttp.send(q);
}

function callback(){
	if(xmlHttp.readyState==4){//응답요청 완료
		if(xmlHttp.status==200){//성공한 경우
			print();
		}
	}
}

function print(){
	var xmlDoc=xmlHttp.responseXML;
	var root=xmlDoc.getElementsByTagName("root")[0];
	var num1=root.getElementsByTagName("num1")[0].firstChild.nodeValue;
	var num2=root.getElementsByTagName("num2")[0].firstChild.nodeValue;	
	var oper=xmlDoc.getElementsByTagName("oper")[0].firstChild.nodeValue;
	var ans=xmlDoc.getElementsByTagName("ans")[0].firstChild.nodeValue;
	
	//alert(num1+" : "+num2+" : "+oper+" : "+ans);
	
	var s="<b>"+ans+"</b>";
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