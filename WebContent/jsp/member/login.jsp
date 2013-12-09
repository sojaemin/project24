<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en"><!-- bootstrap -->
<head>
<% String errormessage = request.getAttribute("errormessage").toString(); %>
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
	if (document.login_form.user_id.value.length < 1) {
	       alert('아이디를 입력하십시오.');
	       document.login_form.user_id.focus();
	       return false;
    }
    if (document.login_form.pwd.value.length < 6) {
       alert('패스워드를 6글자 이상 입력하십시오.');
       document.login_form.pwd.focus();
       return false;
    }

    /*//파일
      if (typeof f.mb_icon != 'undefined') {
        if (f.mb_icon.value) {
            if (!f.mb_icon.value.toLowerCase().match(/.(gif|png|jpg|jpeg)$/i)) {
                alert('회원아이콘이 이미지 파일이 아닙니다.');
                f.mb_icon.focus();
                return false;
            }
        }
    }
    */
    
	document.login_form.action = "/member.do?method=doLogin";
	document.login_form.submit();
}

$(document).ready(function (){
	<%if (errormessage.equals("noid")) { %>
alert("아이디가 존재하지 않습니다.");
	<%} else if (errormessage.equals("wrongpassword")) { %>
	alert("비밀번호가 틀렸습니다.");
	<%}%>
	
});

</script>

<body style="background-color: white;" >
<%@include file="/jsp/common/top.jsp"%>
<div class="well">
<div class="container" align="center">
	<h1>로그인<small>&nbsp;&nbsp;&nbsp;&nbsp;아이디와 패스워드를 입력 하여 주세요.</small>
	</h1>
</div>
</div>


<div class="container">
<form id="login_form" name=login_form method=post autocomplete="off" class="form-signin">
<h2 class="form-signin-heading"></h2>
아이디<input type="text" name="user_id" id="user_id" class="input-block-level" placeholder="아이디" maxlength="25">
패스워드<input type="password" name="pwd" id="pwd" class="input-block-level" placeholder="패스워드" maxlength="25">
<!-- <label class="checkbox">
<input type="checkbox" value="remember-me"> 기억하기
</label>-->
<center><button class="btn btn-large btn-primary" type="submit" onclick="javascript:go();return false;">로그인</button></center>

</form>

</td>
<td width=40></td>
</tr></table>


</div>

<%@include file="/jsp/common/footer.jsp"%>
</body>
</html>
