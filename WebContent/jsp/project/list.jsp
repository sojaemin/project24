<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>
<%@ page import = "db.dto.projectDTO" %>
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
<title>프로젝트 리스트</title>
<%@include file="/jsp/common/header.jsp"%>
</head>
<script language="javascript">
var scrollDisable = "N";
$(document).ready(function (){
	$(window).scroll(function (){
			if($(window).scrollTop() == $(document).height() - $(window).height()){ //최하단의 위치값과 동일여부
				if (scrollDisable == "N") {
						lastPosted(); //스크롤페이징 ajax 호출 스크립트
				} else if (scrollDisable == "Y") {
					return false;
				}
			
			} 
		});
//처음 페이지 로딩될때 실행됨
	lastPosted(); //스크롤페이징 ajax 호출 스크립트
	});
	
  var pstate = 0; //ajax 진행중값

  function lastPosted(){   
   if(pstate == 0){ /*진행중이 아닐경우*/
    $("#LodingImage").html('<img src="/images/loading.gif" style="margin:10px 0;"  />');
    pstate == 1; //진행중값으로 변경
    var lastId = 0;
    if($(".list tr:last").attr("id")){ //id 값이 있을 
      // window.scrollTo(0, $(window).scrollTop() + 30);
       if (navigator.userAgent.indexOf('iPhone') != -1) 
        {
      		setTimeout(scrollTo, 0, 0, $(window).scrollTop() + 30);         //안드로이드 스크롤 아래이동
        }else{
       		window.scrollTo(0, $(window).scrollTop() + 30); //아이폰 스크롤 아래이동.
        }     
     lastId = $(".list tr:last").attr("id");

    }
    /*ajax 호출*/
    $.ajax({
     type :"POST",
     data : "page_num="+lastId+"&search_val="+"<%=search_val%>",
     url  : "/project.do?method=getListAjax&cate=<%=req_cate%>",
     contentType:"application/x-www-form-urlencoded;charset=utf-8", //한글 깨짐 방지
     success: function (data){
      if(data=="false"){
       alert("데이터를 로드 하지 못하였습니다.");
       $("#LodingImage").empty();
       pstate = 0;
      }else if(data == ""){
       alert("더이상 목록이 존재 하지 않습니다.");
       $("#LodingImage").empty();
       pstate = 1;

      }else{

       if ($(".list tr:last").attr("id") == "0"){//더이상 데이타가 없는 경우, 
    	   scrollDisable = "Y";
       } else if($(".list tr:last").attr("id")){ //첫실행이 아닐경우 li존재
    	   $(".list tr:last").after(data);
       }else{ //첫실행일 경우li 없음

        $(".list").html(data); 
       }

       $("#LodingImage").empty();
       pstate = 0;
      }
     },
     error: function (){
      alert("파일 접속 오류 잠시후 이용해 주세요.");
      $("#LodingImage").empty();      
      pstate = 0;
     }
    });
   }
  }
  //window.onLoad = lastPostLoad;
  
  
  function goSearch() {
	  //alert($("#search_text").val());
	  //document.form_search.search_val.value = encodeURI(document.form_search.search_text.value,"UTF-8");
	  //$("#search_val").val(decodeURI($("#search_text").val(),"UTF-8"));
	  document.form_search.action = "/project.do?method=getList&cate=<%=req_cate%>";
	  document.form_search.submit();
  }
  
  <% if (error.equals("noco")) {%>
	alert("기업회원만 프로젝트를 등록할 수 있습니다.");  
  <%}%>
  
 </script>
 
</head>
<body style="background-color: white;" >
<%@include file="/jsp/common/top.jsp"%>
<div class="well">
<div class="container" align="center">
	<h1>프로젝트 리스트<!-- <small>&nbsp;&nbsp;&nbsp;&nbsp;모든 종류의 프로젝트를 등록하실 수 있습니다.</small>-->
	&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-primary btn-large" href="#" onclick="javascript:location.href('/project.do?method=goNew');return false;">프로젝트 등록 &raquo;</a></h1>
</div>
</div>


<div class="container">

<div class="container-fluid">
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
      <div class="row-fluid">
