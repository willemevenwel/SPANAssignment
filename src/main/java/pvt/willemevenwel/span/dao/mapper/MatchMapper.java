package pvt.willemevenwel.span.dao.mapper;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import pvt.willemevenwel.span.dao.vo.MatchVO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchMapper implements ResultSetMapper<MatchVO> {
    @Override
    public MatchVO map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        MatchVO match = new MatchVO();
        int columnCounter = 0;
        match.setId(r.getInt(++columnCounter));
        match.setTeam_one_name(r.getString(++columnCounter));
        match.setTeam_one_score(r.getInt(++columnCounter));
        match.setTeam_two_name(r.getString(++columnCounter));
        match.setTeam_two_score(r.getInt(++columnCounter));
        return match;
    }
}
