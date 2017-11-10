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

<script type="text/javascript">
function send(){
	
	var f=document.scoreForm;
	
	
	var s=f.hak.value;
	if(s.length==0){
		alert("학번을 입력해주세요");
		f.hak.focus();
		return;
	}
	
	if(! s==/^[\d]$/){
		alert("숫자만 입력해주세요");
		f.hak.focus();
		return;
	}
	
	
	var s=f.name.value;
	if(s.length==0){
		alert("이름을 입력해주세요");
		f.name.focus();
		return;
	}
	if(! s==/^[기-힣]$/){
		alert("이름은 한글만 입력해 주세요");
		f.name.focus();
		return;
	}
	
	var s=f.birth.value;
	if(s.length==0){
		alert("생년월일을 입력해 주세요");
		f.birth.focus();
		return;
	}
	
	
	var s=f.kor.value;
	if(s.length==0){
		alert("국어점수를 입력해주세요");
		f.kor.focus();
		return;
	}
	if( s==/^[\D]$/ || s<0 || s>100){
		alert("숫자 그리고 100 이하만 입력해 주세요");
		f.kor.focus();
		return;
	}
	
	var s=f.eng.value;
	if(s.length==0){
		alert("영어점수를 입력해주세요");
		f.eng.focus();
		return;
	}
	if( s==/^[\D]$/ || s<0 || s>100){
		alert("숫자 그리고 100 이하만 입력해 주세요");
		f.eng.focus();
		return;
	}
	
	var s=f.mat.value;
	if(s.length==0){
		alert("수학점수를 입력해주세요");
		f.mat.focus();
		return;
	}
	if( s==/^[\D]$/ || s<0 || s>100){
		alert("숫자 그리고 100 이하만 입력해 주세요");
		f.mat.focus();
		return;
	}
	
	var m="${mode}";
	if(m=="insert")
		f.action="<%=cp%>/score/write";
	else if(m=="update")
		f.action="<%=cp%>/score/update";
	f.submit();
}


</script>

</head>
<body>

<form name="scoreForm" method="post">
<table style="width: 700px; margin: 30px auto 0px;">
	<tr height="35">
		<td align="left">
			<h3>성적처리</h3>
		</td>
	</tr>
	<tr height="35">
		<td align="center" width="20%">
		학번
		</td>
		<td width="50%" align="left">
			<c:if test="${dto.hak==null}">
			<input type="text" name="hak" value="${dto.hak}">
			</c:if>
			<c:if test="${dto.hak!=null}">
			<input type="text" name="hak" value="${dto.hak}" readonly="readonly">
			</c:if>
		</td>
	</tr>
	<tr height="35">
		<td align="center"  width="20%">
		이름
		</td>
		<td width="50%" align="left">
			<input type="text" name="name" value="${dto.name}">
		</td>
	</tr>
	<tr height="35">
		<td align="center"  width="20%">
		생년월일
		</td>
		<td width="50%" align="left">
			<input type="text" name="birth" value="${dto.birth}">
		</td>
	</tr>
	<tr height="35">
		<td align="center"  width="20%">
		국어
		</td>
		<td width="50%" align="left">
			<input type="text" name="kor" value="${dto.kor}">
		</td>
	</tr>
	<tr height="35">
		<td align="center"  width="20%">
		영어
		</td>
		<td width="50%">
			<input type="text" name="eng" value="${dto.eng}">
		</td>
	</tr>
	<tr height="35">
		<td align="center"  width="20%">
		수학
		</td>
		<td width="50%">
			<input type="text" name="mat" value="${dto.mat}">
		</td>
	</tr>
	<tr height="35" align="center">
	
		<td align="center">
			<button type="button" onclick="send();">입력</button>
			<button type="reset">다시입력</button>
			<button type="javascript:location.href='<%=cp%>/score/list';">등록취소</button>
		</td>
		
	</tr>
</table>
</form>

</body>
</html>