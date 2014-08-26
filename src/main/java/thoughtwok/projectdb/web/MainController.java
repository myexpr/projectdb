package thoughtwok.projectdb.web;

import java.io.IOException;
import java.io.Writer;

import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.staticFileLocation;
import thoughtwok.projectdb.entity.MetaStatistics;
import thoughtwok.projectdb.service.StatisticsService;
import freemarker.template.SimpleHash;
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

        spark.Spark.get(new FreemarkerBasedRoute("/", "project_stats.ftl") {

            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException,
                    TemplateException {
                StatisticsService ssService = new StatisticsService();
                MetaStatistics statistics = ssService.getStatistics();

                SimpleHash hash = new SimpleHash();
                hash.put("activeProjectCount", statistics.getActiveProjectCount());
                hash.put("tagFrequency", statistics.getTagStatistics().getTagFrequency().entrySet());

                this.getTemplate().process(hash, writer);
            }
        });


        spark.Spark.get(new FreemarkerBasedRoute("/jqdemo", "jq.ftl") {

            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException,
                    TemplateException {
                StatisticsService ssService = new StatisticsService();
                MetaStatistics statistics = ssService.getStatistics();

                SimpleHash hash = new SimpleHash();
                hash.put("activeProjectCount", statistics.getActiveProjectCount());
                hash.put("tagFrequency", statistics.getTagStatistics().getTagFrequency().entrySet());

                this.getTemplate().process(hash, writer);
            }
        });
    }

    public static void main(String[] args) {
        new MainController();
    }

}
