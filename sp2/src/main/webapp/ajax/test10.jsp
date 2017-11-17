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
		var q=$("form[name=guestForm]").serialize();//네임으로 접근 key 는 name
		var url="test10_ok.jsp";
		
		$.ajax({
			type:"post"
			,url:url
			,data:q
			,dataType:"json"
			,beforeSend:check
			,success:function(data){
				//console.log(data);
				var out="데이터 갯수 : "+data.count;
				for(var idx=0; idx<data.list.length; idx++){
					var item=data.list[idx];
					out+="<br>이름: "+item.name;
					out+="<br>내용: "+item.content;
					out+="<br>번호: "+item.num;
					out+="<br>======================<br>";
				}
				$("#resultLayout").html(out);
			}
			,error:function(e){
				console.log(e.responseText);
			}
		});
	});
});

function check(){
	if(! $("#name").val()){
		$("#name").focus();
		return false;
	}
	
	if(! $("#content").val()){
		$("#content").focus();
		return false;
	}
	
	return true;
}


</script>

</head>
<body>

<form name="guestForm">
	이름 : <input type="text" id="name" name="name"><br>
	내용 : <textarea rows="5" cols="50" id="content" name="content"></textarea><br>
	<input type="button" value="등록하기" id="btnSend">
</form>
	<br>
	<hr>

	<div id="resultLayout"></div>



</body>
</html>