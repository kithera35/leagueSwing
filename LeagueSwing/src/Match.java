
public class Match {
	
	private SportsTeam hostTeam;
	private SportsTeam awayTeam;
	private boolean result;
	private Date date;
	
	
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	public Match(SportsTeam hostTeam, SportsTeam awayTeam, Date date) {
		super();
		this.hostTeam = hostTeam;
		this.awayTeam = awayTeam;
		this.date = date;
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
