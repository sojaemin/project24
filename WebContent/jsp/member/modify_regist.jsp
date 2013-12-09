<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>
<%@ page import = "db.dto.memberDTO" %>
<%@ page import = "util.common" %>
<% List list = (List)request.getAttribute("list"); %>
    
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
	 /*if (document.reg_form.user_id.value.length < 1) {
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
    */
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
	document.reg_form.action = "/member.do?method=modifyRegist";
	document.reg_form.submit();
}


</script>

<body style="background-color: white;" >
<%@include file="/jsp/common/top.jsp"%>
<div class="well">
<div class="container" align="center">
	<h1>회원정보 변경<small>&nbsp;&nbsp;&nbsp;&nbsp;회원정보를 입력 하여 주세요.</small>
	</h1>
</div>
</div>


<div class="container">
<% 
if (list.size() != 0) {

	for(int i=0;i<list.size();i++) { 
		memberDTO dto = (memberDTO)list.get(i);
%>
<form id="reg_form" name=reg_form method=post autocomplete="off" enctype="multipart/form-data" class="form-horizontal">
<fieldset>

<div class="control-group">
	<label class="control-label" for="input01">회원구분 : </label>
	<div class="controls">
		<select name="gubun" disabled>
			<option value="F" <%if (dto.getGubun().equals("F")) {%>selected<%} %>>프리랜서</option>
			<option value="C" <%if (dto.getGubun().equals("C")) {%>selected<%} %>>기업회원</option>
		</select>
		<p class="help-block"></p>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="input01">아이디 : </label>
	<div class="controls">
		<input class=ed maxlength=25 size=25 id='user_id' name="user_id" value="<%=dto.getId()%>" disabled> <span id='msg_mb_id'></span>
		<p class="help-block"></p>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="input01">패스워드 : </label>
	<div class="controls">
		<INPUT class=ed type=password name="password" size=25 maxlength=25 required itemname="패스워드" disabled>
		<p class="help-block"></p>
		<a href="/member.do?method=getPasswordChg">패스워드 변경하러 가기</a>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="input01">카테고리 : </label>
	<div class="controls">
		<select name="cate">
			<option value="01" <%if (dto.getCate().equals("01")) {%>selected<%} %>>JAVA</option>
			<option value="02" <%if (dto.getCate().equals("02")) {%>selected<%} %>>PHP</option>
			<option value="03" <%if (dto.getCate().equals("03")) {%>selected<%} %>>ASP</option>
			<option value="04" <%if (dto.getCate().equals("04")) {%>selected<%} %>>IT(웹) 기타</option>
			<option value="05" <%if (dto.getCate().equals("05")) {%>selected<%} %>>아이폰</option>
			<option value="06" <%if (dto.getCate().equals("06")) {%>selected<%} %>>안드로이드</option>
			<option value="07" <%if (dto.getCate().equals("07")) {%>selected<%} %>>모바일웹</option>
			<option value="08" <%if (dto.getCate().equals("08")) {%>selected<%} %>>IT(모바일) 기타</option>
			<option value="09" <%if (dto.getCate().equals("09")) {%>selected<%} %>>SNS</option>
			<option value="10" <%if (dto.getCate().equals("10")) {%>selected<%} %>>블로그</option>
			<option value="11" <%if (dto.getCate().equals("11")) {%>selected<%} %>>마케팅 기타</option>
			<option value="12" <%if (dto.getCate().equals("12")) {%>selected<%} %>>3D</option>
			<option value="13" <%if (dto.getCate().equals("13")) {%>selected<%} %>>웹디자인</option>
			<option value="14" <%if (dto.getCate().equals("14")) {%>selected<%} %>>애니메이션</option>
			<option value="15" <%if (dto.getCate().equals("15")) {%>selected<%} %>>디자인 기타</option>
			<option value="16" <%if (dto.getCate().equals("16")) {%>selected<%} %>>기획</option>
			<option value="17" <%if (dto.getCate().equals("17")) {%>selected<%} %>>판매</option>
			<option value="18" <%if (dto.getCate().equals("18")) {%>selected<%} %>>분석</option>
			<option value="19" <%if (dto.getCate().equals("19")) {%>selected<%} %>>영업 기타</option>
			<option value="20" <%if (dto.getCate().equals("20")) {%>selected<%} %>>기타</option>
		</select>
		<p class="help-block"></p>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="input01">E-mail : </label>
	<div class="controls">
		<input class=ed type=text id='email' name='email' size=38 maxlength=100 value='<%=dto.getEmail()%>'>
        <span id='msg_mb_email'></span>
        <p class="help-block"></p>
	</div>
</div>


<div class="control-group">
	<label class="control-label" for="input01">핸드폰번호 : </label>
	<div class="controls">
		<input class=ed type=text name='handphone' id='handphone' size=21 maxlength=20  itemname='핸드폰번호' value='<%=dto.getHandphone()%>'>
        <p class="help-block"></p>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="input01">프로필 이미지 : </label>
	<div class="controls">
		<ul class="thumbnails">
			<li class="span4">
				<%if (dto.getPro_img() == null || dto.getPro_img().equals("") || dto.getPro_img().equals("null")) {%>
				이미지가 없습니다. 이미지를 등록하여 주세요.
				<%} else { %>
				<a href="/data/<%=dto.getPro_img()%>" class="thumbnail">
					<img data-src="holder.js/300x200" src="/data/<%=dto.getPro_img()%>" alt=""></a>
				<%} %>
			</li>
		</ul>
		<input type=file name='user_img' id='user_img' size=21 itemname='user_img' value=''>
		<input type="hidden" name="org_user_img" value='<%=dto.getPro_img()%>'>
        <p class="help-block"></p>
	</div>
</div>

        
       
<!-- <div class="control-group">
	<label class="control-label" for="input01">서명 : </label>
	<div class="controls">
		<textarea name=signature id=signature class=tx rows=5 style='width:95%;'  itemname='서명'><%=common.replaceRn(dto.getSignature())%></textarea>
		<p class="help-block"></p>
	</div>
</div>-->

        
<div class="control-group">
	<label class="control-label" for="input01">자기소개 : </label>
	<div class="controls">
		<textarea name=profile class=tx rows=5 style='width:95%;'  itemname='자기 소개'><%=common.replaceRn(dto.getProfile())%></textarea>
		<p class="help-block"></p>
	</div>
</div>

</fieldset>
<% 	}
} else {
%>
조회할 데이타가 없습니다.
<%	
}	
%>
<p align=center>
	<a id="btn_submit" class="btn btn-primary btn-large" onclick="javascript:goRegist();">변경하기</a>
</p>
</form>

</td>
<td width=40></td>
</tr></table>


</div>

<%@include file="/jsp/common/footer.jsp"%>
</body>
</html>
