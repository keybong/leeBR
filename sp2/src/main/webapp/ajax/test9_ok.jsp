<%@ page contentType="text/xml; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp=request.getContextPath();

	request.setCharacterEncoding("utf-8");
	
	String name=request.getParameter("name");
	String content=request.getParameter("content");
%>

<root>
	<dataCount>5</dataCount>
	<%for(int n=1; n<=5; n++){%>
		<record num="<%=n%>">
			<name><%=n+" : "+name%></name>
			<content><![CDATA[<%=n+" : "+content%>]]></content>
		</record>
	<%}%>
</root>
