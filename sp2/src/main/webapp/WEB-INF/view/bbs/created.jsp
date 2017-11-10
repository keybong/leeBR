<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp=request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
body{
	font-family: 나눔 고딕;
}
</style>

<script type="text/javascript">
function sendOk(){
	var f=document.createdForm;
	
	var str=f.subject.value
	if(str.length==0){
		alert("제목을 입력해주세요");
		f.subject.focus();
		return;
	}
	var str=f.name.value
	if(str.length==0){
		alert("이름을 입력해주세요");
		f.name.focus();
		return;
	}
	var str=f.content.value
	if(str.length==0){
		alert("내용을 입력해주세요");
		f.content.focus();
		return;
	}
	var str=f.pwd.value
	if(str.length==0){
		alert("비밀번호를 입력해주세요");
		f.pwd.focus();
		return;
	}
	
	
	<%-- if(mode=="created")
		f.action="<%=cp%>/bbs/";
	else if(mode=="update")
		f.action=""; --%>

	f.action="<%=cp%>/bbs/${mode}";
	f.submit();
}
</script>


</head>
<body>
<div align="center">
<form name="createdForm" method="post">
<table style="width: 700px; border-collapse: collapse;">
	<tr style="border-bottom: 1px solid #BDBDBD;"><td colspan="2"><h3>| 게시판</h3></td></tr>
	
	<tr height="40px;" style="border-bottom: 1px solid #777777;">
		<td bgcolor="#DDDDDD" width="20%;" style="text-align: center;">제목</td>
		<td> <input type="text" name="subject" style="width: 460px;height: 20px; margin-left: 3px; border: 1px solid #999999; border-radius: 4px;"> </td>
	</tr>
	<tr height="40px;" style="border-bottom: 1px solid #777777;">
		<td bgcolor="#DDDDDD" style="text-align: center;">작성자</td>
		<td> <input type="text" name="name" style="height: 20px; margin-left: 3px; border: 1px solid #999999; border-radius: 4px;"> </td>
	</tr>
	<tr height="110px;" style="border-bottom: 1px solid #777777;">
		<td bgcolor="#DDDDDD" style="text-align: center; vertical-align: top;">내용</td>
		<td> <textarea name="content" style="width: 400px; height: 100px; margin-left: 3px; border: 1px solid #999999; border-radius: 4px;"></textarea> </td>
	</tr>
	<tr height="40px;" style="border-bottom: 1px solid #777777;">
		<td bgcolor="#DDDDDD" style="text-align: center;">패스워드</td>
		<td> <input type="password" name="pwd"  style="height: 20px; margin-left: 3px; border: 1px solid #999999; border-radius: 4px;"> </td>
	</tr>
	<tr height="40px;">
		<td colspan="2" align="center">
			<button type="button" onclick="sendOk();">등록하기                </button>
			<button type="reset">다시입력</button>
			<button type="button" onclick="javascript:location.href='<%=cp%>/bbs/list';">등록취소</button>
		</td>
	</tr>
</table>
</form>
</div>

</body>
</html>