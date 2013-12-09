<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>
<%@ page import = "db.dto.projectDTO" %>
<%@ page import = "db.dto.applyDTO" %>
<%@ page import = "util.common" %>
<% List list = (List)request.getAttribute("list");
List list_apply = (List)request.getAttribute("list_apply");
 String req_cate = common.nulltovoid(request.getParameter("cate"));
 String modifyRight = request.getAttribute("modifyRight").toString();
 String no = common.nulltovoid(request.getParameter("no"));
 String sess_user_id = "";
 String selectFreelancerYN = "N";
 String status = "00";
 if (session.getAttribute("login_id") == null || session.getAttribute("login_id").equals("")) {
	 sess_user_id = "";
 } else {
	 sess_user_id = (String)session.getAttribute("login_id");
 }
 %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en"><!-- bootstrap -->
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>프로젝트24</title>
<%@include file="/jsp/common/header.jsp"%>
</head>
<script type="text/javascript">
function goApply() {
	<%if (session.getAttribute("login_id") == null || session.getAttribute("login_id").equals("")) { %>
		alert("로그인 하셔야 지원 가능 합니다.");
	<%} else {%>
	 	if (document.apply_form.apply_context.value.length < 1) {
	        alert('내용을 입력하여 주세요.');
	        document.apply_form.apply_context.focus();
	        return false;
	    }
	 
	 	document.apply_form.action = "/apply.do?method=insertNew";
		document.apply_form.submit();
		
	<%}%>
}

function goDel(del_no) {
	<%if (session.getAttribute("login_id") == null || session.getAttribute("login_id").equals("")) { %>
		alert("로그인 하셔야 삭제 가능 합니다.");
	<%} else {%>
		document.apply_form.apply_no.value = del_no;
	 	document.apply_form.action = "/apply.do?method=delApply";
		document.apply_form.submit();
		
	<%}%>
}

function goMod(mod_no, cont) {
	<%if (session.getAttribute("login_id") == null || session.getAttribute("login_id").equals("")) { %>
		alert("로그인 하셔야 수정 가능 합니다.");
	<%} else {%>
		document.apply_form.apply_no.value = mod_no;
		document.apply_form.apply_text.value = cont;
	 	document.apply_form.action = "/apply.do?method=updateApply";
		document.apply_form.submit();
		
	<%}%>
}


var apply_status = "no";
function show_apply(str_no) {

	if (apply_status == "no") {
		$("#apply_cont_int_"+str_no).css("display","none");
		$("#apply_cont_mod_"+str_no).css("display","block");
		apply_status = "modify";
	} else {
		$("#apply_cont_int_"+str_no).css("display","block");
		$("#apply_cont_mod_"+str_no).css("display","none");
		apply_status = "no";
	}
}

function selectFreelancer(selectedUserId, applyNo) {
	<%if (session.getAttribute("login_id") == null || session.getAttribute("login_id").equals("")) { %>
	alert("로그인 하셔야 선발 가능 합니다.");
<%} else {%>
	document.apply_form.apply_no.value = applyNo;
	document.apply_form.seluserid.value = selectedUserId;
 	document.apply_form.action = "/apply.do?method=selectFreelancer";
	document.apply_form.submit();
	
<%}%>
}

function setDone(applyNo) {
	<%if (session.getAttribute("login_id") == null || session.getAttribute("login_id").equals("")) { %>
	alert("로그인 하셔야 완료 가능 합니다.");
<%} else {%>
	document.apply_form.apply_no.value = applyNo;
 	document.apply_form.action = "/apply.do?method=setDone";
	document.apply_form.submit();
<%}%>
}

function setCancel(applyNo) {
	<%if (session.getAttribute("login_id") == null || session.getAttribute("login_id").equals("")) { %>
	alert("로그인 하셔야 취소 가능 합니다.");
<%} else {%>
	document.apply_form.apply_no.value = applyNo;
 	document.apply_form.action = "/apply.do?method=setCancel";
	document.apply_form.submit();
	
<%}%>
}
</script>

<body style="background-color: white;" >
<%@include file="/jsp/common/top.jsp"%>
<div class="well">
<div class="container" align="center">
	<h1>프로젝트<small></small>
	</h1>
</div>
</div>

<div class="container">

<div class="container-fluid">
      <div class="row-fluid">
