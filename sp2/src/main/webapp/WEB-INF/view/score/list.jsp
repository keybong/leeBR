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
.td{
	border: 1px solid black;
	
}
tr{
	 border-top-left-radius: 5px;
	border-top-right-radius: 5px; 
}
.tdTitle{
	background-color: #cccccc;
}
a{
	
}
a:hover a:active {
	
}
</style>

<script type="text/javascript">
function searchList(){
	var f=document.searchForm;
	
	f.action="<%=cp%>/score/list";
	f.submit();
}

function allCheck(){
	var checkBox=document.getElementsByName("checkBox");
		if(checkBox[0].checked==true){
			for(var i=0 ; i<checkBox.length; i++){
				checkBox[i].checked=false;
			}
		}else{
			for(var i=0 ; i<checkBox.length; i++){
				checkBox[i].checked=true;
			}
		}
	
}


</script>





</head>
<body>

<table style="width: 700px; margin: 30px auto 0px; border-collapse: collapse;">
	<tr align="center" style="text-align: center;">
		<td class="tdTitle"><input type="checkbox" name="allCheck" onchange="allCheck();"></td>
		<td class="tdTitle">학번                             </td>
		<td class="tdTitle">이름</td>
		<td class="tdTitle">생년월일</td>
		<td class="tdTitle">국어</td>
		<td class="tdTitle">영어</td>
		<td class="tdTitle">수학</td>
		<td class="tdTitle">총점</td>
		<td class="tdTitle">평균</td>
		<td class="tdTitle">변경</td>
	</tr>
	<c:forEach var="dto" items="${list}">
	<tr align="center" style="text-align: center;">
		<td class="td"><input type="checkbox" name="checkBox"></td>
		<td class="td">${dto.hak}</td>
		<td class="td">${dto.name}</td>
		<td class="td">${dto.birth}</td>
		<td class="td">${dto.kor}</td>
		<td class="td">${dto.eng}</td>
		<td class="td">${dto.mat}</td>
		<td class="td">${dto.tot}</td>
		<td class="td">${dto.ave}</td>
		<td class="td"><a href="<%=cp%>/score/update?hak=${dto.hak}">수정</a> | <a href="<%=cp%>/score/delete?hak=${dto.hak}">삭제</a></td>
	</tr>
	</c:forEach>
</table>

<form name="searchForm" method="post">
<table style="width: 700px; margin: 20px auto 0px;">
	<tr height="35">
		<td colspan="2">${paging}</td>
	</tr>
	
	<tr height="40">
		<td width="50%">&nbsp;</td>
		<td align="right">
			<select name="searchKey">
				<option value="hak">학번</option>
				<option value="name">이름</option>
				<option value="birth">생년월일</option>
			</select>
			<input type="text" name="searchValue">
			<button type="button" onclick="searchList();">검색</button>
			<button type="button" onclick="javascript:location.href='<%=cp%>/score/write';">성적작성</button>
		</td>
	</tr>
</table>
</form>


</body>
</html>