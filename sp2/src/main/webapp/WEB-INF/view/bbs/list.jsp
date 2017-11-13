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

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js">
	

</script>


<script type="text/javascript">

function send(f){
	f.action="<%=cp%>/bbs/list";
	f.submit();	
}

$(function (){
	$("#chkAll").click(function (){
		if(this.checked==true){
			$("input[name=nums]").each(function(){//each 반복문 단, 같은 이름
				this.checked=true;
			});
		}else {
			$("input[name=nums]").each(function(){
				this.checked=false;
			});
		}
	});
	
	$("#btnDeleteList").click(function(){
		var cnt=$("input[name=nums]:checkbox:checked").length;
		
		if(cnt==0){
			alert("삭제할 게시물을 먼저 선택 하세요 !!!");
			return;
		}
		
		var f=document.deleteListForm;
		f.action="<%=cp%>/bbs/deleteList";
		f.submit();
		
	});
	
});

</script>


</head>
<body>



<div align="center">

<table style="width: 1300px;">
	<tr><td colspan="6"><h3>| 게시판</h3></td></tr>
	
	<tr style="width: 100%;">
		<td colspan="3"><button type="button" id="btnDeleteList">삭제</button></td>
		<td colspan="3" align="right">
		<form name="selectListFrom" method="post">
			<select name="rows" onchange="send(this.form);">
				<option value="5" ${rows==5? "selected='selected'":""}>5개씩 출력</option>
				<option value="10" ${rows==10? "selected='selected'":""}>10개씩 출력</option>
				<option value="20" ${rows==20? "selected='selected'":""}>20개씩 출력</option>
				<option value="50" ${rows==50? "selected='selected'":""}>50개씩 출력</option>
			</select>
			<input type="hidden" name="searchKey" value="${searchKey}">
			<input type="hidden" name="searchValue" value="${searchValue}">
		</form>
		</td>
	</tr>
	</table>
	<form name="deleteListForm" method="post">
	<table style="width: 1300px;">
	<tr align="center" height="40px;" class="list" style="background-color: #6799FF">
		<td width="5%;"><input type="checkbox" name="allCheck" id="chkAll"></td>
		<td width="7%;" style="color: #ffffff; text-align: center;">번호</td>
		<td width="48%;"  style="color: #ffffff; text-align: center;">제목</td>
		<td width="15%;"  style="color: #ffffff; text-align: center;">작성자</td>
		<td width="15%;"  style="color: #ffffff; text-align: center;">작성일</td>
		<td width="10%;"  style="color: #ffffff; text-align: center;">조회수</td>
	</tr>
	<c:forEach var="dto" items="${list}">
	<tr align="center" height="40px;" style="border-bottom: 1px solid #gray">
		<td class=""><input type="checkbox" name="nums" value="${dto.num}"></td>
		<td>${dto.listNum}</td>
		<td>
			<a href="${articleUrl}&num=${dto.num}">${dto.subject}</a>
			<c:if test="${dto.gap<1}">
				<img src="<%=cp%>/resource/images/new.gif">
			</c:if>
		</td>
		<td>${dto.name}</td>
		<td>${dto.created}</td>
		<td>${dto.hitCount}</td>
	</tr>
	</c:forEach>
	</table>
	<input type="hidden" name="page" value="${page}">
	<input type="hidden" name="rows" value="${rows}">
	</form>
	
	<table style="width: 1300px;">
	<tr>
		<td align="center" colspan="6">${paging}</td>
	</tr>
	
	<tr>
		<td colspan="6"  align="left">
		<form name="listFrom" method="post">
			<select name="searchKey">
				<option value="subject">제목</option>
				<option value="name">작성자</option>
				<option value="content">내용</option>
				<option value="created">작성일</option>
			</select>
			<input type="text" name="searchValue">
			<input type="hidden" name="rows" value="${rows}">
			<button type="button" onclick="send(this.form);">검색</button>
			<button type="button"
		 onclick="javascript:location.href='<%=cp%>/bbs/created'">글올리기</button>
		</form>
	</tr>


</table>

</div>
</body>
</html>