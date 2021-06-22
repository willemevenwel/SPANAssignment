package pvt.willemevenwel.span;

import java.util.Arrays;

public class TeamScore {

    private String mTeamName;
    private int mTeamScore;

    public TeamScore(String pTeamScore) {

        String teamSplit[] = pTeamScore.trim().split(" ");
        setTeamName(String.join(" ", Arrays.copyOf(teamSplit, teamSplit.length-1)));
        setTeamScore(Integer.valueOf(teamSplit[teamSplit.length-1]));

    }

    public String getTeamName() {
        return mTeamName;
    }

    public void setTeamName(String mTeamName) {
        this.mTeamName = mTeamName;
    }

    public int getTeamScore() {
        return mTeamScore;
    }

    public void setTeamScore(int mTeamScore) {
        this.mTeamScore = mTeamScore;
    }

}
