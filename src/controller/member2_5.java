package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.dao.memberDAO;
import db.dto.memberDTO;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;

import com.mysql.jdbc.StringUtils;

import java.io.*;
import util.FileUploadRequestWrapper;
import util.common;
import util.encrypUtil;

/**
 * Servlet implementation class member
 */
public class member2_5 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public member2_5() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//POST 방식일 경우, 아래처럼 인코딩을 세팅해줘야 한글이 안깨진다.
		request.setCharacterEncoding("UTF-8");
		//url : /member.do?method=?
		 if (request.getParameter("method").equals("terms")) {
			 goTerms(request, response);
			 return;
		} else if (request.getParameter("method").equals("regist")) {
			goRegist(request, response);
			return;
		} else if(request.getParameter("method").equals("insertRegist")) {
			insertRegist(request, response);
			return;
		} else if(request.getParameter("method").equals("goModify")) {
			goModify(request, response);
			return;
		} else if(request.getParameter("method").equals("getList")) {
			getList(request, response);
			return;
		} else if(request.getParameter("method").equals("getListAjax")) {
			getListAjax(request, response);
			return;
		} else if(request.getParameter("method").equals("getLogin")) {
			getLogin(request, response);
			return;
		} else if(request.getParameter("method").equals("doLogin")) {
			doLogin(request, response);
			return;
		} else if(request.getParameter("method").equals("doLogout")) {
			doLogout(request, response);
			return;
		} else if(request.getParameter("method").equals("modifyRegist")) {
			modifyRegist(request, response);
			return;
		} else if(request.getParameter("method").equals("getProfile")) {
			getProfile(request, response);
			return;
		} else if(request.getParameter("method").equals("getListDetail")) {
			getListDetail(request, response);
			return;
		}
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void goTerms(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/member/terms.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void goRegist(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/member/regist.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void goModify(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));
		
		List ls = new ArrayList();
		
		try {
			memberDAO mDAO = new memberDAO();
			ls = mDAO.getMemberInfo(user_id);
			request.setAttribute("list", ls);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/member/modify_regist.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	protected void insertRegist(HttpServletRequest request, HttpServletResponse response) {
		String result = "N";
		String dupYN = "N";
		memberDTO dto = new memberDTO();
		Date current = new Date();
		encrypUtil enc = new encrypUtil();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String currentDate =  dateFormat.format(current);
				
		try {
		FileUploadRequestWrapper requestWrap = new FileUploadRequestWrapper(request, -1, -1, "C:\\jspbook\\workspace\\jspbook\\chap11\\WebContent\\data");

		//파일 업로드
		Map pmap = requestWrap.getFileItem();
		Iterator it = pmap.keySet().iterator();

		//map에 있는걸 하나씩 꺼내
		while (it.hasNext()) {  
			String name = (String)it.next();   //파라메터 이름을 구해
			
			FileItem fileItem = (FileItem)requestWrap.getFileItem(name);  //fileItem을 구하고
		    if (fileItem != null) {
		      System.out.println(name+" : "+fileItem.getName()+"<br>");
		      if (fileItem.getSize() > 0) {  //fileItem이 용량이 있으면 
		        int idx = fileItem.getName().lastIndexOf("\\");
		         if (idx == -1) {
		           idx = fileItem.getName().lastIndexOf("/");
		           String fileName = fileItem.getName().substring(idx + 1); //파일 이름만 짤라내 (경로빼고)
		           String ext = getExt(fileItem.getName());
		           fileName = System.currentTimeMillis()+ext;
		           System.out.println("======filename==="+fileName);
		           System.out.println("======ext==="+ext);
		           //파일 이름을 현재시간을 이용하여 변경
		           
		           //File uploadedFile = new File ("C:\\imsi\\"+currentDate+"\\", fileName);
		           File uploadedFile = new File ("C:\\jspbook\\workspace\\jspbook\\chap11\\WebContent\\data", fileName);
		           /*if (!uploadedFile.exists()) {//폴더가 없으면 생성
		        	   uploadedFile.mkdirs();
		           }*/
		   	       fileItem.write(uploadedFile);   //그리고 저장 (저장 실패시 temp 폴더에 파일 남아있는데 저장이 완료되면 임시저장장소에 있는 파일 자동 삭제)
		   	       dto.setPro_img(fileName);
		         }
		      }
		    }
		}
		String encPwd = enc.getHash(requestWrap.getParameter("password")).toString();
		//파일 업로드를 완료한 다음, 디비에 입력
		dto.setId(requestWrap.getParameter("user_id"));
		dto.setPassword(encPwd);
		dto.setGubun(requestWrap.getParameter("gubun"));
		dto.setCate(requestWrap.getParameter("cate"));
		dto.setEmail(requestWrap.getParameter("email"));
		dto.setHandphone(requestWrap.getParameter("handphone"));
		dto.setSignature(common.replaceBr(requestWrap.getParameter("signature")));
		dto.setProfile(common.replaceBr(requestWrap.getParameter("profile")));
		
		memberDAO mDAO = new memberDAO();
		
		//데이타베이스에 입력전에 아이디가 중복되는지 체크
		dupYN = mDAO.chkMemberID(requestWrap.getParameter("user_id"));
		if (dupYN.equals("Y")) { //중복이면 바로 결과 화면으로 보낸다.
			result = "D"; //결과에 중복입을 알려줌
		} else {// 중복이 아니면 데이타베이스에 입력한다.
			result = mDAO.insertMember(dto);//Y : 디비 입력 성공 N:실패
		}
		
		if(result.equals("Y")) {//로그인이 정상적으로 되면 세션을 살려줌
			HttpSession session = request.getSession(true);
			session.setMaxInactiveInterval(60*60*3);
			session.setAttribute("login_id", requestWrap.getParameter("user_id"));
		}
		
		request.setAttribute("result", result);
		request.setAttribute("user_id", requestWrap.getParameter("user_id"));
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/member/registdone.jsp");
		
		dispatcher.forward(request, response);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void modifyRegist(HttpServletRequest request, HttpServletResponse response) {
		String result = "N";
		String dupYN = "N";
		memberDTO dto = new memberDTO();
		Date current = new Date();
		encrypUtil enc = new encrypUtil();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String currentDate =  dateFormat.format(current);
		
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));
		
		try {
		FileUploadRequestWrapper requestWrap = new FileUploadRequestWrapper(request, -1, -1, "C:\\jspbook\\workspace\\jspbook\\chap11\\WebContent\\data");

		//파일 업로드
		Map pmap = requestWrap.getFileItem();
		Iterator it = pmap.keySet().iterator();

		//map에 있는걸 하나씩 꺼내
		while (it.hasNext()) {  
			String name = (String)it.next();   //파라메터 이름을 구해
			
			FileItem fileItem = (FileItem)requestWrap.getFileItem(name);  //fileItem을 구하고
		    if (fileItem != null) {
		      System.out.println(name+" : "+fileItem.getName()+"<br>");
		      if (fileItem.getSize() > 0) {  //fileItem이 용량이 있으면 
		        int idx = fileItem.getName().lastIndexOf("\\");
		         if (idx == -1) {
		           idx = fileItem.getName().lastIndexOf("/");
		           String fileName = fileItem.getName().substring(idx + 1); //파일 이름만 짤라내 (경로빼고)
		           String ext = getExt(fileItem.getName());
		           fileName = System.currentTimeMillis()+ext;
		           System.out.println("======filename==="+fileName);
		           System.out.println("======ext==="+ext);
		           //파일 이름을 현재시간을 이용하여 변경
		           
		           //File uploadedFile = new File ("C:\\imsi\\"+currentDate+"\\", fileName);
		           File uploadedFile = new File ("C:\\jspbook\\workspace\\jspbook\\chap11\\WebContent\\data", fileName);
		           /*if (!uploadedFile.exists()) {//폴더가 없으면 생성
		        	   uploadedFile.mkdirs();
		           }*/
		   	       fileItem.write(uploadedFile);   //그리고 저장 (저장 실패시 temp 폴더에 파일 남아있는데 저장이 완료되면 임시저장장소에 있는 파일 자동 삭제)
		   	       dto.setPro_img(fileName);
		         }
		      } else {
		    	  dto.setPro_img(requestWrap.getParameter("org_user_img"));
		      }
		    }
		}
		
		//String encPwd = enc.getHash(requestWrap.getParameter("password")).toString();
		//파일 업로드를 완료한 다음, 디비에 입력
		//dto.setId(requestWrap.getParameter("user_id"));
		//dto.setPassword(encPwd);
		dto.setCate(requestWrap.getParameter("cate"));
		dto.setEmail(requestWrap.getParameter("email"));
		dto.setHandphone(requestWrap.getParameter("handphone"));
		dto.setSignature(common.replaceBr(requestWrap.getParameter("signature")));
		dto.setProfile(common.replaceBr(requestWrap.getParameter("profile")));
		dto.setId(user_id);
		
		memberDAO mDAO = new memberDAO();
		
		result = mDAO.modifyMember(dto);//Y : 디비 입력 성공 N:실패
		
		request.setAttribute("result", result);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/member/modifydone.jsp");
		
		dispatcher.forward(request, response);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	protected void getList(HttpServletRequest request, HttpServletResponse response) {
		try {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/member/list.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void getListAjax(HttpServletRequest request, HttpServletResponse response) {
		String page_num = request.getParameter("page_num");
		String cate = request.getParameter("cate");
		System.out.println("====controll cate======="+cate);
		if( page_num == null || page_num.equals("") || page_num.equals("null") ){
			page_num = "0";
		}
		if( cate == null || cate.equals("") || cate.equals("null") ){
			cate = "00";
		}

		List ls = new ArrayList();
		
		try {
			memberDAO mDAO = new memberDAO();
			ls = mDAO.getMemberList(page_num, cate);
			
			request.setAttribute("list", ls);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/member/listajax.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void getLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setAttribute("errormessage", "");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/member/login.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void doLogin(HttpServletRequest request, HttpServletResponse response) {
		String user_id = common.nulltovoid(request.getParameter("user_id"));
		String password = common.nulltovoid(request.getParameter("pwd"));
		String org_pwd = "";
		String return_url = "";
		String gubun = "";
		List ls = new ArrayList();
		try {
		encrypUtil enc = new encrypUtil();
		String encPwd = enc.getHash(password).toString();
		memberDAO mDAO = new memberDAO();
		ls = mDAO.getMemberInfo(user_id);
		
		if (ls.size() != 0) {
			for(int i=0;i<ls.size();i++) { 
				memberDTO dto = (memberDTO)ls.get(i);
				org_pwd = dto.getPassword();
				gubun = dto.getGubun();
			}
		}
		
		HttpSession session = request.getSession(true);
		
		if (encPwd.equals(org_pwd)) {
			//로그인이 정상적으로 이루어질 경우, 세션 관련 처리.
			session.setMaxInactiveInterval(60*60*3);
			session.setAttribute("login_id", user_id);
			session.setAttribute("gubun", gubun);
			return_url = "/index.jsp";
		} else {
			session.setAttribute("login_id", "");
			if (org_pwd.equals("")) {//디비에 아이디가 존재하지 않음
				request.setAttribute("errormessage", "noid");
			} else {//아이디가 틀림
				request.setAttribute("errormessage", "wrongpassword");
			}
			return_url = "/jsp/member/login.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(return_url);
		dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doLogout(HttpServletRequest request, HttpServletResponse response) {
		try {
		HttpSession session = request.getSession(true);
		//session.setAttribute("login_id", "");
		session.invalidate();	
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void getProfile(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));
		
		List ls = new ArrayList();
		
		try {
			memberDAO mDAO = new memberDAO();
			ls = mDAO.getMemberInfo(user_id);
			request.setAttribute("list", ls);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/member/profile.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected void getListDetail(HttpServletRequest request, HttpServletResponse response) {
		String user_id = common.nulltovoid(request.getParameter("user_id"));
		List ls = new ArrayList();
		
		try {
			memberDAO mDAO = new memberDAO();
			ls = mDAO.getMemberInfo(user_id);
			request.setAttribute("list", ls);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/member/listdetail.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String getExt(String filename) {
		if("".equals(filename)) {
			return "";
		}
		return filename.substring(filename.lastIndexOf("."),filename.length());
	}
}
