<%@ page contentType="text/xml; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp=request.getContextPath();
	
	int num1= Integer.parseInt(request.getParameter("num1"));
	int num2= Integer.parseInt(request.getParameter("num2"));
	int ans=0;
	String oper=request.getParameter("oper");
	
	switch(oper){
	case "add" : ans=num1+num2; break;
	case "sub" : ans=num1-num2; break;
	case "mul" : ans=num1*num2; break;
	case "div" : ans=num1/num2; break;
	}
%>

<root>
	<num1><%=num1 %></num1>
	<num2><%=num2 %></num2>
	<oper><%=oper %></oper>
	<ans><%=ans %></ans>
</root>