<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>
<%@ page import = "db.dto.projectDTO" %>
<%@ page import = "util.common" %>
<%@ page import = "javax.servlet.http.HttpSession" %>
<%
String req_cate = request.getParameter("cate"); 
String search_val = common.nulltovoid(request.getParameter("search_val"));
String gubun = session.getAttribute("gubun").toString();
if (gubun.equals("")) {
	gubun = "F";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en"><!-- bootstrap -->
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>내 프로필</title>
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
     url  : "/member.do?method=getApplyListAjax&cate=<%=req_cate%>&gubun=<%=gubun%>",
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
	  document.form_search.action = "/member.do?method=getApplyList&cate=<%=req_cate%>&gubun=<%=gubun%>";
	  document.form_search.submit();
  }
 </script>
 
</head>
<body style="background-color: white;" >
<%@include file="/jsp/common/top.jsp"%>
<div class="well">
<div class="container" align="center">
	<h1>내 프로필<!-- <small>&nbsp;&nbsp;&nbsp;&nbsp;모든 종류의 프로젝트를 등록하실 수 있습니다.</small>-->
	</h1>
</div>
</div>


<div class="container">

<div class="container-fluid">
      <div class="row-fluid">
        <div class="span1">
         
        </div><!--/span-->
        <div class="span10">
        
        <div class="tabbable"> <!-- Only required for left/right tabs -->
		  <ul class="nav nav-tabs">
			    <li><a href="/member.do?method=getProfile&gubun=<%=gubun%>">소개</a></li>
			    <% if (gubun.equals("C")) {%>
		    	<li class="active"><a href="/member.do?method=getApplyList&gubun=<%=gubun%>">등록한 프로젝트</a></li>
		   		<%} else { %>
		   		<li class="active"><a href="/member.do?method=getApplyList&gubun=<%=gubun%>">지원한 프로젝트</a></li>
				<%} %>
			    <li><a href="/member.do?method=getWorkList&gubun=<%=gubun%>">작업중인 프로젝트</a></li>
			    <li><a href="/member.do?method=getDoneList&gubun=<%=gubun%>">작업완료한 프로젝트</a></li>
		  </ul>
          <div>
          <div style="float:left;width:80%" >
          <form class="form-search" id="form_search" name="form_search" valign="center" method=post>
			  <div class="input-append" style="width:100%">
			    <input type="text" id="search_val" name="search_val" class="span4 search-query" value="<%=search_val%>"/>
			    <!-- <input type="hidden" id="search_val" name="search_val"> -->
			    <button class="btn" onclick="javascript:goSearch();return false;">검색</button>
			  </div>
		  </form>
		  </div>
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
				
		</div>
		
		
		
		 </div><!--/span-->

     </div><!--/row-->

    </div>
    
</div>

<%@include file="/jsp/common/footer.jsp"%>
</body>
</html>
