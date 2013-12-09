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

import db.dto.projectDTO;

/**
 * @author sojaemin
 *
 */
public class projectDAO {
	DataSource ds;
	Connection conn;
	PreparedStatement pst;
	public projectDAO() throws SQLException {
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
					System.out.println("conn closed!!");
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return dupIdYn;
	}
	
	public String insertNew(projectDTO dto) {
		int intResult = 0;
		String strResult = "N";
		
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement("insert into project(writer_id, cate, status, title, context, amount, jobdate, regist_date) values (?,?,?,?,?,?,?,now());");
			pst.setString(1,dto.getWriter_id());
			pst.setString(2,dto.getCate());
			pst.setString(3,"01");
			pst.setString(4,dto.getTitle());
			pst.setString(5,dto.getContext());
			pst.setString(6,dto.getAmount());
			pst.setString(7,dto.getJobdate());
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
					System.out.println("conn closed!!");
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return strResult;
	}
	
	public String modifyProject(projectDTO dto) {
		int intResult = 0;
		String strResult = "N";
		
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement("update project set cate = ? , status = ?, amount = ?, jobdate = ?, title = ?, context= ?, modify_date= now() where no = ?;");
			pst.setString(1,dto.getCate());
			pst.setString(2,dto.getStatus());
			pst.setString(3,dto.getAmount());
			pst.setString(4,dto.getJobdate());
			pst.setString(5,dto.getTitle());
			pst.setString(6,dto.getContext());
			pst.setString(7,dto.getNo());
			
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
					System.out.println("conn closed!!");
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return strResult;
	}
	
	
	public List getProjectList(String page_no, String cate, String search_val) {
		List list = new ArrayList();
		
		int startno = (Integer.parseInt(page_no) + 1);//(Integer.parseInt(page_no) - 1)+10+1;
		int endno = startno + 19;//startno + 10 - 1;
		try {
			conn = ds.getConnection();
			if (cate.equals("00")) {
				pst = conn.prepareStatement("SELECT RM, T2.* FROM( " +
						"SELECT @RNUM:=@RNUM+1 AS RM, T1.* " +
						"FROM (SELECT * FROM project WHERE CONTEXT LIKE ? ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R " + 
						")  AS T2 " +
						"WHERE RM >= ? AND RM <= ?;");
				pst.setString(1, "%"+search_val+"%");
				pst.setInt(2, startno);
				pst.setInt(3, endno);
			} else {
			pst = conn.prepareStatement("SELECT RM, T2.* FROM( " +
					"SELECT @RNUM:=@RNUM+1 AS RM, T1.* " +
					"FROM (SELECT * FROM project WHERE CONTEXT LIKE ? AND CATE = ? ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R " + 
					")  AS T2 " +
					"WHERE RM >= ? AND RM <= ?;");
			pst.setString(1, "%"+search_val+"%");
			pst.setString(2, cate);
			pst.setInt(3, startno);
			pst.setInt(4, endno);
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
					System.out.println("conn closed!!");
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return list;
	}
	
	public List getListDetail(int id) {
		List list = new ArrayList();
		
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement("select * from project where no = ?;");
			pst.setInt(1, id);
			ResultSet rs= pst.executeQuery();
			
			while(rs.next()) {
				projectDTO dto = new projectDTO();
				
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
				dto.setSelected_id(rs.getString("selected_id"));
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
					System.out.println("conn closed!!");
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return list;
	}
	
	public String getWriterId(String id) {
		String strResult = "N";
		List list = new ArrayList();
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement("select writer_id from project where no = ?;");
			pst.setString(1,id);
			ResultSet rs= pst.executeQuery();
		
			while(rs.next()) {
				strResult = rs.getString("writer_id");
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
					System.out.println("conn closed!!");
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return strResult;
	}
}
