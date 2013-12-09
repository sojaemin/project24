package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

import org.apache.commons.fileupload.FileItem;

import util.FileUploadRequestWrapper;
import util.common;
import util.encrypUtil;
import db.dao.memberDAO;
import db.dao.projectDAO;
import db.dto.memberDTO;

/**
 * Servlet implementation class member
 */

public class member extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public member() {
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
		} else if(request.getParameter("method").equals("getApplyList")) {
			getApplyList(request, response);
			return;
		} else if(request.getParameter("method").equals("getApplyListAjax")) {
			getApplyListAjax(request, response);
			return;
		} else if(request.getParameter("method").equals("getWorkList")) {
			getWorkList(request, response);
			return;
		} else if(request.getParameter("method").equals("getWorkListAjax")) {
			getWorkListAjax(request, response);
			return;
		} else if(request.getParameter("method").equals("getDoneList")) {
			getDoneList(request, response);
			return;
		} else if(request.getParameter("method").equals("getDoneListAjax")) {
			getDoneListAjax(request, response);
			return;
		} else if(request.getParameter("method").equals("getPasswordChg")) {
			getPasswordChg(request, response);
			return;
		} else if(request.getParameter("method").equals("setPasswordChg")) {
			setPasswordChg(request, response);
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
	
	
/*	protected void insertRegist(HttpServletRequest request, HttpServletResponse response) {
		String result = "N";
		String dupYN = "N";
		memberDTO dto = new memberDTO();
		Date current = new Date();
		encrypUtil enc = new encrypUtil();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String currentDate =  dateFormat.format(current);
			
		//경로
		String path = null;
		//파일
		Part filePart = null;
		//파일이름
		String fileName = null;

		OutputStream out = null;
		InputStream filecontent = null;
		PrintWriter writer = null;
		try {
			
			path = "C:\\jspbook\\workspace\\jspbook\\chap11\\WebContent\\data";//request.getParameter("destination");
			filePart = request.getPart("user_img");
			
			if (filePart.getSize() > 0) {
				fileName = getFileName(filePart);
				response.setHeader("Content-Type", "text/html; charset=UTF-8");// Http 헤더에도 utf-8로 찍히며 전송될 데이터의 인코딩도 utf-8로 내보내게 된다.
				//항상 response.getWriter() 전에 나와야 한다.
				//그렇지 않으면, 완료 페이지에 한글이 깨져서 나오게 된다.

				writer = response.getWriter();
				
				fileName = System.currentTimeMillis()+getExt(fileName);//확장자와 현재 시간을 조합해서 새 파일명을 만든다.
				out = new FileOutputStream(new File(path + File.separator + fileName ));
				filecontent = filePart.getInputStream();
				
				int read = 0;
				final byte[] bytes = new byte[1024];
				
				while( (read = filecontent.read(bytes)) != -1){
					out.write(bytes, 0, read);
				}
				//new File Tulips.jpg C:\jspbook\workspace\jspbook\chap11\WebContent\data에생성되었습니다.
				dto.setPro_img(fileName);
			}
			
		
		String encPwd = enc.getHash(request.getParameter("password")).toString();
		//파일 업로드를 완료한 다음, 디비에 입력
		dto.setId(request.getParameter("user_id"));
		dto.setPassword(encPwd);
		dto.setGubun(request.getParameter("gubun"));
		dto.setCate(request.getParameter("cate"));
		dto.setEmail(request.getParameter("email"));
		dto.setHandphone(request.getParameter("handphone"));
		dto.setSignature(common.replaceBr(request.getParameter("signature")));
		dto.setProfile(common.replaceBr(request.getParameter("profile")));
		
		memberDAO mDAO = new memberDAO();
		
		//데이타베이스에 입력전에 아이디가 중복되는지 체크
		dupYN = mDAO.chkMemberID(request.getParameter("user_id"));
		if (dupYN.equals("Y")) { //중복이면 바로 결과 화면으로 보낸다.
			result = "D"; //결과에 중복입을 알려줌
		} else {// 중복이 아니면 데이타베이스에 입력한다.
			result = mDAO.insertMember(dto);//Y : 디비 입력 성공 N:실패
		}
		
		if(result.equals("Y")) {//로그인이 정상적으로 되면 세션을 살려줌
			HttpSession session = request.getSession(true);
			session.setMaxInactiveInterval(60*60*3);
			session.setAttribute("login_id", request.getParameter("user_id"));
			session.setAttribute("gubun", request.getParameter("gubun"));
		}
		
		request.setAttribute("result", result);
		request.setAttribute("user_id", request.getParameter("user_id"));
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/member/registdone.jsp");
		
		dispatcher.forward(request, response);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	protected void insertRegist(HttpServletRequest request, HttpServletResponse response) {
		String result = "N";
		String dupYN = "N";
		memberDTO dto = new memberDTO();
		Date current = new Date();
		encrypUtil enc = new encrypUtil();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String currentDate =  dateFormat.format(current);
				
		try {
		FileUploadRequestWrapper requestWrap = new FileUploadRequestWrapper(request, -1, -1, getServletContext().getRealPath("/")+"data");

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
		           File uploadedFile = new File (getServletContext().getRealPath("/")+"data", fileName);
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
	
	/*protected void modifyRegist(HttpServletRequest request, HttpServletResponse response) {
		String result = "N";
		String dupYN = "N";
		memberDTO dto = new memberDTO();
		Date current = new Date();
		encrypUtil enc = new encrypUtil();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String currentDate =  dateFormat.format(current);
		
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));
		
        //경로
		String path = null;
		//파일
		Part filePart = null;
		//파일이름
		String fileName = null;

		OutputStream out = null;
		InputStream filecontent = null;
		PrintWriter writer = null;
		try {
			path = "C:\\jspbook\\workspace\\jspbook\\chap11\\WebContent\\data";//request.getParameter("destination");
			filePart = request.getPart("user_img");
			
			if (filePart.getSize() > 0) {
				fileName = getFileName(filePart);
				response.setHeader("Content-Type", "text/html; charset=UTF-8");// Http 헤더에도 utf-8로 찍히며 전송될 데이터의 인코딩도 utf-8로 내보내게 된다.
				//항상 response.getWriter() 전에 나와야 한다.
				//그렇지 않으면, 완료 페이지에 한글이 깨져서 나오게 된다.

				writer = response.getWriter();
				
				fileName = System.currentTimeMillis()+getExt(fileName);//확장자와 현재 시간을 조합해서 새 파일명을 만든다.
				out = new FileOutputStream(new File(path + File.separator + fileName ));
				filecontent = filePart.getInputStream();
				
				int read = 0;
				final byte[] bytes = new byte[1024];
				
				while( (read = filecontent.read(bytes)) != -1){
					out.write(bytes, 0, read);
				}
				//new File Tulips.jpg C:\jspbook\workspace\jspbook\chap11\WebContent\data에생성되었습니다.
				dto.setPro_img(fileName);
			} else {
				dto.setPro_img(request.getParameter("org_user_img"));
			}
		
		//String encPwd = enc.getHash(requestWrap.getParameter("password")).toString();
		//파일 업로드를 완료한 다음, 디비에 입력
		//dto.setId(requestWrap.getParameter("user_id"));
		//dto.setPassword(encPwd);
		dto.setCate(request.getParameter("cate"));
		dto.setEmail(request.getParameter("email"));
		dto.setHandphone(request.getParameter("handphone"));
		dto.setSignature(common.replaceBr(request.getParameter("signature")));
		dto.setProfile(common.replaceBr(request.getParameter("profile")));
		dto.setId(user_id);
		
		memberDAO mDAO = new memberDAO();
		
		result = mDAO.modifyMember(dto);//Y : 디비 입력 성공 N:실패
		
		request.setAttribute("result", result);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/member/modifydone.jsp");
		
		dispatcher.forward(request, response);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if( out != null ){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}if(filecontent != null ){
				try {
					filecontent.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			if( writer != null){
				writer.close();
			}
		}
	}*/
	protected void modifyRegist(HttpServletRequest request, HttpServletResponse response) {
		String result = "N";
		String dupYN = "N";
		memberDTO dto = new memberDTO();
		Date current = new Date();
		encrypUtil enc = new encrypUtil();
		System.out.println(getServletContext().getRealPath("/"));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String currentDate =  dateFormat.format(current);
		
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));
		
		try {
		FileUploadRequestWrapper requestWrap = new FileUploadRequestWrapper(request, -1, -1, getServletContext().getRealPath("/")+"data");

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
		           File uploadedFile = new File (getServletContext().getRealPath("/")+"data", fileName);
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
		String gubun = common.nulltovoid(request.getParameter("gubun"));
		String search_val = request.getParameter("search_val");

		if( page_num == null || page_num.equals("") || page_num.equals("null") ){
			page_num = "0";
		}
		if( cate == null || cate.equals("") || cate.equals("null") ){
			cate = "00";
		}
		try {
		List ls = new ArrayList();
		
		if (gubun == null || gubun.equals("")) {
			gubun = "F";
		}
			memberDAO mDAO = new memberDAO();
			if (gubun.equals("F")) {
				ls = mDAO.getMemberList(page_num, cate, search_val);
			} else if (gubun.equals("C")) {
				ls = mDAO.getCoMemberList(page_num, cate, search_val);
			}
			
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
	
	
	protected void getApplyList(HttpServletRequest request, HttpServletResponse response) {
		try {
			///jsp/member/applylist.jsp 로 그대로 넘긴다.
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/member/applylist.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void getApplyListAjax(HttpServletRequest request, HttpServletResponse response) {
		String page_num = request.getParameter("page_num"); //페이징에 필요한 페이지 번호
		String cate = request.getParameter("cate"); //카테고리 구분 값이 필요할 경우 가져온다.
		String search_val = request.getParameter("search_val"); //검색 기능에 필요한 검색어
		String gubun = request.getParameter("gubun"); //회원구분 값, 프리랜서 : F, 기업회원 : C
		HttpSession session = request.getSession(true);//세션값을 생성한다.
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));//세션에서 로그인 아이디를 가져온다.
		
		if( page_num == null || page_num.equals("") || page_num.equals("null") ){
			//페이지 번호가 없을땐, 0으로 세팅해 준다.
			page_num = "0";
		}
		if( cate == null || cate.equals("") || cate.equals("null") ){
			//카테고리 값이 없으면 00으로 세팅해 준다.
			cate = "00";
		}

		List ls = new ArrayList();
		
		try {
			memberDAO mDAO = new memberDAO();//데이타베이스에 접근을 하기위해, memberDAO 객체를 생성한다.
			if (gubun.equals("C")) {//회원구분이 C,즉 기업 회원일 경우 
				ls = mDAO.getCoApplyList(page_num, cate, search_val, user_id);
			} else if (gubun.equals("F")) {//회원구빈이 F, 프리랜서일 경우
				ls = mDAO.getApplyList(page_num, cate, search_val, user_id);
			}
			
			request.setAttribute("list", ls);//DAO에서 조회한 리스트를 request 객체에 포함시켜 jsp로 전달한다.
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/member/applylistajax.jsp");//dispatcher 세팅
			dispatcher.forward(request, response);//jsp로 전송
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	protected void getWorkList(HttpServletRequest request, HttpServletResponse response) {
		try {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/member/worklist.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void getWorkListAjax(HttpServletRequest request, HttpServletResponse response) {
		String page_num = request.getParameter("page_num");
		String cate = request.getParameter("cate");
		String search_val = request.getParameter("search_val");
		String gubun = request.getParameter("gubun");
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));
		
		if( page_num == null || page_num.equals("") || page_num.equals("null") ){
			page_num = "0";
		}
		if( cate == null || cate.equals("") || cate.equals("null") ){
			cate = "00";
		}

		List ls = new ArrayList();
		
		try {
			memberDAO mDAO = new memberDAO();
			if (gubun.equals("C")) {
				ls = mDAO.getCoWorkList(page_num, cate, search_val, user_id);
			} else if (gubun.equals("F")) {
				ls = mDAO.getWorkList(page_num, cate, search_val, user_id);
			}
			
			
			request.setAttribute("list", ls);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/member/worklistajax.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	protected void getDoneList(HttpServletRequest request, HttpServletResponse response) {
		try {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/member/donelist.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void getDoneListAjax(HttpServletRequest request, HttpServletResponse response) {
		String page_num = request.getParameter("page_num");
		String cate = request.getParameter("cate");
		String search_val = request.getParameter("search_val");
		String gubun = request.getParameter("gubun");
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));
		
		if( page_num == null || page_num.equals("") || page_num.equals("null") ){
			page_num = "0";
		}
		if( cate == null || cate.equals("") || cate.equals("null") ){
			cate = "00";
		}

		List ls = new ArrayList();
		
		try {
			memberDAO mDAO = new memberDAO();
			if (gubun.equals("C")) {
				ls = mDAO.getCoDoneList(page_num, cate, search_val, user_id);
			} else if (gubun.equals("F")) {
				ls = mDAO.getDoneList(page_num, cate, search_val, user_id);
			}
			
			request.setAttribute("list", ls);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/member/donelistajax.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	protected void getPasswordChg(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));
		String return_url = "";
		List ls = new ArrayList();
		
		try {
			if (user_id.equals("null") || user_id == null ||user_id.equals("")) {
				return_url = "/jsp/member/login.jsp";
			} else {
				return_url = "/jsp/member/getPasswordChg.jsp";
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(return_url);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void setPasswordChg(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));
		String return_url = "";
		System.out.println("setpasswordchg"+user_id+"/");
		try {
			memberDTO dto = new memberDTO();
			encrypUtil enc = new encrypUtil();
			String encPwd = enc.getHash(request.getParameter("password")).toString();
			System.out.println("setpasswordchg"+encPwd);
			memberDAO mDAO = new memberDAO();
			mDAO.chgPassword(user_id, encPwd);
			request.setAttribute("status", "done");		
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/member/getPasswordChg.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
