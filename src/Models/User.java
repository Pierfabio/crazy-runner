package Models;

public class User {
	
	private int id;
	private String name, nickname;
	
	public User(String name, String nickname) {
		this.name = name;		
		this.nickname = nickname;
	}
	
	
	
	public User(int id, String name, String nickname) {
		this.id = id;
		this.name = name;
		this.nickname = nickname;
	}



	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getNickname() {
		return this.nickname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

}
