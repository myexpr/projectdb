package thoughtwok.projectdb.service;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
@Profile("test")
public class TestConfiguration implements AppConfiguration {

    @Override
    public DB getMongoDB() {
        DB database = null;

        try {
            database =
                    new MongoClient(new MongoClientURI("mongodb://d_mongo" //+ System.getenv("D_MONGO_PORT_27017_TCP_ADDR")
                            + "/")).getDB("projrepo");
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return database;
    }

}
