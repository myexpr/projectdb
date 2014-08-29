package thoughtwok.projectdb.service;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
@Profile("dev")
public class DevConfiguration implements AppConfiguration {


    @Override
    public DB getMongoDB() {
        DB database = null;
        try {
            database = new MongoClient(new MongoClientURI("mongodb://localhost/")).getDB("dev");
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return database;
    }

}
