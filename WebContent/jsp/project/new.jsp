<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en"><!-- bootstrap -->
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>프로젝트 등록</title>
<%@include file="/jsp/common/header.jsp"%>
</head>
<script type="text/javascript">
function goRegist() {

	document.reg_form.action = "/project.do?method=insertNew";
	document.reg_form.submit();
}


</script>

<body style="background-color: white;" >
<%@include file="/jsp/common/top.jsp"%>
<div class="well">
<div class="container" align="center">
	<h1>프로젝트 등록<small>&nbsp;&nbsp;&nbsp;&nbsp;프로젝트 정보를 입력 하여 주세요.</small>
	</h1>
</div>
</div>


<div class="container">

<form id="reg_form" name=reg_form method=post autocomplete="off" class="form-horizontal">
<fieldset>

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
	<label class="control-label" for="input01">상태 : </label>
	<div class="controls">
		<select name="status" disabled>
			<option value="01">모집중</option>
			<option value="02">작업중</option>
			<option value="03">완료</option>
			<option value="04">취소</option>
		</select>
		<p class="help-block"></p>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="input01">예상금액 : </label>
	<div class="controls">
		<input class=ed type=text id='amount' name='amount' size=10 maxlength=10 value=''>
        <span id='msg_mb_email'></span>
        <p class="help-block"></p>
	</div>
</div>


<div class="control-group">
	<label class="control-label" for="input01">예상기간 : </label>
	<div class="controls">
		<input class=ed type=text name='jobdate' id='jobdate' size=10 maxlength=10  itemname='기간' value=''>
        <p class="help-block"></p>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="input01">제목 : </label>
	<div class="controls">
		<textarea name=title id=title class=tx rows=2 style='width:95%;'  itemname='제목'></textarea>
		<p class="help-block"></p>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="input01">내용 : </label>
	<div class="controls">
		<textarea name=context id=context class=tx rows=7 style='width:95%;'  itemname='내용'></textarea>
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

