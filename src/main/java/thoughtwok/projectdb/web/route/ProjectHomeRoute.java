package thoughtwok.projectdb.web.route;

import java.io.IOException;
import java.io.Writer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import thoughtwok.projectdb.entity.MetaStatistics;
import thoughtwok.projectdb.service.StatisticsService;

@Controller
public class ProjectHomeRoute extends FreemarkerBasedRoute {

    @Autowired
    StatisticsService statisticsService;
    
    public ProjectHomeRoute() throws IOException {
        super("/", "pdb_project_home.ftl");
    }
    
    @Override
    protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
        MetaStatistics statistics = this.statisticsService.getStatistics();

        SimpleHash hash = new SimpleHash();
        hash.put("activeProjectCount", statistics.getActiveProjectCount());
        hash.put("tagFrequency", statistics.getTagStatistics().getTagFrequency().entrySet());

        this.getTemplate().process(hash, writer);
    }

}
