package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.common;
import util.encrypUtil;
import db.dao.applyDAO;
import db.dao.projectDAO;
import db.dto.applyDTO;

/**
 * Servlet implementation class member
 */
public class apply extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public apply() {
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
		//url : /apply.do?method=?
		 if(request.getParameter("method").equals("insertNew")) {
			insertNew(request, response);
			return;
		} else if(request.getParameter("method").equals("delApply")) {
			delApply(request, response);
			return;
		} else if(request.getParameter("method").equals("updateApply")) {
			updateApply(request, response);
			return;
		} else if(request.getParameter("method").equals("selectFreelancer")) {
			selectFreelancer(request, response);
			return;
		} else if(request.getParameter("method").equals("setDone")) {
			setDone(request, response);
			return;
		} else if(request.getParameter("method").equals("setCancel")) {
			setCancel(request, response);
			return;
		}
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void insertNew(HttpServletRequest request, HttpServletResponse response) {
		String resultno = "";
		applyDTO dto = new applyDTO();
		Date current = new Date();
		encrypUtil enc = new encrypUtil();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String currentDate =  dateFormat.format(current);
				
		try {

		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));

		dto.setApply_no(request.getParameter("no"));
		dto.setContext(request.getParameter("apply_context"));
		dto.setUser_id(user_id);
		
		applyDAO aDAO = new applyDAO();
		
		resultno = aDAO.insertNew(dto);//Y : 디비 입력 성공 N:실패
		
		//프로젝트 리스트페이지로 넘겨줌
		response.sendRedirect("/project.do?method=getListDetail&no="+request.getParameter("no"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void delApply(HttpServletRequest request, HttpServletResponse response) {
		String result = "N";
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));
		
		String apply_no = common.nulltovoid(request.getParameter("apply_no"));
		String no = common.nulltovoid(request.getParameter("no"));
		
		List ls = new ArrayList();		
		String org_user_id = "";
		
		try {
		applyDTO dto = new applyDTO();
		applyDAO pDAO = new applyDAO();
		org_user_id = pDAO.getApplyUserId(apply_no);
		if (user_id.equals(org_user_id)) { //권한이 있으면 정상적으로 페이지 이동
			result = pDAO.delApply(apply_no, no);
		}
		
		response.sendRedirect("/project.do?method=getListDetail&no="+no);
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void updateApply(HttpServletRequest request, HttpServletResponse response) {
		String result = "N";
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));
		
		String apply_no = common.nulltovoid(request.getParameter("apply_no"));
		String apply_text = common.nulltovoid(request.getParameter("apply_text"));
		String no = common.nulltovoid(request.getParameter("no"));
		
		List ls = new ArrayList();		
		String org_user_id = "";
		
		try {
		applyDTO dto = new applyDTO();
		applyDAO pDAO = new applyDAO();
		org_user_id = pDAO.getApplyUserId(apply_no);
		System.out.println("===apply_no======"+apply_no);
		System.out.println("===apply_text======"+apply_text);
		System.out.println("===no======"+no);
		if (user_id.equals(org_user_id)) { //권한이 있으면 정상적으로 페이지 이동
			result = pDAO.updateApply(apply_no, apply_text);
			System.out.println("===success======");
		}
		
		response.sendRedirect("/project.do?method=getListDetail&no="+no);
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void selectFreelancer(HttpServletRequest request, HttpServletResponse response) {
		String result = "N";
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));
		String seluserId = common.nulltovoid(request.getParameter("seluserid"));
		String apply_no = common.nulltovoid(request.getParameter("apply_no"));
		
		List ls = new ArrayList();		
		String org_user_id = "";
		
		try {
		projectDAO pDAO = new projectDAO();
		applyDAO aDAO = new applyDAO();
		org_user_id = pDAO.getWriterId(apply_no);

		if (user_id.equals(org_user_id)) { //권한이 있으면 정상적으로 페이지 이동
			System.out.println(org_user_id);
			System.out.println(seluserId);
			System.out.println(apply_no);
			result = aDAO.selectFreelancer(seluserId, apply_no);
		}
		
		response.sendRedirect("/project.do?method=getListDetail&no="+apply_no);
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void setDone(HttpServletRequest request, HttpServletResponse response) {
		String result = "N";
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));
		String apply_no = common.nulltovoid(request.getParameter("apply_no"));
		
		String org_user_id = "";
		
		try {
		projectDAO pDAO = new projectDAO();
		applyDAO aDAO = new applyDAO();
		org_user_id = pDAO.getWriterId(apply_no);
		if (user_id.equals(org_user_id)) { //권한이 있으면 정상적으로 페이지 이동
			result = aDAO.setStatus("03", apply_no);
		}
		
		response.sendRedirect("/project.do?method=getListDetail&no="+apply_no);
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void setCancel(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("d");
		String result = "N";
		HttpSession session = request.getSession(true);
		String user_id = common.nulltovoid((String) session.getAttribute("login_id"));
		String apply_no = common.nulltovoid(request.getParameter("apply_no"));
		
		String org_user_id = "";
		
		try {
		projectDAO pDAO = new projectDAO();
		applyDAO aDAO = new applyDAO();
		org_user_id = pDAO.getWriterId(apply_no);

		if (user_id.equals(org_user_id)) { //권한이 있으면 정상적으로 페이지 이동
			result = aDAO.setStatus("04", apply_no);
		}
		
		response.sendRedirect("/project.do?method=getListDetail&no="+apply_no);
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
