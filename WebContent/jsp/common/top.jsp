<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "javax.servlet.http.HttpSession" %>
<%@ page import = "db.dao.messageDAO"%>

<%
String msgcnt = "";//읽지 않은 메세지 수를 표시한 변수 초기화
if (session.getAttribute("login_id") == null || session.getAttribute("login_id").equals("")) {
	//세션에서 로그인한 아이디가 null이거라 빈 공란일 경우, 즉 로그인 하지 않았을 경우
} else {//정상적으로 로그인한 경우
	messageDAO mDAO = new messageDAO();//messageDAO 객체 생성
	msgcnt = mDAO.getMessageCnt(session.getAttribute("login_id").toString());//로그인한 아이디로 getMessageCnt()메소드를 실행하여, 결과값을 변수에 세팅한다.
}
%>
<a name="g4_head"></a>
<div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="/index.jsp">프로젝트24</a>
            
			            <!-- 로그인 이전 -->
            <div class="btn-group pull-right">
            	<%if (session.getAttribute("login_id") == null || session.getAttribute("login_id").equals("")) { %>
		            <a href="/member.do?method=getLogin" class="btn">로그인</a>
		            <a href="/member.do?method=terms" class="btn">회원가입</a>
	           <%} else { %>
		            <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
		              <i class="icon-user"></i> <%=session.getAttribute("login_id") %>
		              <span class="caret"></span>
		              <%if (Integer.parseInt(msgcnt) > 0) {//읽지않은 메세지 수가 0보다 크면%>
		              &nbsp;&nbsp;&nbsp;&nbsp;<span class="badge badge-warning"><%=msgcnt %></span><!-- 읽지않은 메세지 수를 표시해준다. -->
		              <%} %> 
		            </a>
		            <ul class="dropdown-menu">
		              <li><a href="/message.do?method=getMessageList">메세지함 &nbsp;&nbsp;&nbsp;&nbsp;
		              <%if (Integer.parseInt(msgcnt) > 0) {//읽지않은 메세지 수가 0보다 크면%>
		              	<span class="badge badge-warning"><%=msgcnt %></span><!-- 읽지않은 메세지 수를 표시해준다. -->
		              	<%} %> 
		              	 </a></li>
		              <li><a href="/member.do?method=getProfile">내 프로필/프로젝트 보기</a></li>
		              <li><a href="/member.do?method=goModify">정보수정</a></li>
		              <li class="divider"></li>
		              <li><a href="/member.do?method=doLogout">로그아웃</a></li>
		            </ul>
	           <%} %>
            </div>
			          
          <div class="nav-collapse">
            <ul class="nav">
              <li ><a href="/project.do?method=getList">프로젝트 찾기</a></li>
              <li ><a href="/member.do?method=getList">프리랜서 찾기</a></li>
              <li ><a href="/member.do?method=getList&gubun=C">기업회원 찾기</a></li>
              <li>

              </li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
        
      </div>
</div>



