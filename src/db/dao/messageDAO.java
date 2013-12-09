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
import db.dto.messageDTO;
import db.dto.projectDTO;

/**
 * @author sojaemin
 *
 */
public class messageDAO {
	DataSource ds;
	Connection conn;
	PreparedStatement pst;
	public messageDAO() throws SQLException {
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
		String dupIdYn = "N"; //중복여부 값을 초기화
		PreparedStatement pst = null;//쿼리 실행에 사용할 PreparedStatement값 초기화
		try {
		conn = ds.getConnection();//데이타베이스 커넥션 초기화
		pst = conn.prepareStatement("select count(id) as cnt from member where id = ?;");//쿼리 설정
		pst.setString(1, userId);//쿼리의 ? 에 들어갈 변수 세팅 
		
		ResultSet rs = pst.executeQuery();//쿼리 실행
		
		if (rs.next()) {//쿼리 실행하고 받은 resultset에 값이 존재하는 동안 아래 명령어 실행
			if(rs.getInt("cnt") > 0) {//cnt 칼럼 값이 0보다 크면
				dupIdYn = "Y";//중복여부는 Y로 세팅
			}
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
		return dupIdYn;
	}
	
	public String getMessageCnt(String userId) {
		String result = "0"; //리턴 값으로 사용할 결과값, 즉 읽지않은 메세지 수 
		PreparedStatement pst = null;
		try {
		conn = ds.getConnection();
		pst = conn.prepareStatement("select count(*) as cnt from message where to_id = ? and newYN = 'Y';");//쿼리 세팅
		pst.setString(1, userId);//쿼리의 변수값 세팅
		
		ResultSet rs = pst.executeQuery();//쿼리 실행
		
		if (rs.next()) {
			if(rs.getInt("cnt") > 0) {//읽지않은 메세지가 0보다 크면
				result = Integer.toString(rs.getInt("cnt"));//결과값 변수에 읽지않은 메세지 수량을 할당
			}
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
		return result;
	}
	public String sendMessage(messageDTO dto) {
		int intResult = 0;//결과값 세팅
		String strResult = "N";//결과값 세팅
		
		try {
			conn = ds.getConnection();//데이타베이스 세팅 설정
			pst = conn.prepareStatement("insert into message(from_id, to_id, newYN, context, send_date) values (?,?,'Y',?, now());");//쿼리 세팅
			//쿼리에 들어갈 변수값 세팅
			pst.setString(1,dto.getFrom_id());
			pst.setString(2,dto.getTo_id());
			pst.setString(3,dto.getContext());

			intResult = pst.executeUpdate();//쿼리를 실행
		
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
	
	
	public String readMessage(String to_id) {
		int intResult = 0;//결과값 초기화
		String strResult = "N";//결과값 초기화
		
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement("update message set newYN = 'N', read_date = now() where to_id = ? and newYN = 'Y';");//쿼리 설정
			pst.setString(1,to_id);//쿼리에 사용할 변수값 세팅
			
			intResult = pst.executeUpdate();//쿼리 실행
		
			if (intResult>0) { //1보다 크므로 성공
				strResult = "Y";
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
		return strResult;
	}
	
	
	
	public List getRecMessageList(String page_no, String search_val, String user_id) {
		List list = new ArrayList();//조회 내용을 담고, 리턴값으로 사용할 list 초기회
		
		int startno = (Integer.parseInt(page_no) + 1);//조회 시작 번호 세팅
		int endno = startno + 9;//조회 끝 번호 세팅
		try {
			conn = ds.getConnection();//데이타베이스 커넥션 세팅
			pst = conn.prepareStatement("SELECT RM, T2.* FROM( " +
					"SELECT @RNUM:=@RNUM+1 AS RM, T1.* " +
					"FROM (SELECT * FROM message WHERE CONTEXT LIKE ? AND to_id = ? ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R " + 
					")  AS T2 " +
					"WHERE RM >= ? AND RM <= ?;");//쿼리 설정
			//쿼리에 들어갈 변수값 세팅
			pst.setString(1, "%"+search_val+"%");
			pst.setString(2, user_id);
			pst.setInt(3, startno);
			pst.setInt(4, endno);

			ResultSet rs= pst.executeQuery();//쿼리를 실행하여 resultset에 담는다.
			
			while(rs.next()) {//resultset에 값이 존재하는 동안
				//아래 messageDTO에 resultset 값을 옮겨담는다.
				messageDTO dto = new messageDTO();
				dto.setRm(rs.getString("rm"));
				dto.setNo(rs.getString("no"));
				dto.setFrom_id(rs.getString("from_id"));
				dto.setTo_id(rs.getString("to_id"));
				dto.setNewYN(rs.getString("newYN"));
				dto.setContext(rs.getString("context"));
				dto.setSend_date(rs.getString("send_date"));
				dto.setRead_date(rs.getString("read_date"));
				
				list.add(dto);//messageDTO값을 list에 전달한다.
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
		return list;//리스트 값을 리턴
	}
	
	public List getSendMessageList(String page_no, String search_val, String user_id) {
		List list = new ArrayList();
		
		int startno = (Integer.parseInt(page_no) + 1);//(Integer.parseInt(page_no) - 1)+10+1;
		int endno = startno + 9;//startno + 10 - 1;
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement("SELECT RM, T2.* FROM( " +
					"SELECT @RNUM:=@RNUM+1 AS RM, T1.* " +
					"FROM (SELECT * FROM message WHERE CONTEXT LIKE ? AND from_id = ? ORDER BY NO DESC) AS T1, (SELECT @RNUM:=0) as R " + 
					")  AS T2 " +
					"WHERE RM >= ? AND RM <= ?;");
			pst.setString(1, "%"+search_val+"%");
			pst.setString(2, user_id);
			pst.setInt(3, startno);
			pst.setInt(4, endno);

			ResultSet rs= pst.executeQuery();
			
			while(rs.next()) {
				messageDTO dto = new messageDTO();
				dto.setRm(rs.getString("rm"));
				dto.setNo(rs.getString("no"));
				dto.setFrom_id(rs.getString("from_id"));
				dto.setTo_id(rs.getString("to_id"));
				dto.setNewYN(rs.getString("newYN"));
				dto.setContext(rs.getString("context"));
				dto.setSend_date(rs.getString("send_date"));
				dto.setRead_date(rs.getString("read_date"));
				
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
}
