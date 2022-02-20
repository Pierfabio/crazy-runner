package Models;

public class Score {
	
	private Integer id;
	private String nickname;
	private Integer score;
	
	public Score(){
		
	}
	
	public Score(Integer id, Integer score){
		this.id=id;
		this.score=score;
	}
	
	public Score(String nickname, Integer score){
		this.nickname=nickname;
		this.score=score;
	}
	
	public void setId(Integer id){
		this.id=id;	
	}
	
	public Integer getId(){	
		return this.id;
	}
	
	public void setNickname(String nickname){
		this.nickname=nickname;	
	}
	
	public String setNickname(){	
		return this.nickname;
	}
	
	
	public void setScore(Integer score){
		this.score=score;
	}
	
	public Integer getScore(){
		return this.score;
	}
	

}
