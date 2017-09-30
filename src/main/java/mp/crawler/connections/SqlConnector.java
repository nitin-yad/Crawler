/**
 *@author nitin.yadav
 */

package mp.crawler.connections;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlConnector {
    private static Connection connection;
    private static SqlConnector instance;
    private static final String SQL_PROP_FILE = "/opt/conf/sqlConfig.properties";
    private static final Logger logger = LoggerFactory.getLogger(SqlConnector.class);

    private SqlConnector() {
        Properties properties = new Properties();
        InputStream stream = null;
        try {
            stream = new FileInputStream(SQL_PROP_FILE);
            properties.load(stream);
            String hostName = properties.getProperty("mysql.server", "localhost");
            String port = properties.getProperty("mysql.port", "3306");
            String userName = properties.getProperty("mysql.userName", "root");
            String password = properties.getProperty("mysql.password");
            String dbName = properties.getProperty("mysql.dbName", "preprocessing");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + hostName + ":" + port + "/" + dbName, userName,
                    password);
        } catch (SQLException e) {
            logger.error("Unable to connect to db!", e);
        } catch (ClassNotFoundException e) {
            logger.error("Unable to connect to db!", e);
        } catch (FileNotFoundException e) {
            logger.error("sqlConfig file not found!", e);
        } catch (IOException e) {
            logger.error("Unable to read the config file!", e);
        }
    }

    public static SqlConnector getInstance() {
        synchronized (SqlConnector.class) {
            if (instance == null) {
                instance = new SqlConnector();
            }
        }
        return instance;
    }

    public static void main(String[] args) throws SQLException {
        SqlConnector.getInstance();
        SqlConnector.connection.close();
        logger.info("mysql connection successfull!");
    }
}
