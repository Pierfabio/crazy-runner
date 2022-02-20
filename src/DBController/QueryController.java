package DBController;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Models.Score;
import Models.User;

public class QueryController {
	
	private final String DB_READ_SCORE = "CALL read_score";
	private final String DB_INSERT_SCORE = "CALL insert_score";
	private final String DB_UPDATE_SCORE = "CALL update_score";
	private final String DB_INSERT_USER = "CALL insert_user";
	private final String DB_READ_STANDINGS = "CALL get_standings";
	private final String DB_CHECK_USER = "CALL check_user";
	private final String DB_CHECK_NICKNAME = "CALL nickname_available";
	private final String DB_CHECK_SCORE = "CALL check_score";
	
	DBConnection conn = new DBConnection();
	
	public Score readScore(User user){
		
		Score s = new Score();
		
		

		
		ResultSet rs = null;
		CallableStatement stm = null;
		Connection c = conn.getDatabaseConnection();
		
		try {
			stm = c.prepareCall(DB_READ_SCORE+"(?,?,?)");
			stm.setInt(1, user.getId());
			stm.setString(2, user.getName());
			stm.setString(3, user.getNickname());
			rs = stm.executeQuery();
			
			
			
			if(rs!=null){
				
				
				while(rs.next()){
					
					
					
					s.setId(user.getId());
					s.setScore(rs.getInt("score"));
					
					rs.close();
					stm.close();
					c.close();
					
					return s;
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		
		return new Score(user.getId(), 0);
	}
	
	public boolean insertScore(Score s){	
		
		ResultSet rs = null;
		CallableStatement stm = null;
		Connection c = conn.getDatabaseConnection();
		
		
		try {
			stm = c.prepareCall(DB_INSERT_SCORE+"(?,?)");
			stm.setInt(1, s.getId());
			stm.setInt(2, s.getScore());
			rs = stm.executeQuery();
			
			if(rs!=null){
				
				rs.close();
				stm.close();
				c.close();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	
		
	
		return false;
		
	}
	
	public boolean updateScore(Score s){
		

		
		ResultSet rs = null;
		CallableStatement stm = null;
		Connection c = conn.getDatabaseConnection();
		
		if(!checkScore(s)){
			return false;
		}

		try {
			
			
			stm = c.prepareCall(DB_UPDATE_SCORE+"(?,?)");	
			stm.setInt(1, s.getId());
			stm.setInt(2, s.getScore());
			rs = stm.executeQuery();
			
			if(rs!=null){
				
				rs.close();
				stm.close();
				//conn.close();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	
		return false;
		
	}
	
	public Integer insertUser(User user){
		
		
		ResultSet rs = null;
		CallableStatement stm = null;
		Connection c = conn.getDatabaseConnection();
		
		try {
			stm = c.prepareCall(DB_INSERT_USER+"(?,?)");
			stm.setString(1, user.getName());
			stm.setString(2, user.getNickname());
			rs = stm.executeQuery();
			
			if(rs!=null){
				if(rs.next()){
					
					Integer idUser = rs.getInt("NEW USER");
					
					rs.close();
					stm.close();
					c.close();
					
					insertScore(new Score(idUser, new Integer(0)));
					
					return idUser;
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
		
	}
	
	public ArrayList<Score> readStandings(Integer id){
		
		ArrayList<Score> scores = new ArrayList<Score>();
		Score s = new Score();
		
		ResultSet rs = null;
		CallableStatement stm = null;
		Connection c = conn.getDatabaseConnection();
		
		try {
			stm = c.prepareCall(DB_READ_STANDINGS+"()");
			
			rs = stm.executeQuery();
			
			if(rs!=null){
				while(rs.next()){
					
					s.setNickname(rs.getString("nickname"));
					s.setScore(rs.getInt("score"));
					
					if(s!=null){
						
						scores.add(s);
					}
						
				}
				rs.close();
				stm.close();
				c.close();
				return scores;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return new ArrayList<Score>();
	}
	
	
	public Integer checkUser(User user){
		
		ResultSet rs = null;
		CallableStatement stm = null;
		Connection c = conn.getDatabaseConnection();
		
		try {
			stm = c.prepareCall(DB_CHECK_USER+"(?,?)");
			stm.setString(1, user.getName());
			stm.setString(2, user.getNickname());
			rs = stm.executeQuery();
			
			if(rs!=null){
				if(rs.next()){
					return rs.getInt("user");
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
		
	}
	
	public boolean checkNickname(String nickname){
		
		ResultSet rs = null;
		CallableStatement stm = null;
		Connection c = conn.getDatabaseConnection();
		
		try {
			stm = c.prepareCall(DB_CHECK_NICKNAME+"(?)");
			stm.setString(1, nickname);
			rs = stm.executeQuery();
			
			if(rs!=null){
				if(rs.next()){
					return rs.getBoolean("availability");
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return false;
	}
	
	
	public boolean checkScore(Score s){
		
		ResultSet rs = null;
		CallableStatement stm = null;
		Connection c = conn.getDatabaseConnection();
		
		try {
			stm = c.prepareCall(DB_CHECK_SCORE+"(?,?)");
			stm.setInt(1, s.getId());
			stm.setInt(2, s.getScore());
			rs = stm.executeQuery();
			
			if(rs!=null){
				if(rs.next()){
					return rs.getBoolean("isMaxScore");
				}
				
			}  
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return false;
		
		
	}
	
	
	
	
	
	
	

}
