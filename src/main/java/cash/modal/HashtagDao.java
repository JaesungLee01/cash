package cash.modal;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cash.vo.Hashtag;

public class HashtagDao {
	public List<Map<String,Object>> selectWordCountByMonth(String memberId, int targetYear, int targetMonth){
		List<Map<String,Object>> list = new ArrayList<>();
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String sql = "SELECT word, COUNT(*) cnt\r\n"
	    		+ "FROM hashtag h INNER JOIN cashbook c\r\n"
	    		+ "ON h.cashbook_no = c.cashbook_no\r\n"
	    		+ "WHERE c.member_id=? AND\r\n"
	    		+ "YEAR(c.cashbook_date) = ? AND MONTH(c.cashbook_date) = ?\r\n"
	    		+ "GROUP BY word\r\n"
	    		+ "ORDER BY COUNT(*) DESC";
	    
	    try {
			String driver = "org.mariadb.jdbc.Driver";
	        String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
	        String dbUser = "root";
	        String dbPw = "java1234";
	        Class.forName(driver);
	        conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, memberId);
	        stmt.setInt(2, targetYear);
	        stmt.setInt(3, targetMonth);
	        System.out.println(stmt);
	        rs = stmt.executeQuery();
	        while(rs.next()) {
	            Map<String, Object> m = new HashMap<String, Object>();
	            m.put("word", rs.getString("word"));
	            m.put("cnt", rs.getString("cnt"));
	            list.add(m);
	         }

	        
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				rs.close();
	            stmt.close();
	            conn.close();
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	
	public int insertHashtag(Hashtag hashtag) {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    int row = 0;
	    String sql = "INSERT INTO hashtag(cashbook_no, word, updatedate, createdate)"
	    		+ "VALUES(?,?,NOW(),NOW())";
		
		try {
			String driver = "org.mariadb.jdbc.Driver";
	        String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
	        String dbUser = "root";
	        String dbPw = "java1234";
	        Class.forName(driver);
	        conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
	        stmt = conn.prepareStatement(sql);
	        
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
	            stmt.close();
	            conn.close();
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
		return row;
	}
}
