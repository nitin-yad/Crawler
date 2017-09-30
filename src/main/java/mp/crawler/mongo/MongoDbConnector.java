/**
 *@author nitin.yadav
 */

package mp.crawler.mongo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoDbConnector {
    private static MongoClient connection;
    private static MongoDbConnector instance;
    private static final String MONGO_PROP_FILE = "/opt/conf/mongoConfig.properties";
    private static final Logger logger = LoggerFactory.getLogger(MongoDbConnector.class);

    private MongoDbConnector() {
        Properties properties = new Properties();
        InputStream stream = null;
        try {
            stream = new FileInputStream(MONGO_PROP_FILE);
            properties.load(stream);
            String hostName = properties.getProperty("mongo.server", "localhost");
            String port = properties.getProperty("mongo.port", "27017");
            connection = new MongoClient(new MongoClientURI("mongodb://" + hostName + ":" + port
                    + "/?connectTimeoutMS=300000"));
        } catch (FileNotFoundException e) {
            logger.error("mongoConfig file not found!", e);
        } catch (IOException e) {
            logger.error("Unable to read the config file!", e);
        }
    }

    public static MongoDbConnector getInstance() {
        synchronized (MongoDbConnector.class) {
            if (instance == null) {
                instance = new MongoDbConnector();
            }
        }
        return instance;
    }

    public static MongoClient getConnection(){
        MongoDbConnector.getInstance();
        return MongoDbConnector.connection;
    }

    public static void main(String[] args) {
        MongoDbConnector.getInstance();
        MongoDbConnector.connection.getDatabase("match_data").getCollection("match_details");
        logger.info("mongo connection successfull!");
    }
}