<%if (req_cate == null || req_cate.equals("")) {%><code><%} %><a href="/project.do?method=getList" >전체보기</a><%if (req_cate == null || req_cate.equals("")) {%></code><%} %>&nbsp;
IT(웹)&nbsp;
<%if (req_cate != null && req_cate.equals("01")) {%><code><%} %><a href="/project.do?method=getList&cate=01" >JAVA</a><%if (req_cate != null && req_cate.equals("01")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("02")) {%><code><%} %><a href="/project.do?method=getList&cate=02" >PHP</a><%if (req_cate != null && req_cate.equals("02")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("03")) {%><code><%} %><a href="/project.do?method=getList&cate=03" >ASP</a><%if (req_cate != null && req_cate.equals("03")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("04")) {%><code><%} %><a href="/project.do?method=getList&cate=04" >IT(웹) 기타</a><%if (req_cate != null && req_cate.equals("04")) {%></code><%} %>&nbsp;
IT(모바일)&nbsp;
<%if (req_cate != null && req_cate.equals("05")) {%><code><%} %><a href="/project.do?method=getList&cate=05" >아이폰</a><%if (req_cate != null && req_cate.equals("05")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("06")) {%><code><%} %><a href="/project.do?method=getList&cate=06" >안드로이드</a><%if (req_cate != null && req_cate.equals("06")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("07")) {%><code><%} %><a href="/project.do?method=getList&cate=07" >모바일웹</a><%if (req_cate != null && req_cate.equals("07")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("08")) {%><code><%} %><a href="/project.do?method=getList&cate=08" >IT(모바일) 기타</a><%if (req_cate != null && req_cate.equals("08")) {%></code><%} %>&nbsp;
마케팅&nbsp;
<%if (req_cate != null && req_cate.equals("09")) {%><code><%} %><a href="/project.do?method=getList&cate=09" >SNS</a><%if (req_cate != null && req_cate.equals("09")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("10")) {%><code><%} %><a href="/project.do?method=getList&cate=10" >블로그</a><%if (req_cate != null && req_cate.equals("10")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("11")) {%><code><%} %><a href="/project.do?method=getList&cate=11" >마케팅 기타</a><%if (req_cate != null && req_cate.equals("11")) {%></code><%} %>&nbsp;
디자인&nbsp;
<%if (req_cate != null && req_cate.equals("12")) {%><code><%} %><a href="/project.do?method=getList&cate=12" >3D</a><%if (req_cate != null && req_cate.equals("12")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("13")) {%><code><%} %><a href="/project.do?method=getList&cate=13" >웹디자인</a><%if (req_cate != null && req_cate.equals("13")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("14")) {%><code><%} %><a href="/project.do?method=getList&cate=14" >애니메이션</a><%if (req_cate != null && req_cate.equals("14")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("15")) {%><code><%} %><a href="/project.do?method=getList&cate=15" >디자인 기타</a><%if (req_cate != null && req_cate.equals("15")) {%></code><%} %>&nbsp;
영업&nbsp;
<%if (req_cate != null && req_cate.equals("16")) {%><code><%} %><a href="/project.do?method=getList&cate=16" >기획</a><%if (req_cate != null && req_cate.equals("16")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("17")) {%><code><%} %><a href="/project.do?method=getList&cate=17" >판매</a><%if (req_cate != null && req_cate.equals("17")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("18")) {%><code><%} %><a href="/project.do?method=getList&cate=18" >분석</a><%if (req_cate != null && req_cate.equals("18")) {%></code><%} %>&nbsp;
<%if (req_cate != null && req_cate.equals("19")) {%><code><%} %><a href="/project.do?method=getList&cate=19" >영업 기타</a><%if (req_cate != null && req_cate.equals("19")) {%></code><%} %>&nbsp;
기타&nbsp;
<%if (req_cate != null && req_cate.equals("20")) {%><code><%} %><a href="/project.do?method=getList&cate=20" >기타</a><%if (req_cate != null && req_cate.equals("20")) {%></code><%} %>&nbsp;
        <div>
          <div>
          <br/>
          <% 
