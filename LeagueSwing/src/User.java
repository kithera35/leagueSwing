
public class User {
	
	
	private String username;
	private SportsTeam team;
	
	public User() {}
	public User(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public SportsTeam getTeam() {
		return team;
	}

	public void setTeam(SportsTeam team) {
		this.team = team;
	}
	
	

	
}
