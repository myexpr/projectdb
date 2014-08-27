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
public class TagDaoIntegration {

    @Autowired
    TagDao tagDao;

    @Autowired
    DbService dbService;

    private static String[] TEST_DATA =
            new String[] {"{ 'COMMON_NAME' : [ 'dot com', 'dot dot COM', 'dot com ESTORE' ], 'LATEST' : true, 'TAG_DATA' : [ { 'CATEGORY' : 'VERSION_CONTROL', 'TAG' : 'git' }, { 'CATEGORY' : 'VERSION_CONTROL', 'TAG' : 'tfs' }, { 'CATEGORY' : 'PRODUCT', 'TAG' : 'websphere commerce server' }, { 'CATEGORY' : 'PRODUCT', 'TAG' : 'WCS' }, { 'CATEGORY' : 'PRODUCT', 'TAG' : 'endeca' }, { 'CATEGORY' : 'PRODUCT', 'TAG' : 'solr' }, { 'CATEGORY' : 'PRODUCT', 'TAG' : 'memcache' }, { 'CATEGORY' : 'PRODUCT', 'TAG' : 'omniture' }, { 'CATEGORY' : 'PRODUCT', 'TAG' : 'rich relevance' }, { 'CATEGORY' : 'PRODUCT', 'TAG' : 'google adwords' }, { 'CATEGORY' : 'PRODUCT', 'TAG' : 'apigee' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'mybatis' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'spring' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'struts' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'ehcache' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'hibernate' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'quartz' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'jquery' }, { 'CATEGORY' : 'IDE', 'TAG' : 'eclipse' }, { 'CATEGORY' : 'IDE', 'TAG' : 'intellij' }, { 'CATEGORY' : 'BUILD_TOOLS', 'TAG' : 'ant' }, { 'CATEGORY' : 'BUILD_TOOLS', 'TAG' : 'sonar' }, { 'CATEGORY' : 'BUILD_TOOLS', 'TAG' : 'jenkins' }, { 'CATEGORY' : 'BUILD_TOOLS', 'TAG' : 'udeploy' }, { 'CATEGORY' : 'MONITORING', 'TAG' : 'gomez' }, { 'CATEGORY' : 'METHODOLOGY', 'TAG' : 'SA3' }, { 'CATEGORY' : 'METHODOLOGY', 'TAG' : 'Agile' }, { 'CATEGORY' : 'METHODOLOGY', 'TAG' : 'TDD' }, { 'CATEGORY' : 'LANGUAGE', 'TAG' : 'java' }, { 'CATEGORY' : 'LANGUAGE', 'TAG' : 'jse6' }, { 'CATEGORY' : 'LANGUAGE', 'TAG' : 'jse7' }, { 'CATEGORY' : 'LANGUAGE', 'TAG' : 'jee' }, { 'CATEGORY' : 'LANGUAGE', 'TAG' : 'bash shell' }, { 'CATEGORY' : 'PERSISTENT_STORAGE', 'TAG' : 'oracle' }, { 'CATEGORY' : 'PERSISTENT_STORAGE', 'TAG' : 'mft' }, { 'CATEGORY' : 'PERSISTENT_STORAGE', 'TAG' : 'apache cassandra' }, { 'CATEGORY' : 'DESKTOP_TOOLS', 'TAG' : 'windows 7' }, { 'CATEGORY' : 'DESKTOP_TOOLS', 'TAG' : 'windows xp' }, { 'CATEGORY' : 'DESKTOP_TOOLS', 'TAG' : 'notepad++' }, { 'CATEGORY' : 'DESKTOP_TOOLS', 'TAG' : 'textpad' }, { 'CATEGORY' : 'DESKTOP_TOOLS', 'TAG' : 'editplus' }, { 'CATEGORY' : 'DESKTOP_TOOLS', 'TAG' : 'putty' }, { 'CATEGORY' : 'DESKTOP_TOOLS', 'TAG' : 'mac osx' }, { 'CATEGORY' : 'TRAFFIC_MANAGERS', 'TAG' : 'akamai edge caching' }, { 'CATEGORY' : 'HARDWARE', 'TAG' : 'baremetal' }, { 'CATEGORY' : 'HARDWARE', 'TAG' : 'open stack' }, { 'CATEGORY' : 'HARDWARE', 'TAG' : 'vmware' }, { 'CATEGORY' : 'AUTOMATED_TESTING', 'TAG' : 'qtp' }, { 'CATEGORY' : 'AUTOMATED_TESTING', 'TAG' : 'soasta' }, { 'CATEGORY' : 'AUTOMATED_TESTING', 'TAG' : 'junit' }, { 'CATEGORY' : 'AUTOMATED_TESTING', 'TAG' : 'cucumber' }, { 'CATEGORY' : 'AUTOMATED_TESTING', 'TAG' : 'karma' }, { 'CATEGORY' : 'AUTOMATED_TESTING', 'TAG' : 'jasmine' }, { 'CATEGORY' : 'AUTOMATED_TESTING', 'TAG' : 'load runner' }, { 'CATEGORY' : 'AUTOMATED_TESTING', 'TAG' : 'jmeter' }, { 'CATEGORY' : 'AUTOMATED_TESTING', 'TAG' : 'selenium' }, { 'CATEGORY' : 'WEB_APPLICATION_SERVER', 'TAG' : 'IBM HTTP Server' }, { 'CATEGORY' : 'WEB_APPLICATION_SERVER', 'TAG' : 'IHS' }, { 'CATEGORY' : 'WEB_APPLICATION_SERVER', 'TAG' : 'Websphere application server' }, { 'CATEGORY' : 'WEB_APPLICATION_SERVER', 'TAG' : 'WAS' }, { 'CATEGORY' : 'WEB_APPLICATION_SERVER', 'TAG' : 'Tomcat' }, { 'CATEGORY' : 'COLLABORATION', 'TAG' : 'RS3' }, { 'CATEGORY' : 'COLLABORATION', 'TAG' : 'resultspace' }, { 'CATEGORY' : 'COLLABORATION', 'TAG' : 'jira' }, { 'CATEGORY' : 'COLLABORATION', 'TAG' : 'sharepoint' } ] }",
            "{ 'COMMON_NAME' : [ 'eight legged animal' ], 'LATEST' : true, 'TAG_DATA' : [ { 'CATEGORY' : 'PERSISTENT_STORAGE', 'TAG' : 'Oracle RAC' }, { 'CATEGORY' : 'HARDWARE', 'TAG' : 'baremetal' }, { 'CATEGORY' : 'PRODUCT', 'TAG' : 'Jboss cache' }, { 'CATEGORY' : 'PRODUCT', 'TAG' : 'Drools' }, { 'CATEGORY' : 'PRODUCT', 'TAG' : 'Jboss messaging' }, { 'CATEGORY' : 'PRODUCT', 'TAG' : 'Oracle SQL loader' }, { 'CATEGORY' : 'MONITORING', 'TAG' : 'custom monitoring scripts' }, { 'CATEGORY' : 'COLLABORATION', 'TAG' : 'RS3' }, { 'CATEGORY' : 'COLLABORATION', 'TAG' : 'Resultspace' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'Jboss cache' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'Drools' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'Jboss messaging' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'Oracle SQL loader' }, { 'CATEGORY' : 'BUILD_TOOLS', 'TAG' : 'Cruise control' }, { 'CATEGORY' : 'TRAFFIC_MANAGERS', 'TAG' : 'F5' }, { 'CATEGORY' : 'TRAFFIC_MANAGERS', 'TAG' : 'mod_jk' }, { 'CATEGORY' : 'METHODOLOGY', 'TAG' : 'SA3' }, { 'CATEGORY' : 'VERSION_CONTROL', 'TAG' : 'SVN' }, { 'CATEGORY' : 'VERSION_CONTROL', 'TAG' : 'Tortoise SVN client' }, { 'CATEGORY' : 'DESKTOP_TOOLS', 'TAG' : 'Windows XP' }, { 'CATEGORY' : 'DESKTOP_TOOLS', 'TAG' : 'putty' }, { 'CATEGORY' : 'DESKTOP_TOOLS', 'TAG' : 'edit plus' }, { 'CATEGORY' : 'IDE', 'TAG' : 'eclipse' }, { 'CATEGORY' : 'WEB_APPLICATION_SERVER', 'TAG' : 'Apache HTTP server' }, { 'CATEGORY' : 'WEB_APPLICATION_SERVER', 'TAG' : 'Jboss 4.3' }, { 'CATEGORY' : 'AUTOMATED_TESTING', 'TAG' : 'jmeter' }, { 'CATEGORY' : 'AUTOMATED_TESTING', 'TAG' : 'junit' }, { 'CATEGORY' : 'AUTOMATED_TESTING', 'TAG' : 'Easymock' }, { 'CATEGORY' : 'LANGUAGE', 'TAG' : 'Java 5' }, { 'CATEGORY' : 'LANGUAGE', 'TAG' : 'J2EE' } ] }",
            "{ 'COMMON_NAME' : [ 'wheel Offer' ], 'LATEST' : true, 'TAG_DATA' : [ { 'CATEGORY' : 'HARDWARE', 'TAG' : 'baremetal' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'mybatis' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'spring' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'quartz' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'jquery' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'Spring batch' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'ehcache' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'FB Graph API' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'facebook' }, { 'CATEGORY' : 'VERSION_CONTROL', 'TAG' : 'tfs' }, { 'CATEGORY' : 'MONITORING', 'TAG' : 'Splunk' }, { 'CATEGORY' : 'MONITORING', 'TAG' : 'Gomez' }, { 'CATEGORY' : 'COLLABORATION', 'TAG' : 'JIRA' }, { 'CATEGORY' : 'COLLABORATION', 'TAG' : 'Confluence' }, { 'CATEGORY' : 'COLLABORATION', 'TAG' : 'Sharepoint' }, { 'CATEGORY' : 'PERSISTENT_STORAGE', 'TAG' : 'Oracle' }, { 'CATEGORY' : 'BUILD_TOOLS', 'TAG' : 'ant' }, { 'CATEGORY' : 'BUILD_TOOLS', 'TAG' : 'jenkins' }, { 'CATEGORY' : 'BUILD_TOOLS', 'TAG' : 'sonar' }, { 'CATEGORY' : 'LANGUAGE', 'TAG' : 'JSE6' }, { 'CATEGORY' : 'LANGUAGE', 'TAG' : 'bash shell' }, { 'CATEGORY' : 'WEB_APPLICATION_SERVER', 'TAG' : 'IBM HTTP Server' }, { 'CATEGORY' : 'WEB_APPLICATION_SERVER', 'TAG' : 'WAS 7.0' }, { 'CATEGORY' : 'AUTOMATED_TESTING', 'TAG' : 'junit' }, { 'CATEGORY' : 'AUTOMATED_TESTING', 'TAG' : 'soasta' }, { 'CATEGORY' : 'AUTOMATED_TESTING', 'TAG' : 'qtp' }, { 'CATEGORY' : 'AUTOMATED_TESTING', 'TAG' : 'cucumber' }, { 'CATEGORY' : 'DESKTOP_TOOLS', 'TAG' : 'Windows 7' }, { 'CATEGORY' : 'DESKTOP_TOOLS', 'TAG' : 'textpad' }, { 'CATEGORY' : 'DESKTOP_TOOLS', 'TAG' : 'notepad++' }, { 'CATEGORY' : 'DESKTOP_TOOLS', 'TAG' : 'putty' }, { 'CATEGORY' : 'DESKTOP_TOOLS', 'TAG' : 'winscp' }, { 'CATEGORY' : 'METHODOLOGY', 'TAG' : 'Agile' }, { 'CATEGORY' : 'IDE', 'TAG' : 'RSA' }, { 'CATEGORY' : 'IDE', 'TAG' : 'Spring source tool suite' }, { 'CATEGORY' : 'IDE', 'TAG' : 'RAD' }, { 'CATEGORY' : 'PRODUCT', 'TAG' : 'Endeca' } ] }",
            "{ 'COMMON_NAME' : [ 'electronic super chain' ], 'LATEST' : true, 'TAG_DATA' : [ { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'ibatis' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'spring' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'ehcache' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'quartz' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'jquery' }, { 'CATEGORY' : 'FRAMEWORK', 'TAG' : 'freemarker' }, { 'CATEGORY' : 'BUILD_TOOLS', 'TAG' : 'ant' }, { 'CATEGORY' : 'WEB_APPLICATION_SERVER', 'TAG' : 'IBM HTTP Server' }, { 'CATEGORY' : 'WEB_APPLICATION_SERVER', 'TAG' : 'WAS' }, { 'CATEGORY' : 'HARDWARE', 'TAG' : 'baremetal' }, { 'CATEGORY' : 'METHODOLOGY', 'TAG' : 'Waterfall' }, { 'CATEGORY' : 'PRODUCT', 'TAG' : 'Solr' }, { 'CATEGORY' : 'PRODUCT', 'TAG' : 'omniture' }, { 'CATEGORY' : 'PRODUCT', 'TAG' : 'epsilon' }, { 'CATEGORY' : 'DESKTOP_TOOLS', 'TAG' : 'Windows xp' }, { 'CATEGORY' : 'DESKTOP_TOOLS', 'TAG' : 'textpad' }, { 'CATEGORY' : 'DESKTOP_TOOLS', 'TAG' : 'notepad++' }, { 'CATEGORY' : 'DESKTOP_TOOLS', 'TAG' : 'putty' }, { 'CATEGORY' : 'DESKTOP_TOOLS', 'TAG' : 'winscp' }, { 'CATEGORY' : 'PERSISTENT_STORAGE', 'TAG' : 'Oracle' }, { 'CATEGORY' : 'AUTOMATED_TESTING', 'TAG' : 'junit' }, { 'CATEGORY' : 'LANGUAGE', 'TAG' : 'Java 1.4' }, { 'CATEGORY' : 'LANGUAGE', 'TAG' : 'bash shell' }, { 'CATEGORY' : 'IDE', 'TAG' : 'Spring source tool suite' }, { 'CATEGORY' : 'VERSION_CONTROL', 'TAG' : 'svn' }, { 'CATEGORY' : 'COLLABORATION', 'TAG' : 'Sharepoint' } ] }" };

 
    @Test
    public void dropAndInsertTestData() {
        DBCollection dbCollection = dbService.getCollection("projectdata");
        dbCollection.drop();

        for (String s : TEST_DATA) {
            DBObject o = (DBObject) JSON.parse(s);
            dbCollection.insert(o);
        }
    }
    
    public void testOverallStatistics() {
        TagStatistics stats = tagDao.getTagStatistics();
        for (Entry<String, Integer> e : stats.getTagFrequency().entrySet()) {
            System.out.println(e.getKey() + "<>" + e.getValue());
        }
    }

    public void testSubStatisticsWithOneTag() {
        TagStatistics stats = tagDao.getTagStatisticsFor(new String[] {"Drools"});
        System.out.println(stats.getTagFrequency().entrySet().size());
        for (Entry<String, Integer> e : stats.getTagFrequency().entrySet()) {
            System.out.println(e.getKey() + "<>" + e.getValue());
        }
    }
}
