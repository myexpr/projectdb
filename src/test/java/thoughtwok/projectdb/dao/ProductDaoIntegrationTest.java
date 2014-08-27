package thoughtwok.projectdb.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import thoughtwok.projectdb.entity.CategoryEnum;
import thoughtwok.projectdb.entity.Project;
import thoughtwok.projectdb.entity.Tag;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import static junit.framework.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoIntegrationTest extends BaseIntegrationTest {

    private static String persistedProjectId;

    @Test
    public void shouldPersistAndRetrieveProject() {

        Project templateProject = this.getTemplateProject();
        this.deleteTestData();
        // get count and assert it is 0
        assertEquals(0L, dbService.getCollection("projectdata").count());

        Project createdProject = projectDao.createProject(templateProject);

        assertNotNull(createdProject.getId());
        assertEquals(templateProject.getCommonNames(), createdProject.getCommonNames());
        assertEquals(templateProject.getClients(), createdProject.getClients());
        assertEquals(templateProject.getIndustries(), createdProject.getIndustries());
        assertEquals(templateProject.getTags(), createdProject.getTags());
        // whats null; stays null
        assertNull(createdProject.getMarkets());
        assertNull(createdProject.getPids());
        // get count and assert it is 0
        assertEquals(1L, dbService.getCollection("projectdata").count());

        // finally set it for others to use
        ProductDaoIntegrationTest.persistedProjectId = createdProject.getId();
        
        templateProject = this.getTemplateProject();
        
        Project query = new Project();
        query.setId(ProductDaoIntegrationTest.persistedProjectId);

        Project fetchedProject = projectDao.fetchProjectById(query);
        assertEquals(templateProject.getCommonNames(), fetchedProject.getCommonNames());
        assertEquals(templateProject.getClients(), fetchedProject.getClients());
        assertEquals(templateProject.getIndustries(), fetchedProject.getIndustries());
        assertEquals(templateProject.getTags(), fetchedProject.getTags());

        // whats null; stays null
        assertNull(fetchedProject.getMarkets());
        assertNull(fetchedProject.getPids());

        // get count and assert it is 0
        assertEquals(1L, dbService.getCollection("projectdata").count());

    }

    @Test
    public void testActiveProjectCount() {

        Project templateProject = null;
        this.deleteTestData();

        templateProject = this.getTemplateProject();
        templateProject.setClients(null);;
        projectDao.createProject(templateProject);

        templateProject = this.getTemplateProject();
        templateProject.setMarkets(null);
        projectDao.createProject(templateProject);

        templateProject = this.getTemplateProject();
        templateProject.setMarkets(null);
        templateProject.setSolutionDescription(null);
        projectDao.createProject(templateProject);

        try {
            templateProject = this.getTemplateProject();
            templateProject.setLatest(Boolean.FALSE);
            projectDao.createProject(templateProject);
        } catch(IllegalArgumentException iae) {
            assertEquals(ProjectDao.ERROR_LATEST, iae.getMessage());
        }

        assertEquals(new Long(3), projectDao.countActiveProjects());
    }

    protected Project getTemplateProject() {
        Project project = new Project();
        project.setCommonNames(Arrays.asList(new String[] {"foo", "bar", "foo bar", "foobar"}));
        project.setClients(Arrays.asList(new String[] {"Hclient", "Bclient", "Tclient"}));
        project.setIndustries(Arrays.asList(new String[] {"retail", "telecom", "ecommerce"}));
        project.setSolutionDescription("all ecommerce solutions come here");
        project.setLatest(Boolean.TRUE);
        // now creats tags
        List<Tag> tags = new ArrayList<>();

        for (CategoryEnum c : new CategoryEnum[] {CategoryEnum.BUILD_TOOLS, CategoryEnum.FRAMEWORK}) {
            for (String s : new String[] {"tag1", "tag2", "tag3"}) {
                tags.add(new Tag(c, s));
            }
        }
        project.setTags(tags);

        return project;

    }

    @Test
    public void deprecateProject() {

        Project templateProject = null;
        this.deleteTestData();

        templateProject = this.getTemplateProject();
        templateProject.setClients(null);;
        projectDao.createProject(templateProject);

        templateProject = this.getTemplateProject();
        templateProject.setMarkets(null);
        Project projectToBeDeprecated = projectDao.createProject(templateProject);

        projectToBeDeprecated.setLatest(false);
        projectDao.deprecateProjectById(projectToBeDeprecated);

        DBCursor cursor =
                dbService.getCollection("projectdata").find(
                        new BasicDBObject("_id", new ObjectId(projectToBeDeprecated.getId())));
        DBObject dbObject = cursor.next();
        assertEquals(Boolean.FALSE, dbObject.get("LATEST"));


    }

    @Test
    public void shouldFetchProjectsByTag() {
        this.deleteTestData();
        this.insertTestData();
        
        String[] tags = new String[] {"Drools"};
        List<Project> fetchProjectsByTags = this.projectDao.fetchProjectsByTags(tags);
        assertEquals(1, fetchProjectsByTags.size());
    }
    
    @Test
    public void shouldFetchProjectsByTags() {
        this.deleteTestData();
        this.insertTestData();
        
        String[] tags = new String[] {"putty", "quartz", "ehcache"};
        List<Project> fetchProjectsByTags = this.projectDao.fetchProjectsByTags(tags);
        assertEquals(3, fetchProjectsByTags.size());
    }



}
