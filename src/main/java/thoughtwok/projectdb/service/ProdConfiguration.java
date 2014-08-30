package thoughtwok.projectdb.service;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mongodb.DB;
import com.mongodb.Mongo;

@Configuration
@Profile("default")
public class ProdConfiguration implements AppConfiguration {

    @Override
    public DB getMongoDB() {
        
        String host = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
        int port = Integer.parseInt(System.getenv("OPENSHIFT_MONGODB_DB_PORT"));
        String db = System.getenv("OPENSHIFT_APP_NAME");
        String user = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
        String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");

        Mongo mongo = null;
        try {
            mongo = new Mongo(host, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        DB mongoDb = mongo.getDB(db);
        
        mongoDb.authenticate(user, password.toCharArray());
        
        return mongoDb;
    }

}
