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

import db.dto.applyDTO;

/**
 * @author sojaemin
 *
 */
public class applyDAO {
	DataSource ds;
	Connection conn;
	PreparedStatement pst;
	public applyDAO() throws SQLException {
		Context init;
		try {
			init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/jspbook");
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	public String insertNew(applyDTO dto) {
		int intResult = 0;
		String strResult = "N";
		
		int ttl_apply = 0;
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement("insert into apply(user_id, apply_no, context, regist_date) values (?,?,?,now());");
			pst.setString(1,dto.getUser_id());
			pst.setString(2,dto.getApply_no());
			pst.setString(3,dto.getContext());
			intResult = pst.executeUpdate();
			
			ttl_apply = countApply(dto.getApply_no());
			setApplyCount(ttl_apply,dto.getApply_no());
			
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
	
	public String delApply(String apply_no, String no) {
		int intResult = 0;
		String strResult = "N";
		int ttl_apply = 0;
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement("delete from apply where no = ?;");
			pst.setString(1,apply_no);
			intResult = pst.executeUpdate();
		
			ttl_apply = countApply(no);
			setApplyCount(ttl_apply, no);
			
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
	
	public String updateApply(String apply_no, String context) {
		int intResult = 0;
		String strResult = "N";
		
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement("update apply set context = ? where no = ?;");
			pst.setString(1,context);
			pst.setString(2,apply_no);
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
	
	public String getApplyUserId(String apply_no) {
		String strResult = "N";
		List list = new ArrayList();
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement("select user_id from apply where no = ?;");
			pst.setString(1,apply_no);
			ResultSet rs= pst.executeQuery();
		
			while(rs.next()) {
				strResult = rs.getString("user_id");
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
	
	

	public List getApplyList(String project_no) {
		List list = new ArrayList();
		
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement("SELECT * FROM apply " +
					"WHERE apply_no = ?;");
			pst.setString(1, project_no);
			
			
			ResultSet rs= pst.executeQuery();
			
			while(rs.next()) {
				applyDTO dto = new applyDTO();
				dto.setNo(rs.getString("no"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setApply_no(rs.getString("apply_no"));
				dto.setContext(rs.getString("context"));
				dto.setRegist_date(rs.getString("regist_date"));
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
	
	public String selectFreelancer(String seluserId, String apply_no) {
		int intResult = 0;
		String strResult = "N";
		
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement("update project set selected_id = ?, status = '02' where no = ?;");
			pst.setString(1,seluserId);
			pst.setString(2,apply_no);
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
	
	public String setStatus(String status, String apply_no) {
		int intResult = 0;
		String strResult = "N";
		
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement("update project set status = ? where no = ?;");
			pst.setString(1,status);
			pst.setString(2,apply_no);
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

	public String setApplyCount(int cnt, String apply_no) {
		int intResult = 0;
		String strResult = "N";
		
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement("update project set apply_cnt = ? where no = ?;");
			pst.setInt(1,cnt);
			pst.setString(2,apply_no);
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
	

	public int countApply(String apply_no) {
		int result = 0;

		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement("select count(*) AS cnt from apply where apply_no = ?;");
			pst.setString(1,apply_no);
			ResultSet rs= pst.executeQuery();
		
			while(rs.next()) {
				result = rs.getInt("cnt");
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
		return result;
	}
}
