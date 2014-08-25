package thoughtwok.projectdb.service;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class DBService {
    
    private static final DBService INSTANCE = new DBService();
    
    public static DBService getInstance() {
        return INSTANCE;
    }
    
    private DB mongoDb;

    public DBService() {
        if (this.mongoDb == null) {
            try {
                this.mongoDb = new MongoClient(new MongoClientURI("mongodb://localhost/")).getDB("projrepo");
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }
    
    public DBCollection getCollection(String collectionName) {
        return this.mongoDb.getCollection(collectionName);
        
    }
}