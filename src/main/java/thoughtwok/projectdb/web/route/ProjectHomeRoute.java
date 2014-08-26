package thoughtwok.projectdb.web.route;

import java.io.IOException;
import java.io.Writer;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import thoughtwok.projectdb.entity.MetaStatistics;
import thoughtwok.projectdb.service.StatisticsService;

public class ProjectHomeRoute extends FreemarkerBasedRoute {

    public ProjectHomeRoute(String path, String templateName) throws IOException {
        super(path, templateName);
    }

    @Override
    protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
        StatisticsService ssService = new StatisticsService();
        MetaStatistics statistics = ssService.getStatistics();

        SimpleHash hash = new SimpleHash();
        hash.put("activeProjectCount", statistics.getActiveProjectCount());
        hash.put("tagFrequency", statistics.getTagStatistics().getTagFrequency().entrySet());

        this.getTemplate().process(hash, writer);
    }

}
