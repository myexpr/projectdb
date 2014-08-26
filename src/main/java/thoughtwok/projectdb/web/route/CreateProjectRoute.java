package thoughtwok.projectdb.web.route;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.stereotype.Controller;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;

@Controller
public class CreateProjectRoute extends FreemarkerBasedRoute {

    public CreateProjectRoute() throws IOException {
        super("/create", "pdb_create_project.ftl");
    }
    
    public CreateProjectRoute(String path, String templateName) throws IOException {
        super(path, templateName);
    }

    @Override
    protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
        SimpleHash root = new SimpleHash();
        root.put("projectParams", new HashMap<String, String>());
        root.put("errors", new HashSet<String>());
        this.getTemplate().process(root, writer);

    }

}
