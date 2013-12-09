<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en"><!-- bootstrap -->
<head>
<% String result = (String)request.getAttribute("result");
String user_id = (String)request.getAttribute("user_id");
%>   
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원 가입</title>
<%@include file="/jsp/common/header.jsp"%>   
</head>
<script type="text/javascript">
function goRegist() {
	if (document.reg_form.user_id.value.length < 1) {
	       alert('아이디를 입력하십시오.');
	       document.reg_form.user_id.focus();
	       return false;
    }
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
	document.reg_form.action = "/member.do?method=insertRegist";
	document.reg_form.submit();
}


</script>

<body style="background-color: white;" >
<%@include file="/jsp/common/top.jsp"%>
<div class="well">
<div class="container" align="center">
	<h1>회원가입 완료<small>&nbsp;&nbsp;&nbsp;&nbsp;회원가입 하여 주셔서 감사합니다.</small>
	&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-primary btn-large" href="../bbs/login.php?url=%2Fbbs%2Fregister_result.php">로그인 &raquo;</a></h1>
</div>
</div>

<%=result %>
<% if (result.equals("Y")) { %>
<div class="container">
<div align="center">
<h1><%=user_id %></h1><br/><small>님의 회원가입을 진심으로 축하합니다.</small>
                       <br/><br/> 
<br/>
</div>   
<br/><br/>
<div align="center"><a class="btn btn-primary btn-large" href="/index.jsp">홈으로</a>
</div>
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>

</td>
<td width=40></td>
</tr></table>
</div>
<%} else if (result.equals("D")){ %>

<div class="container">
<div align="center">
<h1><%=user_id %></h1><br/><small>님의 아이디가 이미 존재합니다.</small>
<br/><br/> 
다시 회원가입 하여 주세요.
<br/>
</div>   
<br/><br/>
<div align="center"><a class="btn btn-primary btn-large" href="/index.jsp">홈으로</a>
</div>
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>

</td>
<td width=40></td>
</tr></table>
</div>
<%} else { %>

<div class="container">
<div align="center">
<h1></h1><br/><small>오류가 발생하였습니다.</small>
<br/><br/> 
다시 회원가입 하여 주세요.
<br/>
</div>   
<br/><br/>
<div align="center"><a class="btn btn-primary btn-large" href="/index.jsp">홈으로</a>
</div>
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>

</td>
<td width=40></td>
</tr></table>
</div>

<%} %>

<%@include file="/jsp/common/footer.jsp"%>
</body>
</html>
