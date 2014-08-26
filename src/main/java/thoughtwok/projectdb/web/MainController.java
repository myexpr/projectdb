package thoughtwok.projectdb.web;

import static spark.Spark.staticFileLocation;

import java.io.IOException;
import java.io.Writer;

import spark.Request;
import spark.Response;
import thoughtwok.projectdb.web.route.CreateProjectRoute;
import thoughtwok.projectdb.web.route.FreemarkerBasedRoute;
import thoughtwok.projectdb.web.route.ProjectHomeRoute;
import thoughtwok.projectdb.web.route.SaveProjectRoute;
import thoughtwok.projectdb.web.route.ViewProjectRoute;
import freemarker.template.TemplateException;

public class MainController {

    public MainController() {
        try {
            staticFileLocation("/resources");
            initRoutes();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void initRoutes() throws IOException {
        spark.Spark.get(new ProjectHomeRoute("/", "pdb_project_home.ftl"));
        spark.Spark.get(new CreateProjectRoute("/create", "pdb_create_project.ftl"));
        spark.Spark.post(new SaveProjectRoute("/save", "pdb_create_project.ftl"));
        spark.Spark.get(new ViewProjectRoute("/project/:id", "pdb_display_project.ftl"));
    }


    public static void main(String[] args) {
        new MainController();
    }

}
