package cash.modal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cash.vo.Member;

public class MemberDao {
	// 회원정보 수정
	public int updateMember(String memberId, String newPw) {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    int row = 0;
	    
	    String sql = "UPDATE member SET member_pw = PASSWORD(?), updatedate = now() WHERE member_id = ?";
	    String driver = "org.mariadb.jdbc.Driver";
	    String dburl = "jdbc:mariadb://127.0.0.1:3306/cash";
	    String dbuser = "root";
	    String dbpw = "java1234";
	    
	    try {
	         Class.forName(driver);
	         conn = DriverManager.getConnection(dburl,dbuser,dbpw);
	         stmt = conn.prepareStatement(sql);
	         stmt.setString(1,newPw);
	         stmt.setString(2,memberId);
	         row = stmt.executeUpdate();
	      } catch(Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            stmt.close();
	            conn.close();
	         } catch(Exception e2) {
	            e2.printStackTrace();
	         }
	      }
	      return row;
	}
	// 회원탈퇴
	   public int removeMember(Member member, String memberPw) {
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      int row = 0;
	      
	      String sql = "DELETE FROM member WHERE member_id = ? AND member_pw = PASSWORD(?)";
	      String driver = "org.mariadb.jdbc.Driver";
	      String dburl = "jdbc:mariadb://127.0.0.1:3306/cash";
	      String dbuser = "root";
	      String dbpw = "java1234";
	      
	      try {
	         Class.forName(driver);
	         conn = DriverManager.getConnection(dburl,dbuser,dbpw);
	         stmt = conn.prepareStatement(sql);
	         stmt.setString(1,member.getMemberId());
	         stmt.setString(2,memberPw);
	         row = stmt.executeUpdate();
	      } catch(Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            stmt.close();
	            conn.close();
	         } catch(Exception e2) {
	            e2.printStackTrace();
	         }
	      }
	      return row;
	   }
	
// 회원 상세정보
	public Member selectMemberOne(String memberId) {
		Member memberOne = null;
		
		  Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      String sql = "SELECT member_id memberId, member_pw memberPw, updatedate, createdate FROM member WHERE member_id=?";
	      try {
	    	  Class.forName("org.mariadb.jdbc.Driver");
	    	  conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash","root","java1234");
	    	  stmt = conn.prepareStatement(sql);
	    	  stmt.setString(1, memberId); 
	    	  
	         rs = stmt.executeQuery();
	         if(rs.next()) {
	        	 memberOne = new Member();
	        	 memberOne.setMemberId(rs.getString("memberId"));
	        	 memberOne.setUpdatedate(rs.getString("updatedate"));
	        	 memberOne.setCreatedate(rs.getString("createdate"));
	         }      
	      } catch(Exception e1) {
	         e1.printStackTrace();
	      } finally {
	         try {
	            stmt.close();
	            conn.close();
	         } catch(Exception e2) {
	            e2.printStackTrace();
	         }
	      }
		return memberOne;
	}
	
	
//  회원가입 메서드
  public int insertMember(Member member) {
     int row = 0;
     Connection conn = null;
     PreparedStatement stmt = null;
     String sql  = "INSERT INTO member VALUES(?, PASSWORD(?), NOW(), NOW())";
     try {
        String driver = "org.mariadb.jdbc.Driver";
        String dbUrl= "jdbc:mariadb://127.0.0.1:3306/cash";
        String dbUser = "root";
        String dbPw = "java1234";
        Class.forName(driver);
        conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, member.getMemberId());
        stmt.setString(2, member.getMemberPw());
        row = stmt.executeUpdate();
     } catch (Exception e1) {
        e1.printStackTrace();
     } finally {
        try {
     //close()는 역순으로
           stmt.close();
           conn.close();
        } catch(Exception e2) {
           e2.printStackTrace();
        }
     }
     return row;
  }
  
		
	// 로그인 메소드
	   public Member selectMemberById(Member paramMember) {
		      Member returnMember = null;
		      
		      Connection conn = null;
		      PreparedStatement stmt = null;
		      ResultSet rs = null;
		      String sql = "SELECT member_id memberId FROM member WHERE member_id=? AND member_pw = PASSWORD(?)";
		      try {
		    	  Class.forName("org.mariadb.jdbc.Driver");
		    	  conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash","root","java1234");
		    	  stmt = conn.prepareStatement(sql);
		          stmt.setString(1, paramMember.getMemberId());
		          stmt.setString(2, paramMember.getMemberPw());
		         
		         rs = stmt.executeQuery();
		         if(rs.next()) {
		            returnMember = new Member();
		            returnMember.setMemberId(rs.getString("memberId"));
		         }      
		      } catch(Exception e1) {
		         e1.printStackTrace();
		      } finally {
		         try {
		            rs.close();
		            stmt.close();
		            conn.close();
		         } catch(Exception e2) {
		            e2.printStackTrace();
		         }
		      }
		      
		      return returnMember;
		   }
	   
}
