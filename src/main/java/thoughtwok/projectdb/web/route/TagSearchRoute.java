package thoughtwok.projectdb.web.route;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import spark.Request;
import spark.Response;
import thoughtwok.projectdb.dao.ProjectDao;
import thoughtwok.projectdb.dao.TagDao;
import thoughtwok.projectdb.entity.Project;
import thoughtwok.projectdb.entity.TagStatistics;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

@Controller
public class TagSearchRoute extends FreemarkerBasedRoute {
    
    @Autowired
    ProjectDao projectDao;
    
    @Autowired
    TagDao tagDao;
    
    public TagSearchRoute() throws IOException {
        super("/search", "pdb_tag_search.ftl");
    }

    @Override
    protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
        
        String tag = request.queryParams("tag");
        List<Project> searchResults = null;
        TagStatistics tagStatistics = null;
        SimpleHash root = new SimpleHash();
        
        //check that a tag is present on the request
        Assert.hasText(tag);
        
        String[] tags = tag.split(",");
        
        //find tag statistics as well
        tagStatistics = this.tagDao.getTagStatisticsFor(tags);
        root.put("tagFrequency", tagStatistics.getTagFrequency().entrySet());
        root.put("querytag", tag);
        
        searchResults = projectDao.fetchProjectsByTags(tags);
        root.put("projects", searchResults);
        
        //finally render the template
        this.getTemplate().process(root, writer);
    }

}
