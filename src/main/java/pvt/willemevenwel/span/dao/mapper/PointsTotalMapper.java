package pvt.willemevenwel.span.dao.mapper;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import pvt.willemevenwel.span.dao.vo.PointsVO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PointsTotalMapper implements ResultSetMapper<PointsVO> {
    @Override
    public PointsVO map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        PointsVO match = new PointsVO();
        match.setLeague_name("league_name");
        match.setTeam_name(r.getString("team_name"));
        match.setPoints_allocated(r.getInt("total_points"));
        return match;
    }
}
