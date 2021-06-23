package pvt.willemevenwel.span.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import pvt.willemevenwel.span.dao.mapper.MatchMapper;
import pvt.willemevenwel.span.dao.mapper.PointsMapper;
import pvt.willemevenwel.span.dao.mapper.PointsTotalMapper;
import pvt.willemevenwel.span.dao.vo.MatchVO;
import pvt.willemevenwel.span.dao.vo.PointsVO;

import java.util.List;

public interface MatchPointsDAO {

    @SqlUpdate("create table match (id int auto_increment primary key, league_name varchar(100), team_one_name varchar(100), team_one_score int, team_two_name varchar(100), team_two_score int, winning_team varchar(100)); ")
    void createMatchTable();

    @SqlUpdate("create table points (id int auto_increment primary key, league_name varchar(100), team_name varchar(100), points_description varchar(100), points_allocated int); ")
    void createPointsTable();

    @SqlUpdate("insert into match (league_name, team_one_name, team_one_score, team_two_name, team_two_score, winning_team) values (:league_name, :team_one_name, :team_one_score, :team_two_name, :team_two_score, :winning_team);")
    void insertMatch(@Bind("league_name") String pLeague, @Bind("team_one_name") String pTeamOneName, @Bind("team_one_score") int pTeamOneScore, @Bind("team_two_name") String pTeamTwoName, @Bind("team_two_score") int pTeamTwoScore, @Bind("winning_team") String pWinningTeam);

    @SqlUpdate("insert into points (league_name, team_name, points_description, points_allocated) values (:league_name, :team_name, :points_description, :points_allocated);")
    void insertPoints(@Bind("league_name") String pLeague, @Bind("team_name") String pTeamName, @Bind("points_description") String pPointsDescription, @Bind("points_allocated") int pPointsAllocated);

    @SqlQuery("select id, team_one_name, team_one_score, team_two_name, team_two_score from match ")
    @RegisterMapper(MatchMapper.class)
    List<MatchVO> retrieveMatches();

    @SqlQuery("select id, league_name, team_name, points_description, points_allocated from points ")
    @RegisterMapper(PointsMapper.class)
    List<PointsVO> retrievePoints();

    @SqlQuery("select league_name, team_name, sum(points_allocated) total_points from points group by league_name, team_name order by league_name, total_points DESC")
    @RegisterMapper(PointsTotalMapper.class)
    List<PointsVO> retrievePointsTable();

}
