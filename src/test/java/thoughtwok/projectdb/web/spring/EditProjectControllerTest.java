package thoughtwok.projectdb.web.spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;

import thoughtwok.projectdb.dao.ProjectCollectionEnum;
import thoughtwok.projectdb.dao.ProjectDao;
import thoughtwok.projectdb.entity.CategoryEnum;
import thoughtwok.projectdb.entity.Project;
import thoughtwok.projectdb.entity.Tag;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EditProjectControllerTest {

    @Mock
    ProjectDao projectDao;

    @Mock
    HttpServletRequest request;

    @Mock
    ModelMap modelMap;

    @InjectMocks
    EditProjectController editProjectController;
    
    Project aProject = null;

    @Before 
    public void setDummyFetchedProject() {
        String projectId = "foobar";

        aProject = new Project();
        aProject.setId(projectId);
        aProject.setCommonNames(Arrays.asList(new String[] {"A PROJECT WITH NO TAGS"}));
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(CategoryEnum.BUILD_TOOLS, "jenkins"));
        tags.add(new Tag(CategoryEnum.BUILD_TOOLS, "sonar"));
        tags.add(new Tag(CategoryEnum.IDE, "eclipse"));

        aProject.setTags(tags);
        aProject.setLatest(true);
    }
    
    @Test
    public void shouldFetchProjectFromProjectDao() {

        when(projectDao.fetchProjectById(any(Project.class))).thenReturn(aProject);
        
        editProjectController.editProject(modelMap, "foobar");
        ArgumentCaptor<Project> query = ArgumentCaptor.forClass(Project.class);

        verify(projectDao).fetchProjectById(query.capture());
        assertEquals("foobar", query.getValue().getId());
    }

    @Test
    public void shouldPopulateModelMapWithDataFromFetchedProject() {

        String projectId = "foobar";

        Project aProject = new Project();
        aProject.setId(projectId);
        aProject.setCommonNames(Arrays.asList(new String[] {"A PROJECT WITH NO TAGS"}));
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(CategoryEnum.BUILD_TOOLS, "jenkins"));
        tags.add(new Tag(CategoryEnum.BUILD_TOOLS, "sonar"));
        tags.add(new Tag(CategoryEnum.IDE, "eclipse"));

        aProject.setTags(tags);
        aProject.setLatest(true);
        when(projectDao.fetchProjectById(any(Project.class))).thenReturn(aProject);

        String resultTemplatePath = editProjectController.editProject(modelMap, projectId);
        
        ArgumentCaptor<Map> parameters = ArgumentCaptor.forClass(Map.class);

        verify(modelMap).put(eq("projectParams"), parameters.capture());
        
//        ProjectCollectionEnum._ID.name(), projectId);
        assertEquals("A PROJECT WITH NO TAGS", parameters.getValue().get(ProjectCollectionEnum.COMMON_NAME.name()));
        assertEquals("true", parameters.getValue().get(ProjectCollectionEnum.LATEST.name()));
        assertEquals("jenkins, sonar", parameters.getValue().get(CategoryEnum.BUILD_TOOLS.name()));
        assertEquals("eclipse", parameters.getValue().get(CategoryEnum.IDE.name()));
        
        assertEquals("pdb_create_project", resultTemplatePath);
    }

}
