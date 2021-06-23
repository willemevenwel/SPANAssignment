package pvt.willemevenwel.span.dao.vo;

public class MatchVO {

    private int id;
    private String team_one_name;
    private int team_one_score;
    private String team_two_name;
    private int team_two_score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeam_one_name() {
        return team_one_name;
    }

    public void setTeam_one_name(String team_one_name) {
        this.team_one_name = team_one_name;
    }

    public int getTeam_one_score() {
        return team_one_score;
    }

    public void setTeam_one_score(int team_one_score) {
        this.team_one_score = team_one_score;
    }

    public String getTeam_two_name() {
        return team_two_name;
    }

    public void setTeam_two_name(String team_two_name) {
        this.team_two_name = team_two_name;
    }

    public int getTeam_two_score() {
        return team_two_score;
    }

    public void setTeam_two_score(int team_two_score) {
        this.team_two_score = team_two_score;
    }
}
