package thoughtwok.projectdb.service;

import java.net.UnknownHostException;

import org.springframework.stereotype.Component;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Component
public class DbService {

    private DB mongoDb;

    public DbService() {
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
