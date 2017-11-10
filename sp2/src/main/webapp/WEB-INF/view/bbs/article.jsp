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

<link rel="stylesheet" type="text/css" href="<%=cp%>/resource/css/style.css" />



</head>
<body>

<div align="center">
<table style="width: 800px; border-collapse: collapse;">
	<tr height="100px"><td colspan="6" align="left"><h3>| 게시판</h3></td></tr>
	
	<tr height="40px"  style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
		<td align="center" colspan="2">${dto.subject}</td>
	</tr>
	<tr height="30px" style="border-bottom: 1px solid #cccccc;">
		<td align="left">작성자: ${dto.name}</td>
		<td align="right">${dto.created} | 조회 ${dto.hitCount}</td>
	</tr>
	<tr height="300px" style="border-bottom: 1px solid #cccccc; white-space: pre;">
		<td colspan="2" valign="top">${dto.content}</td>
	</tr>
	<tr height="40px" style="border-bottom: 1px solid #cccccc;">
		<td colspan="2">
		이전글: 
		<c:if test="${not empty preReadDto}">
			<a href="javascript:location.href='<%=cp%>/bbs/article?${query}&num=${preReadDto.num}'">${preReadDto.subject}</a>
		</c:if>
		</td>
	</tr>
	<tr height="40px" style="border-bottom: 1px solid #cccccc;">
		<td colspan="2">
		다음글: 
		<c:if test="${not empty nextReadDto}">
		<a href="javascript:location.href='<%=cp%>/bbs/article?${query}&num=${nextReadDto.num}'">${nextReadDto.subject}</a>
		</c:if>
		</td>
	</tr>
	<tr height="30px">
		<td align="right" colspan="2">from: ${dto.ipAddr}</td>
	</tr>
	<tr height="40px">
		<td align="left"> <button type="button" class="btn">수정</button> <button type="button" class="btn">삭제</button> </td>
		<td align="right"> <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/bbs/list?${query}';">리스트</button> </td>
	</tr>
</table>
</div>

</body>
</html>