/**
 * 
 */
package db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import db.dto.memberDTO;
import db.dto.projectDTO;

/**
 * @author sojaemin
 *
 */
public class memberDAO {
	DataSource ds;
	Connection conn;
	PreparedStatement pst;
	public memberDAO() throws SQLException {
		Context init;
		try {
			init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/jspbook");
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	public String chkMemberID(String userId) {
		String dupIdYn = "N"; //Y:dup N:no dup
		PreparedStatement pst = null;
		try {
		conn = ds.getConnection();
		pst = conn.prepareStatement("select count(id) as cnt from member where id = ?;");
		pst.setString(1, userId);
		
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {
			if(rs.getInt("cnt") > 0) {
				dupIdYn = "Y";
			}
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pst!=null) {
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return dupIdYn;
	}
	
	public String insertMember(memberDTO dto) {
		System.out.println("insertMember!!!");
		int intResult = 0;
		String strResult = "N";
		
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement("insert into member(id, password, gubun, email, handphone, signature, profile, regist_date, pro_img, cate) values (?,?,?,?,?,?,?, now(), ?,?);");
			pst.setString(1,dto.getId());
			pst.setString(2,dto.getPassword());
			pst.setString(3,dto.getGubun());
			pst.setString(4,dto.getEmail());
			pst.setString(5,dto.getHandphone());
			pst.setString(6,dto.getSignature());
			pst.setString(7,dto.getProfile());
			pst.setString(8,dto.getPro_img());
			pst.setString(9,dto.getCate());
			intResult = pst.executeUpdate();
		
			if (intResult>0) { //1보다 크므로 성공
				strResult = "Y";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(pst!=null) {
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return strResult;
	}
	
	public String modifyMember(memberDTO dto) {
		int intResult = 0;
		String strResult = "N";
		
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement("update member set email = ? , handphone = ?, signature = ?, profile = ?, pro_img = ?, cate= ? where id = ?;");
			pst.setString(1,dto.getEmail());
			pst.setString(2,dto.getHandphone());
			pst.setString(3,dto.getSignature());
			pst.setString(4,dto.getProfile());
			pst.setString(5,dto.getPro_img());
			pst.setString(6,dto.getCate());
			pst.setString(7,dto.getId());
			
			intResult = pst.executeUpdate();
		
			if (intResult>0) { //1보다 크므로 성공
				strResult = "Y";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(pst!=null) {
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return strResult;
	}
	
	
	public List getMemberList(String page_no, String cate, String search_val) {
		List list = new ArrayList();
		
		int startno = (Integer.parseInt(page_no) + 1);//(Integer.parseInt(page_no) - 1)+10+1;
		int endno = startno + 19;//startno + 10 - 1;
		System.out.println("======"+startno);
		System.out.println("======"+endno);
		try {
			conn = ds.getConnection();
			if (cate.equals("00")) {
				pst = conn.prepareStatement("SELECT RM, T2.* FROM( " +
						"SELECT @RNUM:=@RNUM+1 AS RM, T1.* " +
						"FROM (SELECT * FROM member WHERE PROFILE LIKE ? AND gubun = 'F' ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R " + 
						")  AS T2 " +
						"WHERE RM >= ? AND RM <= ?;");
				pst.setString(1, "%"+search_val+"%");
				pst.setInt(2, startno);
				pst.setInt(3, endno);
			} else {
			pst = conn.prepareStatement("SELECT RM, T2.* FROM( " +
					"SELECT @RNUM:=@RNUM+1 AS RM, T1.* " +
					"FROM (SELECT * FROM member WHERE PROFILE LIKE ? AND gubun = 'F' AND CATE = ? ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R " + 
					")  AS T2 " +
					"WHERE RM >= ? AND RM <= ?;");
			pst.setString(1, "%"+search_val+"%");
			pst.setString(2, cate);
			pst.setInt(3, startno);
			pst.setInt(4, endno);
			}
			
			ResultSet rs= pst.executeQuery();
			
			while(rs.next()) {
				memberDTO dto = new memberDTO();
				dto.setRm(rs.getString("rm"));
				dto.setNo(rs.getString("no"));
				dto.setId(rs.getString("id"));
				dto.setCate(rs.getString("cate"));
				dto.setProfile(rs.getString("profile"));
				dto.setRegist_date(rs.getString("regist_date"));
				dto.setApply_cnt(rs.getInt("apply_cnt"));
				dto.setDone_cnt(rs.getInt("done_cnt"));
				dto.setSelected_cnt(rs.getInt("selected_cnt"));
				list.add(dto);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(pst!=null) {
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				//커넥션을 꼭 닫아주지 않으면, 간헐적인 디비 접속 오류가 난다.
				try {
					
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return list;
	}
	
	public List getCoMemberList(String page_no, String cate, String search_val) {
		List list = new ArrayList();
		
		int startno = (Integer.parseInt(page_no) + 1);//(Integer.parseInt(page_no) - 1)+10+1;
		int endno = startno + 19;//startno + 10 - 1;
		System.out.println("======"+startno);
		System.out.println("======"+endno);
		try {
			conn = ds.getConnection();
			if (cate.equals("00")) {
				pst = conn.prepareStatement("SELECT RM, T2.* FROM( " +
						"SELECT @RNUM:=@RNUM+1 AS RM, T1.* " +
						"FROM (SELECT * FROM member WHERE PROFILE LIKE ? AND gubun = 'C' ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R " + 
						")  AS T2 " +
						"WHERE RM >= ? AND RM <= ?;");
				pst.setString(1, "%"+search_val+"%");
				pst.setInt(2, startno);
				pst.setInt(3, endno);
			} else {
			pst = conn.prepareStatement("SELECT RM, T2.* FROM( " +
					"SELECT @RNUM:=@RNUM+1 AS RM, T1.* " +
					"FROM (SELECT * FROM member WHERE PROFILE LIKE ? AND gubun = 'C' AND CATE = ? ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R " + 
					")  AS T2 " +
					"WHERE RM >= ? AND RM <= ?;");
			pst.setString(1, "%"+search_val+"%");
			pst.setString(2, cate);
			pst.setInt(3, startno);
			pst.setInt(4, endno);
			}
			
			ResultSet rs= pst.executeQuery();
			
			while(rs.next()) {
				memberDTO dto = new memberDTO();
				dto.setRm(rs.getString("rm"));
				dto.setNo(rs.getString("no"));
				dto.setId(rs.getString("id"));
				dto.setCate(rs.getString("cate"));
				dto.setProfile(rs.getString("profile"));
				dto.setRegist_date(rs.getString("regist_date"));
				dto.setApply_cnt(rs.getInt("apply_cnt"));
				dto.setDone_cnt(rs.getInt("done_cnt"));
				dto.setSelected_cnt(rs.getInt("selected_cnt"));
				list.add(dto);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(pst!=null) {
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				//커넥션을 꼭 닫아주지 않으면, 간헐적인 디비 접속 오류가 난다.
				try {
					
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return list;
	}
	
	public List getMemberInfo(String user_id) {
		List list = new ArrayList();
		
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement("select * from member where id = ?;");
			pst.setString(1, user_id);
			ResultSet rs= pst.executeQuery();
			
			while(rs.next()) {
				memberDTO dto = new memberDTO();
				
				dto.setId(rs.getString("id"));
				dto.setPassword(rs.getString("password"));
				dto.setGubun(rs.getString("gubun"));
				dto.setCate(rs.getString("cate"));
				dto.setEmail(rs.getString("email"));
				dto.setHandphone(rs.getString("handphone"));
				dto.setProfile(rs.getString("profile"));
				dto.setSignature(rs.getString("signature"));
				dto.setRegist_date(rs.getString("regist_date"));
				dto.setLast_login(rs.getString("last_login"));
				dto.setPro_img(rs.getString("pro_img"));
				dto.setApply_cnt(rs.getInt("apply_cnt"));
				dto.setSelected_cnt(rs.getInt("selected_cnt"));
				dto.setDone_cnt(rs.getInt("done_cnt"));
				
				list.add(dto);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(pst!=null) {
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return list;
	}

	public List getApplyList(String page_no, String cate, String search_val, String user_id) {
		List list = new ArrayList();
		
		int startno = (Integer.parseInt(page_no) + 1);
		int endno = startno + 9;
		try {
			conn = ds.getConnection();
			if (cate.equals("00")) {
				pst = conn.prepareStatement("SELECT RM, T2.* FROM( " +
						"SELECT @RNUM:=@RNUM+1 AS RM, T1.* " +
						"FROM (SELECT A.* FROM project A, apply B WHERE A.CONTEXT LIKE ? AND A.no = B.apply_no AND B.user_id = ? ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R " + 
						")  AS T2 " +
						"WHERE RM >= ? AND RM <= ?;");
				pst.setString(1, "%"+search_val+"%");
				pst.setString(2, user_id);
				pst.setInt(3, startno);
				pst.setInt(4, endno);
			} else {
			pst = conn.prepareStatement("SELECT RM, T2.* FROM( " +
					"SELECT @RNUM:=@RNUM+1 AS RM, T1.* " +
					"FROM (SELECT A.* FROM project A, apply B WHERE A.CONTEXT LIKE ? AND A.CATE = ? AND A.no = B.apply_no AND B.user_id = ? ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R " + 
					")  AS T2 " +
					"WHERE RM >= ? AND RM <= ?;");
			pst.setString(1, "%"+search_val+"%");
			pst.setString(2, cate);
			pst.setString(3, user_id);
			pst.setInt(4, startno);
			pst.setInt(5, endno);
			}
			ResultSet rs= pst.executeQuery();
			
			while(rs.next()) {
				projectDTO dto = new projectDTO();
				dto.setRm(rs.getString("rm"));
				dto.setNo(rs.getString("no"));
				dto.setWriter_id(rs.getString("writer_id"));
				dto.setCate(rs.getString("cate"));
				dto.setStatus(rs.getString("status"));
				dto.setTitle(rs.getString("title"));
				dto.setContext(rs.getString("context"));
				dto.setAmount(rs.getString("amount"));
				dto.setJobdate(rs.getString("jobdate"));
				dto.setRegist_date(rs.getString("regist_date"));
				dto.setModify_date(rs.getString("modify_date"));
				dto.setApply_cnt(rs.getInt("apply_cnt"));
				list.add(dto);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pst!=null) {
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return list;
	}
	
	public List getWorkList(String page_no, String cate, String search_val, String user_id) {
		List list = new ArrayList();
		
		int startno = (Integer.parseInt(page_no) + 1);//(Integer.parseInt(page_no) - 1)+10+1;
		int endno = startno + 9;//startno + 10 - 1;
		try {
			conn = ds.getConnection();
			if (cate.equals("00")) {
				pst = conn.prepareStatement("SELECT RM, T2.* FROM( " +
						"SELECT @RNUM:=@RNUM+1 AS RM, T1.* " +
						"FROM (SELECT * FROM project WHERE CONTEXT LIKE ? AND selected_id = ? AND status = '02' ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R " + 
						")  AS T2 " +
						"WHERE RM >= ? AND RM <= ?;");
				pst.setString(1, "%"+search_val+"%");
				pst.setString(2, user_id);
				pst.setInt(3, startno);
				pst.setInt(4, endno);
			} else {
			pst = conn.prepareStatement("SELECT RM, T2.* FROM( " +
					"SELECT @RNUM:=@RNUM+1 AS RM, T1.* " +
					"FROM (SELECT * FROM project WHERE CONTEXT LIKE ? AND CATE = ? AND selected_id = ? AND status = '02' ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R " + 
					")  AS T2 " +
					"WHERE RM >= ? AND RM <= ?;");
			pst.setString(1, "%"+search_val+"%");
			pst.setString(2, cate);
			pst.setString(3, user_id);
			pst.setInt(4, startno);
			pst.setInt(5, endno);
			}
			ResultSet rs= pst.executeQuery();
			
			while(rs.next()) {
				projectDTO dto = new projectDTO();
				dto.setRm(rs.getString("rm"));
				dto.setNo(rs.getString("no"));
				dto.setWriter_id(rs.getString("writer_id"));
				dto.setCate(rs.getString("cate"));
				dto.setStatus(rs.getString("status"));
				dto.setTitle(rs.getString("title"));
				dto.setContext(rs.getString("context"));
				dto.setAmount(rs.getString("amount"));
				dto.setJobdate(rs.getString("jobdate"));
				dto.setRegist_date(rs.getString("regist_date"));
				dto.setModify_date(rs.getString("modify_date"));
				dto.setApply_cnt(rs.getInt("apply_cnt"));
				list.add(dto);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(pst!=null) {
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return list;
	}
	
	public List getDoneList(String page_no, String cate, String search_val, String user_id) {
List list = new ArrayList();
		
		int startno = (Integer.parseInt(page_no) + 1);//(Integer.parseInt(page_no) - 1)+10+1;
		int endno = startno + 9;//startno + 10 - 1;
		try {
			conn = ds.getConnection();
			if (cate.equals("00")) {
				pst = conn.prepareStatement("SELECT RM, T2.* FROM( " +
						"SELECT @RNUM:=@RNUM+1 AS RM, T1.* " +
						"FROM (SELECT * FROM project WHERE CONTEXT LIKE ? AND selected_id = ? AND status = '03' ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R " + 
						")  AS T2 " +
						"WHERE RM >= ? AND RM <= ?;");
				pst.setString(1, "%"+search_val+"%");
				pst.setString(2, user_id);
				pst.setInt(3, startno);
				pst.setInt(4, endno);
			} else {
			pst = conn.prepareStatement("SELECT RM, T2.* FROM( " +
					"SELECT @RNUM:=@RNUM+1 AS RM, T1.* " +
					"FROM (SELECT * FROM project WHERE CONTEXT LIKE ? AND CATE = ? AND selected_id = ? AND status = '03' ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R " + 
					")  AS T2 " +
					"WHERE RM >= ? AND RM <= ?;");
			pst.setString(1, "%"+search_val+"%");
			pst.setString(2, cate);
			pst.setString(3, user_id);
			pst.setInt(4, startno);
			pst.setInt(5, endno);
			}
			ResultSet rs= pst.executeQuery();
			
			while(rs.next()) {
				projectDTO dto = new projectDTO();
				dto.setRm(rs.getString("rm"));
				dto.setNo(rs.getString("no"));
				dto.setWriter_id(rs.getString("writer_id"));
				dto.setCate(rs.getString("cate"));
				dto.setStatus(rs.getString("status"));
				dto.setTitle(rs.getString("title"));
				dto.setContext(rs.getString("context"));
				dto.setAmount(rs.getString("amount"));
				dto.setJobdate(rs.getString("jobdate"));
				dto.setRegist_date(rs.getString("regist_date"));
				dto.setModify_date(rs.getString("modify_date"));
				dto.setApply_cnt(rs.getInt("apply_cnt"));
				list.add(dto);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(pst!=null) {
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return list;
	}
	
	public List getCoApplyList(String page_no, String cate, String search_val, String user_id) {
		List list = new ArrayList();
		
		int startno = (Integer.parseInt(page_no) + 1);//조회해 올 글의 시작번호를 지정
		int endno = startno + 9;//조회해 올 마지막 글 번호를 지정
		try {
			conn = ds.getConnection();//커넥션을 지정
			if (cate.equals("00")) {//카테고리가 00일 경우
				pst = conn.prepareStatement("SELECT RM, T2.* FROM( " +
						"SELECT @RNUM:=@RNUM+1 AS RM, T1.* " +
						"FROM (SELECT * FROM project WHERE CONTEXT LIKE ? AND writer_id = ? AND status = '01' ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R " + 
						")  AS T2 " +
						"WHERE RM >= ? AND RM <= ?;");
				pst.setString(1, "%"+search_val+"%");//위 쿼리에서 첫번째 ?에 들어갈 값 세팅(CONTEXT LIKE ?)
				pst.setString(2, user_id);//위 쿼리에서 두번째 ?에 들어갈 값 세팅(writer_id = ?)
				pst.setInt(3, startno);//위 쿼리에서 세번째 ?에 들어갈 값 세팅(RM >= ?)
				pst.setInt(4, endno);//위 쿼리에서 네번째 ?에 들어갈 값 세팅(RM <= ?)
			} else {
			pst = conn.prepareStatement("SELECT RM, T2.* FROM( " +
					"SELECT @RNUM:=@RNUM+1 AS RM, T1.* " +
					"FROM (SELECT * FROM project WHERE CONTEXT LIKE ? AND CATE = ? AND writer_id = ? AND status = '01' ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R " + 
					")  AS T2 " +
					"WHERE RM >= ? AND RM <= ?;");
			pst.setString(1, "%"+search_val+"%");//위 쿼리에서 첫번째 ?에 들어갈 값 세팅(CONTEXT LIKE ?)
			pst.setString(2, cate);//위 쿼리에서 두번째 ?에 들어갈 값 세팅(CATE = ?)
			pst.setString(3, user_id);//위 쿼리에서 세번째 ?에 들어갈 값 세팅(writer_id = ?)
			pst.setInt(4, startno);//위 쿼리에서 네번째 ?에 들어갈 값 세팅(RM >= ?)
			pst.setInt(5, endno);//위 쿼리에서 다섯번째 ?에 들어갈 값 세팅(RM <= ?)
			}
			ResultSet rs= pst.executeQuery();//위 쿼리를 실행하고 결과값을 rs에 담는다.
			
			while(rs.next()) {//쿼리를 조회해 받아온 결과값이 있는 resultset에 데이타가 있는동안 아래 내용이 실행된다. 
				projectDTO dto = new projectDTO();//projectDTO 객체 생성
				//resultset에 있는 값을 dto로 옮긴다.
				dto.setRm(rs.getString("rm"));
				dto.setNo(rs.getString("no"));
				dto.setWriter_id(rs.getString("writer_id"));
				dto.setCate(rs.getString("cate"));
				dto.setStatus(rs.getString("status"));
				dto.setTitle(rs.getString("title"));
				dto.setContext(rs.getString("context"));
				dto.setAmount(rs.getString("amount"));
				dto.setJobdate(rs.getString("jobdate"));
				dto.setRegist_date(rs.getString("regist_date"));
				dto.setModify_date(rs.getString("modify_date"));
				dto.setApply_cnt(rs.getInt("apply_cnt"));
				list.add(dto);//dto를 리스트에 추가해 준다.
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pst!=null) {
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return list; //결과값을 list로 넘긴다.
	}
	
	public List getCoWorkList(String page_no, String cate, String search_val, String user_id) {
		List list = new ArrayList();
		
		int startno = (Integer.parseInt(page_no) + 1);
		int endno = startno + 9;
		try {
			conn = ds.getConnection();
			if (cate.equals("00")) {
				pst = conn.prepareStatement("SELECT RM, T2.* FROM( " +
						"SELECT @RNUM:=@RNUM+1 AS RM, T1.* " +
						"FROM (SELECT * FROM project WHERE CONTEXT LIKE ? AND writer_id = ? AND status = '02' ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R " + 
						")  AS T2 " +
						"WHERE RM >= ? AND RM <= ?;");
				pst.setString(1, "%"+search_val+"%");
				pst.setString(2, user_id);
				pst.setInt(3, startno);
				pst.setInt(4, endno);
			} else {
			pst = conn.prepareStatement("SELECT RM, T2.* FROM( " +
					"SELECT @RNUM:=@RNUM+1 AS RM, T1.* " +
					"FROM (SELECT * FROM project WHERE CONTEXT LIKE ? AND writer_id = ? AND selected_id = ? AND status = '02' ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R " + 
					")  AS T2 " +
					"WHERE RM >= ? AND RM <= ?;");
			pst.setString(1, "%"+search_val+"%");
			pst.setString(2, cate);
			pst.setString(3, user_id);
			pst.setInt(4, startno);
			pst.setInt(5, endno);
			}
			ResultSet rs= pst.executeQuery();
			
			while(rs.next()) {
				projectDTO dto = new projectDTO();
				dto.setRm(rs.getString("rm"));
				dto.setNo(rs.getString("no"));
				dto.setWriter_id(rs.getString("writer_id"));
				dto.setCate(rs.getString("cate"));
				dto.setStatus(rs.getString("status"));
				dto.setTitle(rs.getString("title"));
				dto.setContext(rs.getString("context"));
				dto.setAmount(rs.getString("amount"));
				dto.setJobdate(rs.getString("jobdate"));
				dto.setRegist_date(rs.getString("regist_date"));
				dto.setModify_date(rs.getString("modify_date"));
				dto.setApply_cnt(rs.getInt("apply_cnt"));
				list.add(dto);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(pst!=null) {
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return list;
	}
	
	public List getCoDoneList(String page_no, String cate, String search_val, String user_id) {
List list = new ArrayList();
		
		int startno = (Integer.parseInt(page_no) + 1);//(Integer.parseInt(page_no) - 1)+10+1;
		int endno = startno + 9;//startno + 10 - 1;
		try {
			conn = ds.getConnection();
			if (cate.equals("00")) {
				pst = conn.prepareStatement("SELECT RM, T2.* FROM( " +
						"SELECT @RNUM:=@RNUM+1 AS RM, T1.* " +
						"FROM (SELECT * FROM project WHERE CONTEXT LIKE ? AND writer_id = ? AND status = '03' ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R " + 
						")  AS T2 " +
						"WHERE RM >= ? AND RM <= ?;");
				pst.setString(1, "%"+search_val+"%");
				pst.setString(2, user_id);
				pst.setInt(3, startno);
				pst.setInt(4, endno);
			} else {
			pst = conn.prepareStatement("SELECT RM, T2.* FROM( " +
					"SELECT @RNUM:=@RNUM+1 AS RM, T1.* " +
					"FROM (SELECT * FROM project WHERE CONTEXT LIKE ? AND CATE = ? AND writer_id = ? AND status = '03' ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R " + 
					")  AS T2 " +
					"WHERE RM >= ? AND RM <= ?;");
			pst.setString(1, "%"+search_val+"%");
			pst.setString(2, cate);
			pst.setString(3, user_id);
			pst.setInt(4, startno);
			pst.setInt(5, endno);
			}
			ResultSet rs= pst.executeQuery();
			
			while(rs.next()) {
				projectDTO dto = new projectDTO();
				dto.setRm(rs.getString("rm"));
				dto.setNo(rs.getString("no"));
				dto.setWriter_id(rs.getString("writer_id"));
				dto.setCate(rs.getString("cate"));
				dto.setStatus(rs.getString("status"));
				dto.setTitle(rs.getString("title"));
				dto.setContext(rs.getString("context"));
				dto.setAmount(rs.getString("amount"));
				dto.setJobdate(rs.getString("jobdate"));
				dto.setRegist_date(rs.getString("regist_date"));
				dto.setModify_date(rs.getString("modify_date"));
				dto.setApply_cnt(rs.getInt("apply_cnt"));
				list.add(dto);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(pst!=null) {
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return list;
	}
	
	public String chgPassword(String user_id, String pwd) {
		int intResult = 0;
		String strResult = "N";
		System.out.println("dao.chgpassword");
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement("update member set password = ? where id = ?;");
			pst.setString(1,pwd);
			pst.setString(2,user_id);
			
			intResult = pst.executeUpdate();
		
			if (intResult>0) { //1보다 크므로 성공
				strResult = "Y";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(pst!=null) {
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return strResult;
	}
	
}
