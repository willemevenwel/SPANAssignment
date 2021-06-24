package pvt.willemevenwel.span.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import pvt.willemevenwel.span.DBUtil;
import pvt.willemevenwel.span.dao.vo.MatchVO;
import pvt.willemevenwel.span.dao.vo.PointsVO;
import pvt.willemevenwel.span.model.MatchPointsModel;
import pvt.willemevenwel.span.model.MatchResult;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tester is a JUnit5 test class")
class Tester {

    private static String lineOne = "Team1 1, Team2 2";
    private static String lineTwo = "Team1 2, Team3 1";
    private static String lineThree = "Team3 2, Team2 1";
    private static String lineFour = "Team4 0, Team5 0";

    @Test
    @DisplayName("Test - Testing")
    public void test() {

        System.out.println("Testing Tester.");

    }

    @Test
    @DisplayName("Test Boolean - is true")
    public void testBooleanIsTrue() {

        System.out.println("testBooleanIsTrue");
        assertTrue(true);

    }

    @Test
    @DisplayName("Test Boolean - is false")
    public void testBooleanIsfalse() {

        System.out.println("testBooleanIsfalse");
        assertFalse(false);

    }

    @Test
    @DisplayName("Test File Line - Splitter 1")
    public void testFileLineSplitter1() {

        System.out.println("testFileLineSplitter1");

        MatchResult matchResult = new MatchResult(lineOne);

        assertEquals("Team1", matchResult.getTeamOne().getTeamName());
        assertEquals(1, matchResult.getTeamOne().getTeamScore());
        assertEquals("Team2", matchResult.getTeamTwo().getTeamName());
        assertEquals(2, matchResult.getTeamTwo().getTeamScore());
        assertEquals("Team2", matchResult.getWinningTeam());

    }

    @Test
    @DisplayName("Test File Line - Splitter 2")
    public void testFileLineSplitter2() {

        System.out.println("testFileLineSplitter2");

        MatchResult matchResult = new MatchResult(lineTwo);

        assertEquals("Team1", matchResult.getTeamOne().getTeamName());
        assertEquals(2, matchResult.getTeamOne().getTeamScore());
        assertEquals("Team3", matchResult.getTeamTwo().getTeamName());
        assertEquals(1, matchResult.getTeamTwo().getTeamScore());
        assertEquals("Team1", matchResult.getWinningTeam());

    }

    @Test
    @DisplayName("Test File Line - Splitter 3")
    public void testFileLineSplitter3() {

        System.out.println("testFileLineSplitter3");

        MatchResult matchResult = new MatchResult(lineFour);

        assertEquals("Team4", matchResult.getTeamOne().getTeamName());
        assertEquals(0, matchResult.getTeamOne().getTeamScore());
        assertEquals("Team5", matchResult.getTeamTwo().getTeamName());
        assertEquals(0, matchResult.getTeamTwo().getTeamScore());
        assertEquals("Draw", matchResult.getWinningTeam());

    }

    @Test
    @DisplayName("Test DB - created")
    public void testDBCreated() {

        System.out.println("testDBCreated");

        DataSource ds = DBUtil.getDB("test", "test", "test");

        assertNotNull(ds);

        try {
            assertNotNull(ds.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
            assertNotNull(e.getMessage());
        }

    }

    @Test
    @DisplayName("Test DB Model - initiated")
    public void testDBModelInitiated() {

        System.out.println("testDBModelInitiated");

        DataSource ds = DBUtil.getDB("test", "test", "test");

        MatchPointsModel model = new MatchPointsModel(ds, false);

        assertNotNull(model);

        assertNotNull(model.getDataSource());
        assertNotNull(model.getDBI());
        assertNotNull(model.getMatchDAO());

    }

    @Test
    @DisplayName("Test DB Model - Schema Created")
    public void testDBModelSchemaCreated() {

        System.out.println("testDBModelSchemaCreated");

        DataSource ds = DBUtil.getDB("test", "test", "test");

        MatchPointsModel model = new MatchPointsModel(ds, true);

        assertNotNull(model);

        DBI dbi = model.getDBI();

        dbi.useHandle(handle -> {

            assertNotNull(handle);

            handle.execute("create table test_table (test_int integer, test_varchar varchar(50)) ");

            int updateCount = handle.insert(
                    "insert into test_table values "
                            + "(1, 'tutorials')");

            assertEquals(1, updateCount);

            List<Integer> resultsTestInt = handle.createQuery("select test_int from test_table").mapTo(Integer.class).list();

            assertEquals(1, resultsTestInt.size());

            List<String> resultsTestVarChar= handle.createQuery("select test_varchar from test_table").mapTo(String.class).list();

            assertEquals(1, resultsTestVarChar.size());

        });

    }

    @Test
    @DisplayName("Test DB Model - Schema Inserts & Read")
    public void testDBModelInsertsAndReadMatch() {

        System.out.println("testDBModelInsertsAndReadMatch");

        DataSource ds = DBUtil.getDB("test", "test", "test");

        MatchPointsModel model = new MatchPointsModel(ds, true);

        assertNotNull(model);

        model.insertMatch("testDBModelInsertsAndReadMatch", "test1", 1, "test2", 2, "test2");

        List<MatchVO> list = model.retrieveMatches();

        assertEquals("test1", list.get(0).getTeam_one_name());
        assertEquals(1, list.get(0).getTeam_one_score());
        assertEquals("test2", list.get(0).getTeam_two_name());
        assertEquals(2, list.get(0).getTeam_two_score());

    }

    @Test
    @DisplayName("Test DB Model - Schema Inserts & Read")
    public void testDBModelInsertsAndReadPoints() {

        System.out.println("testDBModelInsertsAndReadPoints");

        DataSource ds = DBUtil.getDB("test", "test", "test");

        MatchPointsModel model = new MatchPointsModel(ds, true);

        assertNotNull(model);

        model.insertPoints("testDBModelInsertsAndReadPoints", "test1", "Win", 3);

        List<PointsVO> list = model.retrievePoints();

        assertEquals("test1", list.get(0).getTeam_name());
        assertEquals("Win", list.get(0).getPoints_description());
        assertEquals(3, list.get(0).getPoints_allocated());

    }

}