<%--         <div class="span2">
          <div class="well sidebar-nav">
            <ul class="nav nav-list">
              <li <%if (req_cate == null || req_cate.equals("")) {%>class="active"<%} %>><a href="/project.do?method=getList" >전체보기</a></li>
              <li class="nav-header">IT(웹)</li>
              <li <%if (req_cate != null && req_cate.equals("01")) {%>class="active"<%} %>><a href="/project.do?method=getList&cate=01">JAVA</a></li>
              <li <%if (req_cate != null && req_cate.equals("02")) {%>class="active"<%} %>><a href="/project.do?method=getList&cate=02" >PHP</a></li>
              <li <%if (req_cate != null && req_cate.equals("03")) {%>class="active"<%} %>><a href="/project.do?method=getList&cate=03">ASP</a></li>
              <li <%if (req_cate != null && req_cate.equals("04")) {%>class="active"<%} %>><a href="/project.do?method=getList&cate=04" >IT(웹) 기타</a></li>
              <li class="nav-header">IT(모바일)</li>
              <li <%if (req_cate != null && req_cate.equals("05")) {%>class="active"<%} %>><a href="/project.do?method=getList&cate=05" >아이폰</a></li>
              <li <%if (req_cate != null && req_cate.equals("06")) {%>class="active"<%} %>><a href="/project.do?method=getList&cate=06" >안드로이드</a></li>
              <li <%if (req_cate != null && req_cate.equals("07")) {%>class="active"<%} %>><a href="/project.do?method=getList&cate=07" >모바일웹</a></li>
              <li <%if (req_cate != null && req_cate.equals("08")) {%>class="active"<%} %>><a href="/project.do?method=getList&cate=08" >IT(모바일) 기타</a></li>
              <li class="nav-header">마케팅</li>          
              <li <%if (req_cate != null && req_cate.equals("09")) {%>class="active"<%} %>><a href="/project.do?method=getList&cate=09" >SNS</a></li>
              <li <%if (req_cate != null && req_cate.equals("10")) {%>class="active"<%} %>><a href="/project.do?method=getList&cate=10" >블로그</a></li>
              <li <%if (req_cate != null && req_cate.equals("11")) {%>class="active"<%} %>><a href="/project.do?method=getList&cate=11" >마케팅 기타</a></li>
              <li class="nav-header">디자인</li>
              <li <%if (req_cate != null && req_cate.equals("12")) {%>class="active"<%} %>><a href="/project.do?method=getList&cate=12" >3D</a></li>
              <li <%if (req_cate != null && req_cate.equals("13")) {%>class="active"<%} %>><a href="/project.do?method=getList&cate=13" >웹디자인</a></li>
              <li <%if (req_cate != null && req_cate.equals("14")) {%>class="active"<%} %>><a href="/project.do?method=getList&cate=14" >애니메이션</a></li>
              <li <%if (req_cate != null && req_cate.equals("15")) {%>class="active"<%} %>><a href="/project.do?method=getList&cate=15" >디자인 기타</a></li>
              <li class="nav-header">영업</li>
              <li <%if (req_cate != null && req_cate.equals("16")) {%>class="active"<%} %>><a href="/project.do?method=getList&cate=16" >기획</a></li>
              <li <%if (req_cate != null && req_cate.equals("17")) {%>class="active"<%} %>><a href="/project.do?method=getList&cate=17" >판매</a></li>
              <li <%if (req_cate != null && req_cate.equals("18")) {%>class="active"<%} %>><a href="/project.do?method=getList&cate=18" >분석</a></li>
              <li <%if (req_cate != null && req_cate.equals("19")) {%>class="active"<%} %>><a href="/project.do?method=getList&cate=19" >영업 기타</a></li>
              <li class="nav-header">기타</li>
              <li <%if (req_cate != null && req_cate.equals("20")) {%>class="active"<%} %>><a href="/project.do?method=getList&cate=20" >기타</a></li>
            </ul>
          </div><!--/.well -->
        </div><!--/span--> --%>
        <div>
          <div>
          <br/>
          <small>*스크롤을 내리면, 다음 리스트가 나타납니다.</small><br/>
          <div style="float:left;width:80%" >
          <form class="form-search" id="form_search" name="form_search" valign="center" method=post>
			  <div class="input-append" style="width:100%">
			    <input type="text" id="search_val" name="search_val" class="span4 search-query" value="<%=search_val%>"/>
			    <!-- <input type="hidden" id="search_val" name="search_val"> -->
			    <button class="btn" onclick="javascript:goSearch();return false;">검색</button>
			  </div>
		  </form>
		  </div>
          <div style="float:right;">
				        <a href="#" class="btn btn-primary" onclick="javascript:location.href('/project.do?method=goNew');return false;">프로젝트 등록</a>				        </div>
				 <table class="table table-striped">
							    	<thead>
							    			<th><!--번호--></th>
									        <th><h6>구분</h6></th>
									        <th><h6>이름</h6></th>
									        <th><h6>제&nbsp;&nbsp;&nbsp;목</h6></th>
									        <th><h6>상태</h6></th>
									        <th><h6>지원자수</h6></th>
									        <th><h6>금액</h6></th>
									        <th><h6>기간</h6></th>
									        <th><h6>날짜</a></h6></th>
									        <!-- <th><h6>조회</a></h6></th> -->
									</thead>
							    	<tbody class="list">
                   							 
	               				  </tbody>
			    </table>
			    <div id="LodingImage"></div>
			    
          </div>
				
		 </div><!--/span-->

     </div><!--/row-->

    </div>
    
</div>

<%@include file="/jsp/common/footer.jsp"%>
</body>
</html>