if (list.size() != 0) {

	for(int i=0;i<list.size();i++) { 
		projectDTO pdto = (projectDTO)list.get(i);
		status = pdto.getStatus();
%>
<div style="float:right; margin-top:5px;">
	    <%if (modifyRight.equals("Y")) {%>
          	<button class="btn" type="submit" onclick="javascript:location.href('/project.do?method=goModify&no=<%=pdto.getNo() %>');return false;">수정</button>
          	<%if (pdto.getStatus().equals("02")) {%>
			<button class="btn" type="submit" onclick="javascript:setDone('<%=pdto.getNo() %>');return false;">완료</button>
			<%}	%>
			<button class="btn" type="submit" onclick="javascript:setCancel('<%=pdto.getNo() %>');return false;">취소</button>
        <%} %>
          </div>
		<h1 class="page-header"><%=pdto.getTitle()%></h1><br/>
		<div style="float:right;">
			<span style="color:#888888;"><i class="icon-pencil"></i><%=pdto.getRegist_date() %><!-- <%=pdto.getModify_date() %> --></span>
			<span style="color:#888888;"><i class="icon-user"></i><a href="/member.do?method=getListDetail&user_id=<%=pdto.getWriter_id() %>"><%=pdto.getWriter_id() %></a></span>
		</div>
		<!-- <span style="color:#888888;"><i class="icon-eye-open"></i></span> -->
		
		<span class="label label-info">구분</span>&nbsp;&nbsp;&nbsp;&nbsp;<%if (pdto.getCate().equals("01")) {%>JAVA
			<%} else if (pdto.getCate().equals("02")) {%>PHP
			<%} else if (pdto.getCate().equals("03")) {%>ASP
			<%} else if (pdto.getCate().equals("04")) {%>IT(웹) 기타
			<%} else if (pdto.getCate().equals("05")) {%>아이폰
			<%} else if (pdto.getCate().equals("06")) {%>안드로이드
			<%} else if (pdto.getCate().equals("07")) {%>모바일웹
			<%} else if (pdto.getCate().equals("08")) {%>IT(모바일) 기타
			<%} else if (pdto.getCate().equals("09")) {%>SNS
			<%} else if (pdto.getCate().equals("10")) {%>블로그
			<%} else if (pdto.getCate().equals("11")) {%>마케팅 기타
			<%} else if (pdto.getCate().equals("12")) {%>3D
			<%} else if (pdto.getCate().equals("13")) {%>웹디자인
			<%} else if (pdto.getCate().equals("14")) {%>애니메이션
			<%} else if (pdto.getCate().equals("15")) {%>디자인 기타
			<%} else if (pdto.getCate().equals("16")) {%>기획
			<%} else if (pdto.getCate().equals("17")) {%>판매
			<%} else if (pdto.getCate().equals("18")) {%>분석
			<%} else if (pdto.getCate().equals("19")) {%>영업 기타
			<%} else if (pdto.getCate().equals("20")) {%>기타
			<%} %>
			
			&nbsp;<span class="label label-info">상태</span> &nbsp;&nbsp;
			<%if (pdto.getStatus().equals("01")) {%>
				모집중
			<%} else if (pdto.getStatus().equals("02")) {%>
				작업중
			<%} else if (pdto.getStatus().equals("03")) {%>
				완료
			<%} else if (pdto.getStatus().equals("04")) {%>
				취소
			<%}	%>
			
			&nbsp;<span class="label label-info">금액</span> &nbsp;&nbsp;<%=pdto.getAmount()%>
			
			&nbsp;<span class="label label-info">작업기간</span> &nbsp;&nbsp;<%=pdto.getJobdate()%>
	

<br/><br/>
<%if (sess_user_id.equals(pdto.getWriter_id()) && list_apply.size() > 0 && pdto.getStatus().equals("01")) { 
	selectFreelancerYN = "Y";
%>
	<div id="comment_write">
		<div class="alert alert-info">
		<h4>프리랜서 선발가능!</h4>
		현재 1명 이상의 프리랜서가 지원하였으니, 언제든 원하시는 프리랜서를 선발 하실 수 있습니다.<br/>
		한번 선발하면, 프로세스는 작업중 단계로 변경되며, 이 프로젝트로 프리랜서들이 지원할 수 없게 됩니다.
		<a class="close" data-dismiss="alert" href="#">×</a>
		</div>
	</div>
<%} %>
<%if (sess_user_id.equals(pdto.getWriter_id()) && pdto.getStatus().equals("02")) { 
%>
	<div id="comment_write">
		<div class="alert alert-success">
		현재 "<a href="/member.do?method=getListDetail&user_id=<%=pdto.getSelected_id() %>"><%=pdto.getSelected_id() %></a>"님이 <strong>작업중</strong>입니다.
		<a class="close" data-dismiss="alert" href="#">×</a>
		</div>
	</div>
<%} %>
<%if (sess_user_id.equals(pdto.getSelected_id()) && pdto.getStatus().equals("02")) { 
%>
	<div id="comment_write">
		<div class="alert alert-success">
		<strong>"<%=pdto.getSelected_id() %>"</strong>님 본인이 선발되었습니다.<br/>
		프로젝트 작업을 진행하여 주세요.
		<a class="close" data-dismiss="alert" href="#">×</a>
		</div>
	</div>
<%} %>
<p class="docs-lead">
		<%=pdto.getContext()%>
</p>

	


<% 	}
} else {
%>
조회할 데이타가 없습니다.
<%	
}	
%>
				 
          </div>
          
          <!-- 지원 하기 파트 START -->
          <form id="apply_form" name=apply_form method=post autocomplete="off">
			<input type="hidden" name="no" value="<%=no %>">
			<input type="hidden" name="apply_no" value="">
			<input type="hidden" name="apply_text" value="">
			
			<input type="hidden" name="seluserid" value="">
			 <% if (status.equals("01")) {%>
          	 <h1 class="page-header"></h1>
          	
			 <div class="span9">
			 		<textarea name=apply_context id=apply_context class=tx rows=2 style='width:100%;'  itemname='지원'></textarea>
	         </div>
		   	 <div class="span2">
			        <div><button accesskey='s' class="btn btn-large btn-primary" onclick="javascript:goApply();return false;"><i class="icon-comment icon-white"></i>
			        &nbsp;지원하기</button></div>
			 </div>
			 <h1 class="page-header"><br/></h1>
			 <%} else { %>
			 <div id="comment_write">
				<div class="alert alert-error">
				<h4>지원불가!</h4>
				이 프로젝트는 <strong>모집중</strong>이 아니므로, 더이상 지원 할 수 없습니다.
				<a class="close" data-dismiss="alert" href="#">×</a>
				</div>
			</div>
			 <%} %>
			
			<!-- 리스트 START -->
