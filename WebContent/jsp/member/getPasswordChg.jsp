<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en"><!-- bootstrap -->
<head>
<% String status = "";
if (request.getAttribute("status") == null || request.getAttribute("status").equals("")) {
	status = "";
} else {
	status = request.getAttribute("status").toString();
}
%>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>프로젝트24</title>
<%@include file="/jsp/common/header.jsp"%>
 <style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }

    </style>

</head>
<script type="text/javascript">
function go() {
    if (document.reg_form.password.value.length < 6) {
        alert('패스워드를 6글자 이상 입력하십시오.');
        document.reg_form.password.focus();
        return false;
     }

     if (document.reg_form.password.value != document.reg_form.password_re.value) {
         alert('패스워드가 같지 않습니다.');
         document.reg_form.password_re.focus();
         return false;
     }

     if (document.reg_form.password.value.length > 0) {
         if (document.reg_form.password_re.value.length < 6) {
             alert('패스워드를 6글자 이상 입력하십시오.');
             document.reg_form.password_re.focus();
             return false;
         }
     }
    
	document.reg_form.action = "/member.do?method=setPasswordChg";
	document.reg_form.submit();
}

$(document).ready(function (){

	<%if (status.equals("done")) {%>
		alert("패스워드가 정상적으로 변경되었습니다.");
		location.href="/index.jsp";
	<%} %>
});

</script>

<body style="background-color: white;" >
<%@include file="/jsp/common/top.jsp"%>
<div class="well">
<div class="container" align="center">
	<h1>비밀번호 변경<small>&nbsp;&nbsp;&nbsp;&nbsp;변경할 패스워드를 입력 하여 주세요.</small>
	</h1>
</div>
</div>


<div class="container">
<form id="reg_form" name=reg_form method=post autocomplete="off" class="form-signin">
<h2 class="form-signin-heading"></h2>
패스워드     <input type="text" name="password" id="password" class="input-block-level" placeholder="패스워드" maxlength="25">
패스워드 확인<input type="password" name="password_re" id="password_re" class="input-block-level" placeholder="패스워드 확인" maxlength="25">

<center><button class="btn btn-large btn-primary" type="submit" onclick="javascript:go();return false;">패스워드 변경</button></center>

</form>

</td>
<td width=40></td>
</tr></table>


</div>

<%@include file="/jsp/common/footer.jsp"%>
</body>
</html>
