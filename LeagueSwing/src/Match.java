
public class Match {
	
	private SportsTeam hostTeam;
	private SportsTeam awayTeam;
	private boolean result;
	
	
	
	public Match(SportsTeam hostTeam, SportsTeam awayTeam) {
		this.hostTeam = hostTeam;
		this.awayTeam = awayTeam;
	}
	
	public SportsTeam getHostTeam() {
		return hostTeam;
	}
	public void setHostTeam(SportsTeam hostTeam) {
		this.hostTeam = hostTeam;
	}
	public SportsTeam getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(SportsTeam awayTeam) {
		this.awayTeam = awayTeam;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
	
	
	

}
