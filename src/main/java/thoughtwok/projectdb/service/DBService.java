package thoughtwok.projectdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.DBCollection;

@Component
public class DbService {

    @Autowired
    AppConfiguration appConfiguration;

    public DBCollection getCollection(String collectionName) {
        return appConfiguration.getMongoDB().getCollection(collectionName);
    }
}
