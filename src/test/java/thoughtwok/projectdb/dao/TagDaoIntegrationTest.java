package thoughtwok.projectdb.dao;

import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import thoughtwok.projectdb.entity.TagStatistics;
import thoughtwok.projectdb.service.DbService;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-configuration.xml"})
public class TagDaoIntegrationTest {

    @Autowired
    TagDao tagDao;

    @Autowired
    DbService dbService;


    @Test
    public void testOverallStatistics() {
        TagStatistics stats = tagDao.getTagStatistics();
        for (Entry<String, Integer> e : stats.getTagFrequency().entrySet()) {
            System.out.println(e.getKey() + "<>" + e.getValue());
        }
    }

    @Test
    public void testSubStatisticsWithOneTag() {
        TagStatistics stats = tagDao.getTagStatisticsFor(new String[] {"Drools"});
        System.out.println(stats.getTagFrequency().entrySet().size());
        for (Entry<String, Integer> e : stats.getTagFrequency().entrySet()) {
            System.out.println(e.getKey() + "<>" + e.getValue());
        }
    }
}
