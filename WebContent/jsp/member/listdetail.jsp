<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>
<%@ page import = "util.common" %>
<%@ page import = "db.dto.memberDTO" %>
<% List list = (List)request.getAttribute("list"); %>
<% String req_cate = request.getParameter("cate");
String user_id = request.getParameter("user_id");
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en"><!-- bootstrap -->
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원 가입</title>
<%@include file="/jsp/common/header.jsp"%>
</head>
<script type="text/javascript">

</script>

<body style="background-color: white;" >
<%@include file="/jsp/common/top.jsp"%>
          <% 
if (list.size() != 0) {

	for(int i=0;i<list.size();i++) { 
		memberDTO dto = (memberDTO)list.get(i);
%>
<div class="well">
<div class="container" align="center">
	<h1>
		<%if (dto.getGubun().equals("F")) {%>프리랜서<%} else if (dto.getGubun().equals("C")){%>기업회원<%} %>
		<small></small>
	</h1>
</div>
</div>

<div class="container">

<div class="container-fluid">
      <div class="row-fluid">
 <%if (req_cate == null || req_cate.equals("")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>" >전체보기</a><%if (req_cate == null || req_cate.equals("")) {%></code><%} %>&nbsp;
IT(웹)&nbsp;
<%if (req_cate != null && req_cate.equals("01")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>&cate=01" >JAVA</a><%if (req_cate != null && req_cate.equals("01")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("02")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>&cate=02" >PHP</a><%if (req_cate != null && req_cate.equals("02")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("03")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>&cate=03" >ASP</a><%if (req_cate != null && req_cate.equals("03")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("04")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>&cate=04" >IT(웹) 기타</a><%if (req_cate != null && req_cate.equals("04")) {%></code><%} %>&nbsp;
IT(모바일)&nbsp;
<%if (req_cate != null && req_cate.equals("05")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>&cate=05" >아이폰</a><%if (req_cate != null && req_cate.equals("05")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("06")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>&cate=06" >안드로이드</a><%if (req_cate != null && req_cate.equals("06")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("07")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>&cate=07" >모바일웹</a><%if (req_cate != null && req_cate.equals("07")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("08")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>&cate=08" >IT(모바일) 기타</a><%if (req_cate != null && req_cate.equals("08")) {%></code><%} %>&nbsp;
마케팅&nbsp;
<%if (req_cate != null && req_cate.equals("09")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>&cate=09" >SNS</a><%if (req_cate != null && req_cate.equals("09")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("10")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>&cate=10" >블로그</a><%if (req_cate != null && req_cate.equals("10")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("11")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>&cate=11" >마케팅 기타</a><%if (req_cate != null && req_cate.equals("11")) {%></code><%} %>&nbsp;
디자인&nbsp;
<%if (req_cate != null && req_cate.equals("12")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>&cate=12" >3D</a><%if (req_cate != null && req_cate.equals("12")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("13")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>&cate=13" >웹디자인</a><%if (req_cate != null && req_cate.equals("13")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("14")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>&cate=14" >애니메이션</a><%if (req_cate != null && req_cate.equals("14")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("15")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>&cate=15" >디자인 기타</a><%if (req_cate != null && req_cate.equals("15")) {%></code><%} %>&nbsp;
영업&nbsp;
<%if (req_cate != null && req_cate.equals("16")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>&cate=16" >기획</a><%if (req_cate != null && req_cate.equals("16")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("17")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>&cate=17" >판매</a><%if (req_cate != null && req_cate.equals("17")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("18")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>&cate=18" >분석</a><%if (req_cate != null && req_cate.equals("18")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("19")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>&cate=19" >영업 기타</a><%if (req_cate != null && req_cate.equals("19")) {%></code><%} %>&nbsp;
기타&nbsp;
<%if (req_cate != null && req_cate.equals("20")) {%><code><%} %><a href="/member.do?method=getList&gubun=<%=dto.getGubun()%>&cate=20" >기타</a><%if (req_cate != null && req_cate.equals("20")) {%></code><%} %>&nbsp;
    <div>
          <div class="tabbable"> <!-- Only required for left/right tabs -->
<br/>

<ul>
	<li class="span4">
				<%if (dto.getPro_img() == null || dto.getPro_img().equals("") || dto.getPro_img().equals("null")) {%>
				이미지가 없습니다. 이미지를 등록하여 주세요.
				<%} else { %>
			<a href="/data/<%=dto.getPro_img()%>" class="thumbnail">
			<img data-src="holder.js/300x200" src="/data/<%=dto.getPro_img()%>" alt=""></a></li>
				<%} %>
			<li class="span4">
			<dl class="dl-horizontal">
			  <dt>아이디</dt>
  			  <dd><%=dto.getId()%></dd>
  			  <br/>
  			  <dt>회원구분</dt>
  			  <dd><%if (dto.getGubun().equals("F")) {%>프리랜서<%} else if (dto.getGubun().equals("C")){%>기업회원<%} %></dd>
  			  <br/>
  			  <dt>카테고리</dt>
  			  <dd>
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
  			  </dd>
  			  <br/>
  			  <dt>E-mail</dt>
  			  <dd><%=common.cutStringInstead(dto.getEmail(),3) %></dd>
  			  <br/>
  			  <dt>핸드폰번호</dt>
  			  <dd><%=common.cutStringInstead(dto.getHandphone(),3) %></dd>
  			  <br/>
  			  <dt>소개</dt>
  			  <dd><%=dto.getProfile()%></dd>
  			  <br/>
			</dl>
			
	</li>
</ul>
<ul>
<!-- <li class="span10"><br/>
	<h1>총 지원횟수:<%=dto.getApply_cnt()%>, 총 발탁된 횟수:<%=dto.getSelected_cnt()%>, 총 작업완료 횟수:<%=dto.getDone_cnt()%></h1>
</li>-->
</ul>
<!-- <div class="control-group">
	<label class="control-label" for="input01">서명 : </label>
	<div class="controls">
		<textarea name=signature id=signature class=tx rows=3 style='width:95%;'  itemname='서명'><%=dto.getSignature()%></textarea>
		<p class="help-block"></p>
	</div>
</div>-->




<% 	}
} else {
%>
조회할 데이타가 없습니다.
<%	
}	
%>
				 
          
		 </div><!--/span-->
     </div><!--/row-->
    </div>
</div>
</div>
<br/>
<br/>
<br/>
<br/>
<br/>
<%@include file="/jsp/common/footer.jsp"%>
</body>
</html>
