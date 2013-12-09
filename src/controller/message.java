package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import util.common;
import util.encrypUtil;
import db.dao.memberDAO;
import db.dao.messageDAO;
import db.dto.messageDTO;

/**
 * Servlet implementation class member
 */

public class message extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public message() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//POST 방식일 경우, 아래처럼 인코딩을 세팅해줘야 한글이 안깨진다.
		request.setCharacterEncoding("UTF-8");
		//url : /message.do?method=?
		 if (request.getParameter("method").equals("getMessageList")) {//메세지 리스트를 보여주는 기본 메소드
			 getMessageList(request, response);
			 return;
		 } else if(request.getParameter("method").equals("getMessageListAjax")) {//ajax로 메세지 리스트를 데이타 베이스에서 조회해서 보여주는 메소드
			 getMessageListAjax(request, response);
			 return;
		} else if (request.getParameter("method").equals("sendMessageList")) {//보낸메세지 화면을 보여주는 기본 메소드
			sendMessageList(request, response);
			return;
		} else if(request.getParameter("method").equals("sendMessageListAjax")) {//ajax로 보낸 메세지 리스트를 데이타 베이스에서 조회해서 보여주는 메소드
			sendMessageListAjax(request, response);
			return;
//		} else if(request.getParameter("method").equals("getMessageDetail")) {//메세지 상세 페이지를 만들려고 하다, 딱히 상세페이지 없이 그냥 리스트에서 보여주기로 요건을 변경해서 주석처리해놨다. 
//			getMessageDetail(request, response);
//			return;
		} else if(request.getParameter("method").equals("getSendMessage")) {//메세지 보내기 화면을 보여주는 기본 메소드
			getSendMessage(request, response);
			return;
		} else if(request.getParameter("method").equals("setSendMessage")) {//메세지를 데이타베이스에 입력하는 메소드
			setSendMessage(request, response);
			return;
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);//doPost로 오는 메세지들도 모두 doGet을 호출하게 한다.
	}
	
	private String chkSession(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));//세션에서 로그인한 유저 아이디를 가져온다.
		
		String loginYN = "N";
		if (user_id.length() > 0) {//유저 아이디의 길이가 0 보다 클 경우, 즉 존재할 경우
			loginYN = "Y";//로그인 여부를 Y로 세팅
		}
		return loginYN;
	}
	
	protected void getMessageList(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (chkSession(request).equals("N")) {//세션 존재 여부를 확인해서, 존재하지 않으면,
				response.sendRedirect("/member.do?method=getLogin");//로그인 페이지로 넘겨준다.
			} else {//세션이 존재하면
				RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/message/messagelist.jsp");//정상적으로 메세지 리스트를 보여준다.
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void getMessageListAjax(HttpServletRequest request, HttpServletResponse response) {
		String page_num = request.getParameter("page_num");//리스트 조회할 때 사용할 페이지 번호 세팅
		String search_val = request.getParameter("search_val");//리스트 조회할 때 사용할 검색어 세팅
		
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));//세션에서 로그인 아이디를 가져와 세팅
		
		if( page_num == null || page_num.equals("") || page_num.equals("null") ){//페이지 번호가 없을 경우
			page_num = "0";
		}

		List ls = new ArrayList();//메세지를 담을 list객체 생성
		
		try {
			if (chkSession(request).equals("N")) {//세션이 존재하지 않으면
				response.sendRedirect("/member.do?method=getLogin");//로그인 페이지로 넘긴다.
			} else {
				messageDAO mDAO = new messageDAO();//DAO 객체 생성
				mDAO.readMessage(user_id);//신규 메세지들을 읽음 처리 한다.
				ls = mDAO.getRecMessageList(page_num, search_val, user_id);//받은 메세지를 조회한다.
				
				request.setAttribute("list", ls);//조회한 메세지를 request의 list에 포함시켜, jsp로 전달한다.
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/message/messagelistajax.jsp");//dispatcher 세팅
				dispatcher.forward(request, response);//jsp 화면으로 넘긴다.
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void sendMessageList(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (chkSession(request).equals("N")) {
				response.sendRedirect("/member.do?method=getLogin");
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/message/sendMessageList.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void sendMessageListAjax(HttpServletRequest request, HttpServletResponse response) {
		String page_num = request.getParameter("page_num");
		String search_val = request.getParameter("search_val");
		
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));
		
		if( page_num == null || page_num.equals("") || page_num.equals("null") ){
			page_num = "0";
		}

		List ls = new ArrayList();
		
		try {
			if (chkSession(request).equals("N")) {
				response.sendRedirect("/member.do?method=getLogin");
			} else {
				messageDAO mDAO = new messageDAO();
				ls = mDAO.getSendMessageList(page_num, search_val, user_id);
				
				request.setAttribute("list", ls);
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/message/sendMessageListAjax.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void getSendMessage(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));//세션에서 로그인한 유저 아이디를 가져온다.
		try {
			if (chkSession(request).equals("N")) {//세션을 체크해서, 로그인 여부가 N 이면, 즉 로그인이 안됬을 경우
				response.sendRedirect("/member.do?method=getLogin");//로그인 페이지로 넘겨준다.
			} else {//로그인이 되었다면,
				RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/message/sendMessage.jsp");//정상적으로 메세지 보내는 jsp를 호출한다.
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	protected void setSendMessage(HttpServletRequest request, HttpServletResponse response) {
		String result = "N"; //결과값 초기화
		String existYN = "N"; //받는 사람 ID가 존재하는 아이디인지 판단하는 변수값의 초기화
		messageDTO dto = new messageDTO();//데이타를 받아올 dto를 초기화
		HttpSession session = request.getSession(true);
		String from_id = common.nulltovoid((String) session.getAttribute("login_id"));//세션에서 로그인한 유저 아이디를 from_id에 할당
		try {
		//dto에 필요한 정보를 세팅해 준다.
		dto.setFrom_id(from_id);
		dto.setTo_id(request.getParameter("user_id"));
		dto.setContext(request.getParameter("context"));
		
		messageDAO mDAO = new messageDAO();//DAO 객체 생성
		
		//데이타베이스에 입력전에 아이디 존재여부를 체크
		existYN = mDAO.chkMemberID(request.getParameter("user_id"));
		if (existYN.equals("Y")) { //존재하면 디비에 입력
			result = mDAO.sendMessage(dto);//Y : 디비 입력 성공 N:실패			
		}
		
		if(result.equals("Y")) {//정상적으로 디비에 입력되면 보낸메세지 함으로 돌려줌
			response.sendRedirect("/message.do?method=sendMessageList");
		} else {//존재하는 아이디가 없을시엔 다시 보내기 폼으로 화면을 돌려줌
			response.sendRedirect("/message.do?method=getSendMessage&error=noid");//마지막 파라미터 error에 noid란 값을 할당해서 넘겨준다.
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
