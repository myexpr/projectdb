package thoughtwok.projectdb.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import thoughtwok.projectdb.service.DbService;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import static junit.framework.Assert.*;

public class TagDaoTest {

    @Mock
    DbService dbService;

    @Mock
    DBCollection dbCollection;

    @Mock
    AggregationOutput aggregationOutput;

    @InjectMocks
    TagDao tagDao;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    public void shouldReturnStatisticsAcrossAllProjectsWhenNoInputSpecified() {
        String[] input = null;

        List<DBObject> results = new ArrayList<>();
        DBObject tagFreq = null;
        for (String s : new String[] {"foo", "bar"}) {
            tagFreq = new BasicDBObject("_id", "foo");
            tagFreq.put("tagCount", new Random().nextInt());
            results.add(tagFreq);
        }

        ArgumentCaptor<DBObject> matcher = ArgumentCaptor.forClass(DBObject.class);
        ArgumentCaptor<DBObject> unwind = ArgumentCaptor.forClass(DBObject.class);
        ArgumentCaptor<DBObject> group = ArgumentCaptor.forClass(DBObject.class);

        when(dbService.getCollection("projectdata")).thenReturn(dbCollection);
        when(dbCollection.aggregate(any(DBObject.class), any(DBObject.class), any(DBObject.class))).thenReturn(
                aggregationOutput);
        when(aggregationOutput.results()).thenReturn(results);

        tagDao.getTagStatisticsFor(input);

        verify(dbCollection, only()).aggregate(matcher.capture(), unwind.capture(), group.capture());

        // match only specifices latest == true
        DBObject actualMatchSpecification = (DBObject) matcher.getValue().get("$match");
        DBObject expectedMatchSpecification = new BasicDBObject("LATEST", Boolean.TRUE);
        assertEquals(expectedMatchSpecification, actualMatchSpecification);

        // unwind specification
        DBObject expectedUnwindSpec = new BasicDBObject("$unwind", "$TAG_DATA");
        assertEquals(expectedUnwindSpec, unwind.getValue());

    }

    public void shouldReturnStatisticsAcrossAllProjectsWhenInputSpecified() {
        String[] input = new String[] {"foo", "bar"};

        List<DBObject> results = new ArrayList<>();
        DBObject tagFreq = null;
        for (String s : new String[] {"foo", "bar"}) {
            tagFreq = new BasicDBObject("_id", "foo");
            tagFreq.put("tagCount", new Random().nextInt());
            results.add(tagFreq);
        }

        ArgumentCaptor<DBObject> matcher = ArgumentCaptor.forClass(DBObject.class);
        ArgumentCaptor<DBObject> unwind = ArgumentCaptor.forClass(DBObject.class);
        ArgumentCaptor<DBObject> group = ArgumentCaptor.forClass(DBObject.class);

        when(dbService.getCollection("projectdata")).thenReturn(dbCollection);
        when(dbCollection.aggregate(any(DBObject.class), any(DBObject.class), any(DBObject.class))).thenReturn(
                aggregationOutput);
        when(aggregationOutput.results()).thenReturn(results);

        tagDao.getTagStatisticsFor(input);

        verify(dbCollection, only()).aggregate(matcher.capture(), unwind.capture(), group.capture());

        // match only specifices latest == true
        DBObject actualMatchSpecification = (DBObject) matcher.getValue().get("$match");

        DBObject expectedMatchSpecification =
                new BasicDBObject("LATEST", Boolean.TRUE).append("TAG_DATA.TAG", new BasicDBObject("$all", input));

        assertEquals(expectedMatchSpecification, actualMatchSpecification);

        // unwind specification
        DBObject expectedUnwindSpec = new BasicDBObject("$unwind", "$TAG_DATA");
        assertEquals(expectedUnwindSpec, unwind.getValue());

    }

}
