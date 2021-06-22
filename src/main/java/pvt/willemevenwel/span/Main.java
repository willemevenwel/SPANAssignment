package pvt.willemevenwel.span;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
import org.h2.jdbcx.JdbcConnectionPool;
import org.skife.jdbi.v2.DBI;

import javax.sql.DataSource;
import java.io.*;
import java.util.List;

public class Main extends DirectoryWatcher {

    static Logger logger = Logger.getLogger(Main.class);

    private DataSource mDataSource;
    private DBI mDBI;
    private MatchDAO mMatchDAO;

    public Main(DataSource pDataSource) {

        super();

        logger.info("Configuring H2 DB with JDBI...");
        setDataSource(pDataSource);
        setDBI(new DBI(getDataSource()));
        setMatchDAO(getDBI().open(MatchDAO.class));

        logger.info("Creating schema and tables...");
        getMatchDAO().createMatchTable();
        getMatchDAO().createPointsTable();

        logger.info("Done.");
    }

    public static void main(String[] args) {

        PropertyConfigurator.configure("log4j.properties");

        logger.info("Starting SPANAssignment...");

        DataSource ds = JdbcConnectionPool.create("jdbc:h2:mem:SPANAssignement", "username", "password");

        DirectoryWatcher watcher = new Main(ds);
        watcher.startWatching("dropfileshere");

    }

    private void workThroughFile(String pFileName) {

        logger.info("workThroughFile - " + pFileName);

        //tried some funky stuff here with apache's CSVLoader and Scanner, but all in all, plain
        //old java works fine and best for this scenario

        BufferedReader fileReader = null;

        try {

            logger.info("Processing match file...");

            fileReader = new BufferedReader(new FileReader(pFileName));

            //Read the file line by line
            String line = "";

            while ((line = fileReader.readLine()) != null) {

                MatchResult matchResult = new MatchResult(line);

                logger.info("Reading line: " + matchResult.getTeamOne().getTeamName() + " - " + matchResult.getTeamOne().getTeamScore() + " | " + matchResult.getTeamOne().getTeamName() + " - " + matchResult.getTeamOne().getTeamScore());

                logger.info("Inserting into in mem db...");

                getMatchDAO().insertMatch(
                        pFileName,
                        matchResult.getTeamOne().getTeamName(),
                        matchResult.getTeamOne().getTeamScore(),
                        matchResult.getTeamTwo().getTeamName(),
                        matchResult.getTeamTwo().getTeamScore(),
                        matchResult.getWinningTeam()
                );

                logger.info("Inserted.");

                logger.info("Processing and capturing result...");

                if (matchResult.getWinningTeam().trim().equalsIgnoreCase("draw")) {

                    getMatchDAO().insertPoints(
                            pFileName,
                            matchResult.getTeamOne().getTeamName(),
                            "Draw",
                            1
                    );

                    getMatchDAO().insertPoints(
                            pFileName,
                            matchResult.getTeamTwo().getTeamName(),
                            "Draw",
                            1
                    );

                } else if (matchResult.getWinningTeam().equalsIgnoreCase(matchResult.getTeamOne().getTeamName())) {

                    getMatchDAO().insertPoints(
                            pFileName,
                            matchResult.getTeamOne().getTeamName(),
                            "Win",
                            3
                    );

                    getMatchDAO().insertPoints(
                            pFileName,
                            matchResult.getTeamTwo().getTeamName(),
                            "Loss",
                            0
                    );


                } else if (matchResult.getWinningTeam().equalsIgnoreCase(matchResult.getTeamTwo().getTeamName())) {

                    getMatchDAO().insertPoints(
                            pFileName,
                            matchResult.getTeamOne().getTeamName(),
                            "Loss",
                            0
                    );

                    getMatchDAO().insertPoints(
                            pFileName,
                            matchResult.getTeamTwo().getTeamName(),
                            "Win",
                            3
                    );

                }

                logger.info("Processed and inserted.");

            }

            logger.info("Match file has been processed.");


            //This code is just to verify that the match data was inserted into the db
//            List<MatchVO> list = getMatchDAO().retrieveMatches();
//
//            for (MatchVO match : list) {
//                logger.info("* " + match.getTeam_one_name() + " - " + match.getTeam_one_score());
//                logger.info("* " + match.getTeam_two_name() + " - " + match.getTeam_two_score());
//            }
//
            List<PointsVO> list = getMatchDAO().retrievePointsTable();
            int counter = 0;
            for (PointsVO pointsVO : list) {
                logger.info(++counter + ". " + pointsVO.getTeam_name() + ", " + pointsVO.getPoints_allocated() + " pts");
            }

        } catch (FileNotFoundException fnfe) {

            logger.error(fnfe);

        } catch (IOException ioe) {

            logger.error(ioe);

        } finally {

            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    logger.error(e);
                }
            }

        }

    }

    @Override
    public void fileHasBeenCreated(String pResult) {
        logger.debug("Override - Entry was created on log dir (" + getPathToListen() + ") - File: " + pResult);
        workThroughFile("dropfileshere/" + pResult);
    }

    @Override
    public void fileHasBeenUpdated(String pResult) {
        logger.debug("Override - Entry was modified on log dir (" + getPathToListen() + ") - File: " + pResult);
    }

    @Override
    public void fileHasBeenDeleted(String pResult) {
        logger.debug("Override - Entry was deleted from log dir (" + getPathToListen() + ") - File: " + pResult);
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

    public MatchDAO getMatchDAO() {
        return mMatchDAO;
    }

    public void setMatchDAO(MatchDAO mMatchDAO) {
        this.mMatchDAO = mMatchDAO;
    }
}
