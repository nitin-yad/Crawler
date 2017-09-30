package mp.crawler.mongo;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

import java.io.Serializable;

/**
 * Created by nitin.yadav on 11-10-2016.
 */
public class MongoDB {

    MongoClient mongoClient  = MongoDbConnector.getConnection();

    public Boolean writeData(String dbName, String collectionName, Serializable object){
        Boolean result = false;
        MongoCollection collection = mongoClient.getDatabase(dbName).getCollection(collectionName);
        collection.insertOne(object);
        return true;
    }

    public String readData(String dbName, String collectionName, String id){
        String str =null;
        return str;
    }


}
