package pvt.willemevenwel.span;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
import org.h2.jdbcx.JdbcConnectionPool;
import pvt.willemevenwel.span.dao.vo.MatchVO;
import pvt.willemevenwel.span.dao.vo.PointsVO;
import pvt.willemevenwel.span.model.MatchPointsModel;
import pvt.willemevenwel.span.model.MatchResult;

import javax.sql.DataSource;
import java.io.*;
import java.util.List;

public class Main extends DirectoryWatcher {

    static Logger logger = Logger.getLogger(Main.class);

    private MatchPointsModel mModel;

    public Main() {

        super();

        DataSource ds = JdbcConnectionPool.create("jdbc:h2:mem:SPANAssignement", "username", "password");

        setModel(new MatchPointsModel(ds));

    }

    public static void main(String[] args) {

        PropertyConfigurator.configure("log4j.properties");

        logger.info("Starting SPANAssignment...");

        DirectoryWatcher watcher = new Main();
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

                this.mModel.insertMatch(
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

                    this.mModel.insertPoints(
                            pFileName,
                            matchResult.getTeamOne().getTeamName(),
                            "Draw",
                            1
                    );

                    this.mModel.insertPoints(
                            pFileName,
                            matchResult.getTeamTwo().getTeamName(),
                            "Draw",
                            1
                    );

                } else if (matchResult.getWinningTeam().equalsIgnoreCase(matchResult.getTeamOne().getTeamName())) {

                    this.mModel.insertPoints(
                            pFileName,
                            matchResult.getTeamOne().getTeamName(),
                            "Win",
                            3
                    );

                    this.mModel.insertPoints(
                            pFileName,
                            matchResult.getTeamTwo().getTeamName(),
                            "Loss",
                            0
                    );


                } else if (matchResult.getWinningTeam().equalsIgnoreCase(matchResult.getTeamTwo().getTeamName())) {

                    this.mModel.insertPoints(
                            pFileName,
                            matchResult.getTeamOne().getTeamName(),
                            "Loss",
                            0
                    );

                    this.mModel.insertPoints(
                            pFileName,
                            matchResult.getTeamTwo().getTeamName(),
                            "Win",
                            3
                    );

                }

                logger.info("Processed and inserted.");

            }

            logger.info("Match file has been processed.");


            List<PointsVO> list = this.mModel.retrievePointsTable();
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

    public void setModel(MatchPointsModel mModel) {
        this.mModel = mModel;
    }

}
