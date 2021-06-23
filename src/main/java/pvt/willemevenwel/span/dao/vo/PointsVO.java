package pvt.willemevenwel.span.dao.vo;

public class PointsVO {

    private int id;
    private String league_name;
    private String team_name;
    private String points_description;
    private int points_allocated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getPoints_description() {
        return points_description;
    }

    public void setPoints_description(String points_description) {
        this.points_description = points_description;
    }

    public int getPoints_allocated() {
        return points_allocated;
    }

    public void setPoints_allocated(int points_allocated) {
        this.points_allocated = points_allocated;
    }

    public String getLeague_name() {
        return league_name;
    }

    public void setLeague_name(String league_name) {
        this.league_name = league_name;
    }
}
