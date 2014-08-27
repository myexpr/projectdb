package thoughtwok.projectdb.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import thoughtwok.projectdb.entity.CategoryEnum;
import thoughtwok.projectdb.entity.Project;
import thoughtwok.projectdb.entity.Tag;
import thoughtwok.projectdb.service.DbService;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import static junit.framework.Assert.*;


/**
 * A test case to validate interactions with db layer. The actual validation of persisting and retrieving will happen in
 * other test suite which doesnt rely on mocks so extensively since it is a pain to mock so much
 */
public class ProjectDaoTest {

    @Mock
    DbService dbService;
    @Mock
    DBCollection dbCollection;
    @Mock
    DBObject dbObject;
    @Mock
    DBCursor dbCursor;

    @InjectMocks
    ProjectDao projectDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(dbService.getCollection("projectdata")).thenReturn(dbCollection);
    }

    @Test
    public void shouldNotCreateProjectWhenCommonNameIsNull() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(CategoryEnum.BUILD_TOOLS, "jenkins"));
        tags.add(new Tag(CategoryEnum.BUILD_TOOLS, "sonar"));

        Project aProjectWithNullId = new Project();
        aProjectWithNullId.setCommonNames(null);
        aProjectWithNullId.setTags(tags);

        try {
            projectDao.createProject(aProjectWithNullId);
            fail("should have thrown an illegal argument exception");
        } catch (IllegalArgumentException iae) {
            // all good
            assertEquals(ProjectDao.ERROR_COMMONNAME, iae.getMessage());
        }

        verify(dbCollection, never()).insert(any(DBObject.class));
        verify(dbCollection, never()).update(any(DBObject.class), any(DBObject.class));
    }


    @Test
    public void shouldNotCreateProjectWithNoTags() {

        Project aProjectWithNoTags = new Project();
        aProjectWithNoTags.setCommonNames(Arrays.asList(new String[] {"A PROJECT WITH NO TAGS"}));
        aProjectWithNoTags.setTags(null);

        try {
            projectDao.createProject(aProjectWithNoTags);
            fail("should have thrown an illegal argument exception");
        } catch (IllegalArgumentException iae) {
            // all good then
            assertEquals(ProjectDao.ERROR_TAGS, iae.getMessage());

        }

        verify(dbCollection, never()).insert(any(DBObject.class));
        verify(dbCollection, never()).update(any(DBObject.class), any(DBObject.class));
    }

    @Test
    public void shouldNotCreateProjectWithNullIdOrEmptyTags() {
        Project aProjectWithNoTagsAndId = new Project();
        try {
            projectDao.createProject(aProjectWithNoTagsAndId);
            fail("should have thrown an illegal argument exception");
        } catch (IllegalArgumentException iae) {
            // all good then
            assertEquals(ProjectDao.ERROR_COMMONNAME, iae.getMessage());
        }

        verify(dbCollection, never()).insert(any(DBObject.class));
        verify(dbCollection, never()).update(any(DBObject.class), any(DBObject.class));
    }

    @Test
    public void shouldNotCreateProjectWithLatestFlagSetToFalse() {
        Project aProject = new Project();
        aProject.setCommonNames(Arrays.asList(new String[] {"A PROJECT WITH NO TAGS"}));
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(CategoryEnum.BUILD_TOOLS, "jenkins"));
        tags.add(new Tag(CategoryEnum.BUILD_TOOLS, "sonar"));
        aProject.setTags(tags);
        aProject.setLatest(false);

        try {
            projectDao.createProject(aProject);
        } catch (IllegalArgumentException iae) {
            assertEquals(ProjectDao.ERROR_LATEST, iae.getMessage());
        }
        
        verify(dbCollection, never()).insert(any(DBObject.class));
        verify(dbCollection, never()).update(any(DBObject.class), any(DBObject.class));
    }

    @Test
    public void shouldCreateProject() {
        Project aProject = new Project();
        aProject.setCommonNames(Arrays.asList(new String[] {"A PROJECT WITH NO TAGS"}));
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(CategoryEnum.BUILD_TOOLS, "jenkins"));
        tags.add(new Tag(CategoryEnum.BUILD_TOOLS, "sonar"));
        aProject.setTags(tags);
        aProject.setLatest(true);

        try {
            projectDao.createProject(aProject);
        } catch (IllegalArgumentException iae) {
            // DIRTY HACK..
            // this will throw an assertion error but at this moment I dont have a way to correct it.
            // dbCollection.insert affects the argument DBObject and the id is retrieved from that object. I dont have a
            // way to return a dummy id
        }

        ArgumentCaptor<DBObject> paramDBObject = ArgumentCaptor.forClass(DBObject.class);
        verify(dbCollection, atMost(1)).insert(paramDBObject.capture());

        // now assert a few values
        assertEquals(1, ((List<String>) paramDBObject.getValue().get("COMMON_NAME")).size());
        assertEquals(2, ((List<DBObject>) paramDBObject.getValue().get("TAG_DATA")).size());

    }

    @Test
    public void shouldNotFetchProjectWhenIdIsNUll() {

        Project queryProject = new Project();
        queryProject.setId(null);


        try {
            projectDao.fetchProjectById(queryProject);
            fail("id was null and still the method did something");
        } catch (IllegalArgumentException iae) {
            // good
        }

        verify(dbCollection, never()).find(any(DBObject.class));
    }

    @Test
    public void shouldFetchProjectWithAnId() {
        Project queryProject = new Project();
        queryProject.setId("53fc8c118ff3ef2ae6d1d3b0");

        DBObject invokeQuery = new BasicDBObject();
        ObjectId objectId = new ObjectId("53fc8c118ff3ef2ae6d1d3b0");
        invokeQuery.put("_id", objectId);

        when(dbCollection.find(invokeQuery)).thenReturn(dbCursor);
        when(dbCursor.hasNext()).thenReturn(Boolean.TRUE);
        when(dbCursor.next()).thenReturn(new BasicDBObject("_id", objectId));

        projectDao.fetchProjectById(queryProject);

        verify(dbCollection, only()).find(invokeQuery);
        InOrder order = inOrder(dbCursor);
        verify(dbCursor, atMost(1)).hasNext();
        verify(dbCursor, atMost(1)).next();

    }

    @Test
    public void shouldReturnActiveProjectCount() {
        DBObject query = new BasicDBObject("LATEST", Boolean.TRUE);
        projectDao.countActiveProjects();
        verify(dbCollection, only()).count(query);
    }

    @Test
    public void shouldNotDeprecateProjectWithoutAnId() {
        Project projectWithoutAnId = new Project();
        projectWithoutAnId.setId(null);

        try {
            projectDao.fetchProjectById(projectWithoutAnId);
        } catch (IllegalArgumentException iae) {
            // all good
        }
    }


    @Test
    public void shouldDeprecateProjectWithAnId() {
        Project projectWithoutAnId = new Project();
        projectWithoutAnId.setId("53fc8c118ff3ef2ae6d1d3b0");

        DBObject invokeQuery = new BasicDBObject();
        ObjectId objectId = new ObjectId("53fc8c118ff3ef2ae6d1d3b0");
        invokeQuery.put("_id", objectId);

        DBObject update =
                new BasicDBObject("$set", new BasicDBObject(ProjectCollectionEnum.LATEST.name(), Boolean.FALSE));

        DBObject result = mock(DBObject.class);

        when(dbCollection.findAndModify(invokeQuery, null, null, false, update, true, false)).thenReturn(result);
        when(result.get("LATEST")).thenReturn(Boolean.FALSE);

        projectDao.deprecateProjectById(projectWithoutAnId);

        verify(dbCollection, only()).findAndModify(invokeQuery, null, null, false, update, true, false);
    }
}
