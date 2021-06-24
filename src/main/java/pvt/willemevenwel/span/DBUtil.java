package pvt.willemevenwel.span;

import org.apache.log4j.Logger;
import org.h2.jdbcx.JdbcConnectionPool;

import javax.sql.DataSource;

public class DBUtil {

    private static Logger logger = Logger.getLogger(DBUtil.class);

    public static DataSource getDB(String pDBName, String pUsername, String pPassword) {

        logger.debug("getDB - " + pDBName);

        DataSource ds = JdbcConnectionPool.create("jdbc:h2:mem:" + pDBName, pUsername, pPassword);

        return ds;

    }

}
