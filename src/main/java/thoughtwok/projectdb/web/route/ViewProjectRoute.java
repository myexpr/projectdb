package thoughtwok.projectdb.web.route;

import java.io.IOException;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;
import thoughtwok.projectdb.dao.ProjectDao;
import thoughtwok.projectdb.entity.Project;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

public class ViewProjectRoute extends FreemarkerBasedRoute {
    
    private static Logger LOGGER = LoggerFactory.getLogger(ViewProjectRoute.class);

    public ViewProjectRoute(String path, String templateName) throws IOException {
        super(path, templateName);
    }

    @Override
    protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {

        ProjectDao projectDao = null;
        Project theProject = null;
        String projectId = null;
        SimpleHash root = null;

        projectDao = new ProjectDao();
        projectId = request.params(":id");
        root = new SimpleHash();

        theProject = new Project();
        theProject.setId(projectId);
        theProject = projectDao.fetchProjectById(theProject);

        root.put("project", theProject);
        
        this.getTemplate().process(root, writer);
    }

}
