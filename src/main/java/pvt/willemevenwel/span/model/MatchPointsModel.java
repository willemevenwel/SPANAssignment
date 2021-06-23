package pvt.willemevenwel.span.model;

import org.apache.log4j.Logger;
import org.skife.jdbi.v2.DBI;
import pvt.willemevenwel.span.dao.MatchPointsDAO;
import pvt.willemevenwel.span.dao.vo.MatchVO;
import pvt.willemevenwel.span.dao.vo.PointsVO;

import javax.sql.DataSource;
import java.util.List;

public class MatchPointsModel {

    static Logger logger = Logger.getLogger(MatchPointsModel.class);

    private DataSource mDataSource;
    private DBI mDBI;
    private MatchPointsDAO mMatchPointsDAO;

    public MatchPointsModel(DataSource pDataSource) {

        logger.info("Configuring H2 DB with JDBI...");

        setDataSource(pDataSource);
        setDBI(new DBI(getDataSource()));
        setMatchDAO(getDBI().open(MatchPointsDAO.class));

        logger.info("Creating schema and tables...");

        getMatchDAO().createMatchTable();
        getMatchDAO().createPointsTable();

        logger.info("Done.");

    }

    public DataSource getDataSource() {
        return mDataSource;
    }

    public void setDataSource(DataSource mDataSource) {
        this.mDataSource = mDataSource;
    }

    public DBI getDBI() {
        return mDBI;
    }

    public void setDBI(DBI mDBI) {
        this.mDBI = mDBI;
    }

    public MatchPointsDAO getMatchDAO() {
        return mMatchPointsDAO;
    }

    public void setMatchDAO(MatchPointsDAO mMatchPointsDAO) {
        this.mMatchPointsDAO = mMatchPointsDAO;
    }

    public void insertMatch(String pFileName, String teamName, int teamScore, String teamName1, int teamScore1, String winningTeam) {
        getMatchDAO().insertMatch(pFileName, teamName, teamScore, teamName1, teamScore1, winningTeam);
    }

    public void insertPoints(String pFileName, String teamName, String draw, int i) {
        getMatchDAO().insertPoints(pFileName, teamName, draw, i);
    }

    public List<MatchVO> retrieveMatches() {
        return getMatchDAO().retrieveMatches();
    }

    public List<PointsVO> retrievePoints() {
        return getMatchDAO().retrievePoints();
    }

    public List<PointsVO> retrievePointsTable() {
        return getMatchDAO().retrievePointsTable();
    }

}
