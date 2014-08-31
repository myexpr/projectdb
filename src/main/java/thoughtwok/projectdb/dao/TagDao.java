package thoughtwok.projectdb.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import thoughtwok.projectdb.entity.TagStatistics;
import thoughtwok.projectdb.service.DbService;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@Component
public class TagDao {

    private static Logger LOGGER = LoggerFactory.getLogger(TagDao.class);

    private static final String ERROR_ONE_TAG_REQUIRED = "atleast one tag required";
    @Autowired
    DbService dbService;

    public TagStatistics getTagStatistics() {
        return this.getTagStatisticsFor(null);
    }

    public TagStatistics getTagStatisticsFor(String[] tags) {

        // aggregation pipeline
        DBObject tagSpecification, specificatioWithLatestClause = null;
        DBObject match = new BasicDBObject();

        if (tags != null && tags.length > 0) {
            // { 'CATEGORY' : {$regex : '.*' }, 'TAG' : {$in: ['jmeter']} }
            tagSpecification =
                    new BasicDBObject("LATEST", Boolean.TRUE).append("TAG_DATA.TAG", new BasicDBObject("$all", tags));
            match.put("$match", tagSpecification);
        } else {
            match.put("$match", new BasicDBObject("LATEST", Boolean.TRUE));
        }

        // unwind
        DBObject unwind = new BasicDBObject("$unwind", "$TAG_DATA");

        // group specification
        DBObject groupFields = new BasicDBObject("_id", "$TAG_DATA.TAG");
        groupFields.put("tagCount", new BasicDBObject("$sum", new Integer(1)));
        DBObject group = new BasicDBObject("$group", groupFields);

        DBCollection dbCollection = dbService.getCollection("projectdata");

        LOGGER.info("all specifications MATCH:{} UNWIND:{} GROUP:{}", match, unwind, group);

        AggregationOutput aggregate = dbCollection.aggregate(match, unwind, group);

        TagStatistics tagStatistics = new TagStatistics();
        for (DBObject o : aggregate.results()) {
            tagStatistics.add((String) o.get("_id"), (Integer) o.get("tagCount"));
        }

        // before returning lets remove the query tags from the statistics
        if (tags != null && tags.length > 0) {
            for (String s : tags) {
                tagStatistics.getTagFrequency().remove(s);
            }
        }

        return tagStatistics;

    }

}
