package cash.modal;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cash.vo.Cashbook;

public class CashbookDao {
	// 반환값 : cashbook_no 키값
	public int insertCashbook(Cashbook cashbook) {
	      int cashbookNo = 0;
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null; // 입력후 생성된 키값 반환
	      try {
	         String driver = "org.mariadb.jdbc.Driver";
	         String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
	         String dbUser = "root";
	         String dbPw = "java1234";
	         Class.forName(driver);
	         conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
	         String sql = "INSERT INTO"
	               + " cashbook(member_id, category, cashbook_date, price, memo, updatedate, createdate)"
	               + " VALUES(?,?,?,?,?,NOW(),NOW())";
	         
	         stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	         stmt.setString(1, cashbook.getMemberId());
	         stmt.setString(2, cashbook.getCategory());
	         stmt.setString(3, cashbook.getCashbookDate());
	         stmt.setInt(4, cashbook.getPrice());
	         stmt.setString(5, cashbook.getMemo());
	         int row = stmt.executeUpdate();
	         rs = stmt.getGeneratedKeys();
	         
	         if(rs.next()) {
	            cashbookNo = rs.getInt(1);
	         }
	      } catch(Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            rs.close();
	            stmt.close();
	            conn.close();
	         }catch(Exception e2) {
	            e2.printStackTrace();
	         }
	      }
	      return cashbookNo;
	   }
	
	public List<Cashbook> selectCashbookListByTag(String memberId, String word, int beginRow, int rowPerPage){
		List<Cashbook> list = new ArrayList<Cashbook>();
		
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
		String sql = "SELECT c.cashbook_no cashbookNo, c.category category, c.price price, c.memo memo  "
				+ "FROM cashbook c INNER JOIN hashtag h "
				+ "ON c.cashbook_no = h.cashbook_no "
				+ "WHERE c.member_id = ? "
				+ "AND h.word = ? "
				+ "ORDER BY c.cashbook_no ASC "
				+ "LIMIT ?,?";
		try {
	         String driver = "org.mariadb.jdbc.Driver";
	         String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
	         String dbUser = "root";
	         String dbPw = "java1234";
	         Class.forName(driver);
	         conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
	         stmt = conn.prepareStatement(sql);
	         stmt.setString(1, memberId);
	         stmt.setString(2, word);
	         stmt.setInt(3, beginRow);
	         stmt.setInt(4, rowPerPage);
	         rs = stmt.executeQuery();
	         while(rs.next()) {
	            Cashbook c = new Cashbook();
	            c.setCashbookNo(rs.getInt("cashbookNo"));
	            c.setCategory(rs.getString("category"));
	            c.setPrice(rs.getInt("price"));
	            c.setMemo(rs.getString("memo"));
	            list.add(c);
	         }
	      } catch(Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            rs.close();
	            stmt.close();
	            conn.close();
	         } catch(Exception e2) {
	            e2.printStackTrace();
	         }
	      }
		return list;
	}
	
   public List<Cashbook> selectCashbookListByMonth(String memberId, int tagetYear, int tagetMonth) {
      
      List<Cashbook> list = new ArrayList<Cashbook>();
      
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      String sql = "SELECT cashbook_no cashbookNo, category, price, cashbook_date cashbookDate"
               + " FROM cashbook"
               + " WHERE member_id=? AND YEAR(cashbook_date)=? AND MONTH(cashbook_date)=?"
               + " ORDER BY cashbook_date ASC";
      try {
         String driver = "org.mariadb.jdbc.Driver";
         String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
         String dbUser = "root";
         String dbPw = "java1234";
         Class.forName(driver);
         conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
         stmt = conn.prepareStatement(sql);
         stmt.setString(1, memberId);
         stmt.setInt(2, tagetYear);
         stmt.setInt(3, tagetMonth);
         rs = stmt.executeQuery();
         while(rs.next()) {
            Cashbook c = new Cashbook();
            c.setCashbookNo(rs.getInt("cashbookNo"));
            c.setCategory(rs.getString("category"));
            c.setPrice(rs.getInt("price"));
            c.setCashbookDate(rs.getString("cashbookDate"));
            list.add(c);
         }
      } catch(Exception e) {
         e.printStackTrace();
      } finally {
         try {
            rs.close();
            stmt.close();
            conn.close();
         } catch(Exception e2) {
            e2.printStackTrace();
         }
      }
      
      return list;
   }
   public List<Cashbook> selectCashbookOneByMonth(String memberId, int tagetYear, int tagetMonth, int tagetDay) {
	   
	   List<Cashbook> list = new ArrayList<Cashbook>();
	   
	   Connection conn = null;
	   PreparedStatement stmt = null;
	   ResultSet rs = null;
	   String sql = "SELECT cashbook_no cashbookNo, category, price, cashbook_date cashbookDate, memo, updatedate\r\n"
	   		+ "FROM cashbook\r\n"
	   		+ "WHERE member_id = ? AND\r\n"
	   		+ "year(cashbook_date) = ? AND MONTH(cashbook_date) = ? AND DAY(cashbook_date) = ?\r\n"
	   		+ "ORDER BY cashbook_date ASC";
	   try {
		   String driver = "org.mariadb.jdbc.Driver";
		   String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
		   String dbUser = "root";
		   String dbPw = "java1234";
		   Class.forName(driver);
		   conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
		   stmt = conn.prepareStatement(sql);
		   stmt.setString(1, memberId);
		   stmt.setInt(2, tagetYear);
		   stmt.setInt(3, tagetMonth);
		   stmt.setInt(4, tagetDay);
		   rs = stmt.executeQuery();
		   while(rs.next()) {
			   Cashbook c = new Cashbook();
			   c.setCashbookNo(rs.getInt("cashbookNo"));
			   c.setCategory(rs.getString("category"));
			   c.setPrice(rs.getInt("price"));
			   c.setCashbookDate(rs.getString("cashbookDate"));
			   c.setMemo(rs.getString("memo"));
			   c.setUpdatedate(rs.getString("updatedate"));
			   list.add(c);
		   }
	   } catch(Exception e) {
		   e.printStackTrace();
	   } finally {
		   try {
			   rs.close();
			   stmt.close();
			   conn.close();
		   } catch(Exception e2) {
			   e2.printStackTrace();
		   }
	   }
	   
	   return list;
   }
   
   public int cashbookCnt() {
		int row = 0;
		
		Connection conn = null;
		   PreparedStatement stmt = null;
		   ResultSet rs = null;
		   String sql = "SELECT COUNT(*) FROM hashtag";
		
		   try {
			   String driver = "org.mariadb.jdbc.Driver";
			   String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
			   String dbUser = "root";
			   String dbPw = "java1234";
			   Class.forName(driver);
			   conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			   stmt = conn.prepareStatement(sql);
			   rs = stmt.executeQuery();
		   } catch(Exception e) {
			   e.printStackTrace();
		   } finally {
			   try {
				   rs.close();
				   stmt.close();
				   conn.close();
			   } catch(Exception e2) {
				   e2.printStackTrace();
			   }
		   }
		
		return row;
	}
   
}
