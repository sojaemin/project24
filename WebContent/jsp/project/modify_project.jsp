<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>
<%@ page import = "db.dto.projectDTO" %>
<%@ page import = "util.common" %>
<% List list = (List)request.getAttribute("list"); %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en"><!-- bootstrap -->
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>프로젝트24</title>
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
	document.reg_form.action = "/project.do?method=modifyProject";
	document.reg_form.submit();
}


</script>

<body style="background-color: white;" >
<%@include file="/jsp/common/top.jsp"%>
<div class="well">
<div class="container" align="center">
	<h1>프로젝트 수정
	</h1>
</div>
</div>


<div class="container">
<% 
if (list.size() != 0) {

	for(int i=0;i<list.size();i++) { 
		projectDTO dto = (projectDTO)list.get(i);
%>
<form id="reg_form" name=reg_form method=post autocomplete="off" class="form-horizontal">
<fieldset>
<input type="hidden" name="no" value='<%=dto.getNo()%>'/>
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
	<label class="control-label" for="input01">상태 : </label>
	<div class="controls">
		<input type="hidden" id="status" name="status" value="<%=dto.getStatus()%>">
		<select name="status_dummy" disabled>
			<option value="01" <%if (dto.getStatus().equals("01")) {%>selected<%} %>>모집중</option>
			<option value="02" <%if (dto.getStatus().equals("02")) {%>selected<%} %>>작업중</option>
			<option value="03" <%if (dto.getStatus().equals("03")) {%>selected<%} %>>완료</option>
			<option value="04" <%if (dto.getStatus().equals("04")) {%>selected<%} %>>취소</option>
		</select>
		<p class="help-block"></p>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="input01">예상금액 : </label>
	<div class="controls">
		<input class=ed type=text id='amount' name='amount' size=10 maxlength=10 value='<%=dto.getAmount()%>'>
        <span id='msg_mb_email'></span>
        <p class="help-block"></p>
	</div>
</div>


<div class="control-group">
	<label class="control-label" for="input01">예상기간 : </label>
	<div class="controls">
		<input class=ed type=text name='jobdate' id='jobdate' size=10 maxlength=10  itemname='기간' value='<%=dto.getJobdate()%>'>
        <p class="help-block"></p>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="input01">제목 : </label>
	<div class="controls">
		<textarea name=title id=title class=tx rows=2 style='width:95%;'  itemname='제목'><%=common.replaceRn(dto.getTitle())%></textarea>
		<p class="help-block"></p>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="input01">내용 : </label>
	<div class="controls">
		<textarea name=context id=context class=tx rows=7 style='width:95%;'  itemname='내용'><%=common.replaceRn(dto.getContext())%></textarea>
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
