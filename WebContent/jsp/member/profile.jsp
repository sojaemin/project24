<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>
<%@ page import = "util.common" %>
<%@ page import = "db.dto.memberDTO" %>
<%@ page import = "javax.servlet.http.HttpSession" %>
<% List list = (List)request.getAttribute("list"); %>
<% String req_cate = request.getParameter("cate"); 
String search_val = common.nulltovoid(request.getParameter("search_val"));
String gubun = session.getAttribute("gubun").toString();
if (gubun.equals("")) {
	gubun = "F";
}
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
<div class="well">
<div class="container" align="center">
	<h1>내 프로필<small></small>
	</h1>
</div>
</div>

<div class="container">

<div class="container-fluid">
      <div class="row-fluid">
        <div class="span1">
          
        </div><!--/span-->
        <div class="span10">



<div class="tabbable"> <!-- Only required for left/right tabs -->
  <ul class="nav nav-tabs">
    <li class="active"><a href="/member.do?method=getProfile&gubun=<%=gubun%>">소개</a></li>
    <% if (gubun.equals("C")) {%>
    	<li><a href="/member.do?method=getApplyList&gubun=<%=gubun%>">등록한 프로젝트</a></li>
   	<%} else { %>
   		<li><a href="/member.do?method=getApplyList&gubun=<%=gubun%>">지원한 프로젝트</a></li>
	<%} %>
    <li><a href="/member.do?method=getWorkList&gubun=<%=gubun%>">작업중인 프로젝트</a></li>
    <li><a href="/member.do?method=getDoneList&gubun=<%=gubun%>">작업완료한 프로젝트</a></li>
  </ul>
  
  <div class="tab-content">
    <div class="tab-pane active" id="tab1">
       <div>
          <% 
if (list.size() != 0) {

	for(int i=0;i<list.size();i++) { 
		memberDTO dto = (memberDTO)list.get(i);
%>
<ul>
	<li class="span4">
				<%if (dto.getPro_img() == null || dto.getPro_img().equals("") || dto.getPro_img().equals("null")) {%>
				이미지가 없습니다. 이미지를 등록하여 주세요.
				<%} else { %>
			<a href="/data/<%=dto.getPro_img()%>" class="thumbnail">
			<img data-src="holder.js/300x200" src="/data/<%=dto.getPro_img()%>" alt=""></a></li>
				<%} %>
			<li class="span4">
			아이디 : <h1><%=dto.getId()%></h1><br/><br/>
			회원구분 : <%if (dto.getGubun().equals("F")) {%>프리랜서<%} else if (dto.getGubun().equals("C")){%>기업회원<%} %><br/><br/>
			카테고리 :
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
			<br/><br/>
			E-mail : <%=common.cutStringInstead(dto.getEmail(),3) %><br/><br/>
			핸드폰번호 : <%=common.cutStringInstead(dto.getHandphone(),3) %><br/><br/>
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



<ul>
<li class="span10"><br/>
	<label class="control-label" for="input01">자기소개 : </label>
	<div class="controls">
		<%=dto.getProfile()%>
		<p class="help-block"></p>
	</div>
</li>
</ul>


<% 	}
} else {
%>
조회할 데이타가 없습니다.
<%	
}	
%>
				 
          </div>
    </div>
  
    
  </div>
</div>
         
         
         
          
		 </div><!--/span-->
		 
     </div><!--/row-->
    </div>
</div>

<%@include file="/jsp/common/footer.jsp"%>
</body>
</html>
