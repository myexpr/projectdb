package thoughtwok.projectdb.dao;

import thoughtwok.projectdb.entity.Tag;
import thoughtwok.projectdb.entity.TagStatistics;
import thoughtwok.projectdb.service.DBService;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class TagDao {

    public TagStatistics getTagStatistics() {
        DBCollection collection = DBService.getInstance().getCollection("projectdata");

        // aggregation pipeline
        DBObject match = new BasicDBObject("$match", new BasicDBObject("LATEST", Boolean.TRUE));
        DBObject unwind = new BasicDBObject("$unwind", "$TAG_DATA");
        
        DBObject groupFields = new BasicDBObject("_id", "$TAG_DATA.TAG");
        groupFields.put("tagCount", new BasicDBObject("$sum", new Integer(1)));
        DBObject group = new BasicDBObject("$group", groupFields);
        
        AggregationOutput aggregate = collection.aggregate(match, unwind, group);

        // iterate over results and populate tag statistics
        TagStatistics tagStatistics = new TagStatistics();
        for (DBObject o : aggregate.results()) {
            tagStatistics.add((String) o.get("_id"), (Integer) o.get("tagCount"));
        }

        return tagStatistics;
    }

}
