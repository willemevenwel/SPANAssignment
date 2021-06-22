package pvt.willemevenwel.span;

public class MatchResult {

    private TeamScore mTeamOne;
    private TeamScore mTeamTwo;
    private String mWinningTeam;

    public MatchResult(String pMatchScore) {

        String matchSplit[] = pMatchScore.trim().split(",");
        setTeamOne(new TeamScore(matchSplit[0]));
        setTeamTwo(new TeamScore(matchSplit[1]));

        if (getTeamOne().getTeamScore() > getTeamTwo().getTeamScore()) {
            setWinningTeam(getTeamOne().getTeamName());
        } else if (getTeamTwo().getTeamScore() > getTeamOne().getTeamScore()) {
            setWinningTeam(getTeamTwo().getTeamName());
        } else {
            setWinningTeam("Draw");
        }

    }

    public TeamScore getTeamOne() {
        return mTeamOne;
    }

    public void setTeamOne(TeamScore mTeamOne) {
        this.mTeamOne = mTeamOne;
    }

    public TeamScore getTeamTwo() {
        return mTeamTwo;
    }

    public void setTeamTwo(TeamScore mTeamTwo) {
        this.mTeamTwo = mTeamTwo;
    }

    public String getWinningTeam() {
        return mWinningTeam;
    }

    public void setWinningTeam(String mWinningTeam) {
        this.mWinningTeam = mWinningTeam;
    }
}
