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
button{
	
	background-color: #eeeeee;
	
}


</style>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>

<script type="text/javascript">
function send(f){
	f.action="<%=cp%>/board/list";
	f.submit();
}


</script>


</head>
<body>



<div align="center">

<table style="width: 1300px;">
	<tr><td colspan="6"><h3>| 질문과 답변</h3></td></tr>
	
	</table>
	<table style="width: 1300px;">
	<tr align="center" height="40px;" class="list" style="background-color: #6799FF">
		<td width="7%;" style="color: #ffffff; text-align: center;">번호</td>
		<td width="48%;"  style="color: #ffffff; text-align: center;">제목</td>
		<td width="15%;"  style="color: #ffffff; text-align: center;">작성자</td>
		<td width="15%;"  style="color: #ffffff; text-align: center;">작성일</td>
		<td width="10%;"  style="color: #ffffff; text-align: center;">조회수</td>
	</tr>
	<c:forEach var="dto" items="${list}">
	<tr align="center" height="40px;" style="border-bottom: 1px solid #gray">
		<td>${dto.listNum}</td>
		<td>
			<a href="${articleUrl}&boardNum=${dto.boardNum}">${dto.subject}</a>
<%-- 			<c:if test="${dto.gap<1}">
				<img src="<%=cp%>/resource/images/new.gif">
			</c:if> --%>
		</td>
		<td>${dto.name}</td>
		<td>${dto.created}</td>
		<td>${dto.hitCount}</td>
	</tr>
	</c:forEach>
	</table>
	
	<table style="width: 1300px;">
	<tr>
		<td align="center" colspan="6">${paging}</td>
	</tr>
	
	<tr>
		<td colspan="6"  align="left">
		<form name="searchList" method="post">
			<select name="searchKey">
				<option value="subject">제목</option>
				<option value="name">작성자</option>
				<option value="content">내용</option>
				<option value="created">작성일</option>
			</select>
			<input type="text" name="searchValue">
			<button type="button" onclick="send(this.form);">검색</button>
			<button type="button"
		 onclick="javascript:location.href='<%=cp%>/board/created'">글올리기</button>
		</form>
	</tr>


</table>

</div>
</body>
</html>