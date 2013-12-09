<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en"><!-- bootstrap -->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>프로젝트24</title>
<style>
</style>
<%@include file="/jsp/common/header_main.jsp"%>
</head>

<body style="background-color: white;" >
<%@include file="/jsp/common/top.jsp"%>

<div class="container">
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/ko_KR/all.js#xfbml=1";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>
<!-- <div class="alert alert-block">
<a class="close" data-dismiss="alert" href="#">×</a>
<h4 class="alert-heading">뉴스!   <a href="/bbs/board.php?bo_table=notice&wr_id=3">프로젝트 별 구인구직 사이트 '프로젝트24' </a></h4>

</div> -->
<div class="hero-unit">
<h1 align="center">프로젝트24 사이트에 오신것을<br/> 환영합니다.</h1>
      <p align="center">프로젝트24는 프로젝트 단위로 구직이나 알바를 원하는 프리랜서와 전문적인 프리랜서가 필요한 기업회원을<br/> 매칭시켜주는 웹서비스 입니다.<br>
      Project24에서 여러분의 프로젝트를 성공시켜 보세요.
       </p>
       <p align="center">
       	<a class="btn btn-primary btn-large" href="/member.do?method=terms">가입하러가기 &raquo;</a>
       	<a class="btn btn-primary btn-large" href="/project.do?method=getList">프로젝트 보러가기 &raquo;</a>
       </p>
</div>
<blockquote>
		  <p>프리랜서와 기업회원 정보를 아이디 클릭이나 검색을 통해 언제든 간편하게 확인이 가능합니다.</p>
</blockquote>
<blockquote>
		  <p>등록된 프로젝트는 언제든지 회원 프로젝트 관리 화면에서 조회 및 수정이 가능하며, 진행상태(모집중-작업중-완료,취소)를 확인 할 수 있습니다.</p>
</blockquote>
<blockquote>
		  <p>평점 기능과 프로젝트 완료 이력을 이용하여, 프리랜서 혹은 기업회원의 신뢰도를 판단 할 수 있습니다.</p>
  		  <small>업데이트 예정</small>
</blockquote>
<blockquote>
		  <p>직관적인 UI를 지향하여, 쉽고 간편하게 사이트를 이용할 수 있습니다.</p>
</blockquote>

<!-- <div class="fb-like" data-href="" data-send="false" data-width="800" data-show-faces="true"></div> -->
 <br/><br/><br/><br/><br/><br/>

</div>

<%@include file="/jsp/common/footer.jsp"%>
</body>
</html>
