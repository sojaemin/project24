<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en"><!-- bootstrap -->
<head>
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
	<h1>회원가입 2/2<small>&nbsp;&nbsp;&nbsp;&nbsp;회원정보를 입력 하여 주세요.</small>
	&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-primary btn-large" href="/member.do?method=getLogin">로그인 &raquo;</a></h1>
</div>
</div>


<div class="container">
<div class="alert alert-block">
<a class="close" data-dismiss="alert" href="#">×</a>
<h4 class="alert-heading">회원 가입 완료시, 회원 정보가 프리랜서 및 기업회원 리스트에 자동으로 노출 됩니다.<br/>
핸드폰 번호와 이메일 주소는 노출 되지 않습니다.
</h4>

</div>
<form id="reg_form" name=reg_form method=post autocomplete="off" enctype="multipart/form-data" class="form-horizontal">
<fieldset>

<div class="control-group">
	<label class="control-label" for="input01">회원구분 : </label>
	<div class="controls">
		<select name="gubun">
			<option value="F">프리랜서</option>
			<option value="C">기업회원</option>
		</select>
		<p class="help-block"></p>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="input01">아이디 : </label>
	<div class="controls">
		<input class=ed maxlength=25 size=25 id='user_id' name="user_id" value=""> <span id='msg_mb_id'></span>
		<p class="help-block"></p>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="input01">패스워드 : </label>
	<div class="controls">
		<INPUT class=ed type=password name="password" size=25 maxlength=25 required itemname="패스워드">
		<p class="help-block"></p>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="input01">패스워드 확인 : </label>
	<div class="controls">
		<INPUT class=ed type=password name="password_re" size=25 maxlength=25 required itemname="패스워드 확인">
		<p class="help-block"></p>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="input01">카테고리 : </label>
	<div class="controls">
		<select name="cate">
			<option value="01">JAVA</option>
			<option value="02">PHP</option>
			<option value="03">ASP</option>
			<option value="04">IT(웹) 기타</option>
			<option value="05">아이폰</option>
			<option value="06">안드로이드</option>
			<option value="07">모바일웹</option>
			<option value="08">IT(모바일) 기타</option>
			<option value="09">SNS</option>
			<option value="10">블로그</option>
			<option value="11">마케팅 기타</option>
			<option value="12">3D</option>
			<option value="13">웹디자인</option>
			<option value="14">애니메이션</option>
			<option value="15">디자인 기타</option>
			<option value="16">기획</option>
			<option value="17">판매</option>
			<option value="18">분석</option>
			<option value="19">영업 기타</option>
			<option value="20">기타</option>
		</select>
		<p class="help-block"></p>
	</div>
</div>


<div class="control-group">
	<label class="control-label" for="input01">E-mail : </label>
	<div class="controls">
		<input class=ed type=text id='email' name='email' size=38 maxlength=100 value=''>
        <span id='msg_mb_email'></span>
        <p class="help-block"></p>
	</div>
</div>


<div class="control-group">
	<label class="control-label" for="input01">핸드폰번호 : </label>
	<div class="controls">
		<input class=ed type=text name='handphone' id='handphone' size=21 maxlength=20  itemname='핸드폰번호' value=''>
        <p class="help-block"></p>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="input01">프로필 이미지 : </label>
	<div class="controls">
		<input type=file name='user_img' id='user_img' size=21 itemname='user_img' value=''>
        <p class="help-block"></p>
	</div>
</div>

        
       
<!-- <div class="control-group">
	<label class="control-label" for="input01">서명 : </label>
	<div class="controls">
		<textarea name=signature id=signature class=tx rows=3 style='width:95%;'  itemname='서명'></textarea>
		<p class="help-block"></p>
	</div>
</div>-->

        
<div class="control-group">
	<label class="control-label" for="input01">자기소개 : </label>
	<div class="controls">
		<textarea name=profile class=tx rows=3 style='width:95%;'  itemname='자기 소개'></textarea>
		<p class="help-block"></p>
	</div>
</div>

</fieldset>

<p align=center>
	<a id="btn_submit" class="btn btn-primary btn-large" onclick="javascript:goRegist();">저장하기</a>
</p>
</form>

</td>
<td width=40></td>
</tr></table>


</div>

<%@include file="/jsp/common/footer.jsp"%>
</body>
</html>

