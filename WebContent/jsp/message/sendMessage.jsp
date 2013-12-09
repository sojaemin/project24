<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>
<%@ page import = "db.dto.messageDTO" %>
<%@ page import = "util.common" %>
<%
String req_cate = request.getParameter("cate"); 
String search_val = common.nulltovoid(request.getParameter("search_val"));
String error = common.nulltovoid(request.getParameter("error"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en"><!-- bootstrap -->
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>내 프로필</title>
<%@include file="/jsp/common/header.jsp"%>
<script language="javascript">
function goMessage(){
	if (document.msg_form.user_id.value.length < 1) {
	       alert('받는사람 ID를 입력하십시오.');
	       document.msg_form.user_id.focus();
	       return false;
 	}
	if (document.msg_form.context.value.length < 1) {
	       alert('내용을 입력하십시오.');
	       document.msg_form.context.focus();
	       return false;
	}
	document.msg_form.action = "/message.do?method=setSendMessage";
	document.msg_form.submit();
}

<%if (error.equals("noid")) {%>
	alert("존재하지 않는 아이디 입니다.");
<%}%>
 </script>
</head>

 
</head>
<body style="background-color: white;" >
<%@include file="/jsp/common/top.jsp"%>
<div class="well">
<div class="container" align="center">
	<h1>내 메세지</h1>
</div>
</div>


<div class="container">

<div class="container-fluid">
      <div class="row-fluid">
        <div class="span1">
        </div>
        <div class="span10">
        
        <div class="tabbable"> <!-- Only required for left/right tabs -->
		  <ul class="nav nav-tabs">
			    <li  class="active"><a href="/message.do?method=getSendMessage">메세지보내기</a></li>
			    <li><a href="/message.do?method=getMessageList">받은 메세지</a></li>
			    <li><a href="/message.do?method=sendMessageList">보낸 메세지</a></li>
		  </ul>
          <div>
          <div style="float:left;width:80%" >
		 	<form id="msg_form" name=msg_form method=post autocomplete="off" class="form-horizontal">
				<fieldset>	
					<div class="control-group">
						<label class="control-label" for="input01">받는사람 ID : </label>
						<div class="controls">
							<input class=ed maxlength=25 size=25 id='user_id' name="user_id" value=""> <span id='msg_mb_id'></span>
							<p class="help-block"></p>
						</div>
					</div>  
					        
					<div class="control-group">
						<label class="control-label" for="input01">메세지 내용 : </label>
						<div class="controls">
							<textarea name=context class=tx rows=10 style='width:95%;'  itemname='메세지 내용'></textarea>
							<p class="help-block"></p>
						</div>
					</div>
					
				</fieldset>
				<p align=center>
						<a id="btn_submit" class="btn btn-primary btn-large" onclick="javascript:goMessage();">메세지 보내기</a>
					</p>
			</form>		       
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