<% 
if (list_apply.size() != 0) {

	for(int i=0;i<list_apply.size();i++) { 
		applyDTO dto = (applyDTO)list_apply.get(i);
%>

<table border=0 cellpadding=0 cellspacing=0 width=100%>
        <tr>
            <td height=1 colspan=3 bgcolor="#dddddd"><td>
        </tr>
        <tr>
            <td height=1 colspan=3></td>
        </tr>
        <tr>
            <td valign=top>
                <div style="height:28px; background:url(/assets/img/co_title_bg.gif); clear:both; line-height:28px;">
                <div style="float:left; margin:2px 0 0 2px;">
                <strong><span class='member'><a href="/member.do?method=getListDetail&user_id=<%=dto.getUser_id()%>"><%=dto.getUser_id()%></a></span></strong>
                <span style="color:#888888; font-size:11px;"><%=dto.getRegist_date()%></span>
                </div>
                <div style="float:right; margin-top:5px;">
                &nbsp;<span style="color:#B2B2B2; font-size:11px;"></span>
                <%if (sess_user_id.equals(dto.getUser_id()) && status.equals("01")) { %>
                <button class="btn btn-mini btn-primary" type="button" onclick="javascript:show_apply('<%=i%>');">수정</button>
                <button class="btn btn-mini btn-primary" type="button" onclick="javascript:goDel('<%=dto.getNo()%>');return false;">삭제</button>
				<%} %>
				<%if (selectFreelancerYN.equals("Y") && status.equals("01")) { %>
				<button class="btn btn-mini btn-primary" type="button" onclick="javascript:selectFreelancer('<%=dto.getUser_id() %>','<%=dto.getApply_no() %>');return false;">선발</button>
				<%} %>
                </div>
                </div>

                <!-- 코멘트 출력 -->
                <div id="apply_cont_int_<%=i %>" style='line-height:20px; padding:7px; word-break:break-all; overflow:hidden; clear:both; '>
                	<%=dto.getContext() %>
                </div>
                <div id="apply_cont_mod_<%=i %>" style='line-height:20px; padding:7px; word-break:break-all; overflow:hidden; clear:both;display:none '>
                	 <div class="span10">
			 			<textarea name=apply_text_<%=i %> id=apply_text_<%=i %> class=tx rows=2 style='width:100%;'  itemname='지원'><%=dto.getContext() %></textarea>
			         </div>
				   	 <div class="span2">
					        <div><button class="btn btn-large btn-primary" onclick="javascript:goMod('<%=dto.getNo()%>',document.apply_form.apply_text_<%=i %>.value);return false;">저장</button></div>
					 </div>
                </div>
               </div>
</td>
        </tr>
        <tr>
            <td height=5 colspan=3></td>
        </tr>
        </table>

<% 	}} 
%>		
			<!-- 리스트 END -->
			</form>
			<!-- 지원 하기 파트 EMD -->
          
          
		 </div><!--/span-->
     </div><!--/row-->
    </div>
</div>

<%@include file="/jsp/common/footer.jsp"%>
</body>
</html>
