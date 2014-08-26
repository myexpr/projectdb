package thoughtwok.projectdb.web;

import static spark.Spark.staticFileLocation;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import spark.Request;
import spark.Response;
import spark.Route;
import thoughtwok.projectdb.web.route.CreateProjectRoute;
import thoughtwok.projectdb.web.route.ProjectHomeRoute;
import thoughtwok.projectdb.web.route.SaveProjectRoute;
import thoughtwok.projectdb.web.route.ViewProjectRoute;

@Controller
public class MainController {

    private static Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    
    @Autowired
    ProjectHomeRoute projectHomeRoute;
    
    @Autowired
    CreateProjectRoute createProjectRoute;
    
    @Autowired
    SaveProjectRoute saveProjectRoute;
    
    @Autowired
    ViewProjectRoute viewProjectRoute;

    public void initialize() {
        try {
            staticFileLocation("/resources");
            initRoutes();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void initRoutes() throws IOException {
        spark.Spark.get(new Route("/dummy") {
            
            @Override
            public Object handle(Request paramRequest, Response paramResponse) {
                return null;
            }
        });
        
        spark.Spark.get(this.projectHomeRoute);
        spark.Spark.get(this.createProjectRoute);
        spark.Spark.post(this.saveProjectRoute);
        spark.Spark.get(this.viewProjectRoute);
    }


    public static void main(String[] args) {
        // init spring
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-configuration.xml");

        MainController bean = (MainController) context.getBean("mainController");
        bean.initialize();

        LOGGER.info("projectdb webapp started");
    }

}
