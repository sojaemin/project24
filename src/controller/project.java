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

import db.dao.applyDAO;
import db.dao.projectDAO;
import db.dto.projectDTO;

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
public class project extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public project() {
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
		 if (request.getParameter("method").equals("getList")) {
			 getList(request, response);
			 return;
		} else if (request.getParameter("method").equals("getListAjax")) {
			getListAjax(request, response);
			return;
		} else if(request.getParameter("method").equals("insertNew")) {
			insertNew(request, response);
			return;
		} else if(request.getParameter("method").equals("goNew")) {
			goNew(request, response);
			return;
		} else if(request.getParameter("method").equals("goModify")) {
			goModify(request, response);
			return;
		} else if(request.getParameter("method").equals("modifyProject")) {
			modifyProject(request, response);
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
	
	private String chkSession(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));
		
		String loginYN = "N";
		if (user_id.length() > 0) {
			loginYN = "Y";
		}
		return loginYN;
	}
	
	protected void goNew(HttpServletRequest request, HttpServletResponse response) {
		try {
		if (chkSession(request).equals("N")) {
			response.sendRedirect("/member.do?method=getLogin");
		} else {
			HttpSession session = request.getSession(true);
			
			String gubun = common.nulltovoid((String) session.getAttribute("gubun"));
			System.out.println(gubun);
			if (gubun.equals("C")) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project/new.jsp");
				dispatcher.forward(request, response);
			} else if (gubun.equals("F")) {
				response.sendRedirect("/project.do?method=getList&error=noco");
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void insertNew(HttpServletRequest request, HttpServletResponse response) {
		String resultno = "";
		projectDTO dto = new projectDTO();
		Date current = new Date();
		encrypUtil enc = new encrypUtil();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String currentDate =  dateFormat.format(current);
				
		try {

		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));

		//파일 업로드를 완료한 다음, 디비에 입력
		dto.setCate(request.getParameter("cate"));
		dto.setStatus(request.getParameter("status"));
		dto.setAmount(request.getParameter("amount"));
		dto.setJobdate(request.getParameter("jobdate"));
		dto.setTitle(request.getParameter("title"));
		dto.setContext(common.replaceBr(request.getParameter("context")));
		dto.setWriter_id(user_id);
		
		projectDAO pDAO = new projectDAO();
		
		resultno = pDAO.insertNew(dto);//Y : 디비 입력 성공 N:실패
		
		
		//리스트페이지로 넘겨줌
		response.sendRedirect("/project.do?method=getList");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void goModify(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));
		
		String no = common.nulltovoid(request.getParameter("no"));
		List ls = new ArrayList();		
		String org_user_id = "";

		try {
			projectDAO pDAO = new projectDAO();
			ls = pDAO.getListDetail(common.str2int(no));
			request.setAttribute("list", ls);
				
			if (ls.size() != 0) {

				for(int i=0;i<ls.size();i++) { 
					projectDTO dto = (projectDTO)ls.get(i);
					org_user_id = dto.getWriter_id();
				}
			}
			
			if (user_id.equals(org_user_id)) { //권한이 있으면 정상적으로 페이지 이동
				request.setAttribute("list", ls);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project/modify_project.jsp");
				dispatcher.forward(request, response);
			} else {
				//리스트페이지로 넘겨줌
				response.sendRedirect("/project.do?method=getList");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	
	protected void modifyProject(HttpServletRequest request, HttpServletResponse response) {
		String result = "N";
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));
		
		String no = common.nulltovoid(request.getParameter("no"));
		List ls = new ArrayList();		
		String org_user_id = "";
		
		try {
		projectDTO dto = new projectDTO();
		projectDAO pDAO = new projectDAO();
		ls = pDAO.getListDetail(common.str2int(no));
			
		if (ls.size() != 0) {

			for(int i=0;i<ls.size();i++) { 
				dto = (projectDTO)ls.get(i);
				org_user_id = dto.getWriter_id();
			}
		}
		if (user_id.equals(org_user_id)) { //권한이 있으면 정상적으로 페이지 이동
			dto.setCate(request.getParameter("cate"));
			System.out.println(request.getParameter("status"));
			dto.setStatus(request.getParameter("status"));
			dto.setAmount(request.getParameter("amount"));
			dto.setJobdate(request.getParameter("jobdate"));
			dto.setTitle(request.getParameter("title"));
			dto.setContext(common.replaceBr(request.getParameter("context")));
			dto.setNo(no);
			
			result = pDAO.modifyProject(dto);//Y : 디비 입력 성공 N:실패
			if (result.equals("Y")) {
				response.sendRedirect("/project.do?method=getListDetail&no="+no);
			} else {
				response.sendRedirect("/project.do?method=getList");
			}
			
		} else {
			response.sendRedirect("/project.do?method=getList");
		}
		
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	protected void getList(HttpServletRequest request, HttpServletResponse response) {
		try {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project/list.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void getListAjax(HttpServletRequest request, HttpServletResponse response) {
		String page_num = request.getParameter("page_num");
		String cate = request.getParameter("cate");
		String search_val = request.getParameter("search_val");
		if( page_num == null || page_num.equals("") || page_num.equals("null") ){
			page_num = "0";
		}
		if( cate == null || cate.equals("") || cate.equals("null") ){
			cate = "00";
		}

		List ls = new ArrayList();
		
		try {
			projectDAO pDAO = new projectDAO();
			ls = pDAO.getProjectList(page_num, cate, search_val);
			
			request.setAttribute("list", ls);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project/listajax.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	protected void getListDetail(HttpServletRequest request, HttpServletResponse response) {
		String no = common.nulltovoid(request.getParameter("no"));
		List ls = new ArrayList();
		List ls_apply = new ArrayList();
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));
		String org_user_id = "";
		String modifyRight = "N";
				
		try {
			projectDAO pDAO = new projectDAO();
			applyDAO aDAO = new applyDAO();
			
			ls = pDAO.getListDetail(common.str2int(no));
			request.setAttribute("list", ls);
			
			ls_apply = aDAO.getApplyList(no);
			request.setAttribute("list_apply", ls_apply);
			
			if (ls.size() != 0) {

				for(int i=0;i<ls.size();i++) { 
					projectDTO dto = (projectDTO)ls.get(i);
					org_user_id = dto.getWriter_id();
				}
			}
			
			if (user_id.equals(org_user_id)) {
				modifyRight = "Y";
			}
			request.setAttribute("modifyRight", modifyRight);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/project/listdetail.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
