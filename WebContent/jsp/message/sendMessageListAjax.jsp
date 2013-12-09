<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>
<%@ page import = "db.dto.messageDTO" %>
<%@ page import = "util.common" %>
<% List list = (List)request.getAttribute("list"); %>

<% 
if (list.size() != 0) {

	for(int i=0;i<list.size();i++) { 
		messageDTO dto = (messageDTO)list.get(i);
%>
	<tr id="<%=dto.getRm() %>">
		<td></td>
		<td>
			<%=dto.getTo_id() %>
		</td>
		<td><%=dto.getContext() %></td>
		<td><%=dto.getSend_date() %></td>
		<td>
		<% if (dto.getRead_date() == null) {%>
			읽지않음
		<%} else {%>
			<%=dto.getRead_date() %>
		<%}%>
		</td>
	</tr>
	
<% 	}
} else {
%>
<tr id="0"><td colspan="5">더이상 목록이 없습니다.</td></tr>
<%	
}	
%>
