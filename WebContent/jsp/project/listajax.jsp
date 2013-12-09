<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>
<%@ page import = "db.dto.projectDTO" %>
<%@ page import = "util.common" %>
<% List list = (List)request.getAttribute("list"); %>

<% 
if (list.size() != 0) {

	for(int i=0;i<list.size();i++) { 
		projectDTO dto = (projectDTO)list.get(i);
%>
	<tr id="<%=dto.getRm() %>">
		<td><%=dto.getNo() %></td>
		<td>
		<%if (dto.getCate().equals("01")) {%>JAVA
		<%} else if (dto.getCate().equals("02")) {%>PHP
		<%} else if (dto.getCate().equals("03")) {%>ASP
		<%} else if (dto.getCate().equals("04")) {%>IT(웹) 기타
		<%} else if (dto.getCate().equals("05")) {%>아이폰
		<%} else if (dto.getCate().equals("06")) {%>안드로이드
		<%} else if (dto.getCate().equals("07")) {%>모바일웹
		<%} else if (dto.getCate().equals("08")) {%>IT(모바일) 기타
		<%} else if (dto.getCate().equals("09")) {%>SNS
		<%} else if (dto.getCate().equals("10")) {%>블로그
		<%} else if (dto.getCate().equals("11")) {%>마케팅 기타
		<%} else if (dto.getCate().equals("12")) {%>3D
		<%} else if (dto.getCate().equals("13")) {%>웹디자인
		<%} else if (dto.getCate().equals("14")) {%>애니메이션
		<%} else if (dto.getCate().equals("15")) {%>디자인 기타
		<%} else if (dto.getCate().equals("16")) {%>기획
		<%} else if (dto.getCate().equals("17")) {%>판매
		<%} else if (dto.getCate().equals("18")) {%>분석
		<%} else if (dto.getCate().equals("19")) {%>영업 기타
		<%} else if (dto.getCate().equals("20")) {%>기타
		<%} %>
		</td>
		<td><a href="/member.do?method=getListDetail&user_id=<%=dto.getWriter_id() %>"><%=dto.getWriter_id() %></a></td>
		<td>
		<a href="/project.do?method=getListDetail&no=<%=dto.getNo()%>&cate=<%=request.getParameter("cate")%>">
		<%if (dto.getTitle().length() > 80) {%>
			<%=common.cutString(dto.getTitle(),80) %>...
		<%} else { %>
			<%=dto.getTitle()%>
		<%} %>
		</a>
		</td>
		<td>
		<%if (dto.getStatus().equals("01")) {%>
			모집중
		<%} else if (dto.getStatus().equals("02")) {%>
			작업중
		<%} else if (dto.getStatus().equals("03")) {%>
			완료
		<%} else if (dto.getStatus().equals("04")) {%>
			취소
		<%}	%>
		</td>
		<td><%=dto.getApply_cnt() %></td>
		<td><%=dto.getAmount() %></td>
		<td><%=dto.getJobdate() %></td>
		<td><%=dto.getRegist_date() %></td>
	</tr>
	
<% 	}
} else {
%>
<tr id="0"><td colspan="9">더이상 목록이 없습니다.</td></tr>
<%	
}	
%>
