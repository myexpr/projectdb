package thoughtwok.projectdb.web.spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import thoughtwok.projectdb.dao.ProjectCollectionEnum;
import thoughtwok.projectdb.dao.ProjectDao;
import thoughtwok.projectdb.dao.TagDao;
import thoughtwok.projectdb.entity.CategoryEnum;
import thoughtwok.projectdb.entity.MetaStatistics;
import thoughtwok.projectdb.entity.Project;
import thoughtwok.projectdb.entity.Tag;
import thoughtwok.projectdb.entity.TagStatistics;
import thoughtwok.projectdb.service.StatisticsService;
import freemarker.template.SimpleHash;


@Controller
public class AppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

    @Autowired
    ProjectDao projectDao;

    @Autowired
    StatisticsService statisticsService;

    @Autowired
    TagDao tagDao;

    @RequestMapping(value = {"home","/", ""}, method = RequestMethod.GET)
    public String homePage(@ModelAttribute("model") ModelMap model, HttpServletRequest request) {
        MetaStatistics statistics = this.statisticsService.getStatistics();
        
        String contextPath = request.getContextPath();
        model.put("contextPath", contextPath);
        model.put("activeProjectCount", statistics.getActiveProjectCount());
        model.put("tagFrequency", statistics.getTagStatistics().getTagFrequency().entrySet());

        return "pdb_project_home";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createProject(@ModelAttribute("model") ModelMap model, HttpServletRequest request) {
        String contextPath = request.getContextPath();
        model.put("contextPath", contextPath);
        model.put("projectParams", new HashMap<String, String>());
        model.put("errors", new HashSet<String>());
        return "pdb_create_project";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String saveProject(@ModelAttribute("model") ModelMap model, HttpServletRequest request) {
        String contextPath = request.getContextPath();
        Project theProject = null;
        String commonName = null;
        Set<String> errors = new HashSet<>();

        model.put("contextPath", contextPath);
        commonName = request.getParameter(ProjectCollectionEnum.COMMON_NAME.name());

        theProject = new Project();
        theProject.setCommonNames(asList(request, ProjectCollectionEnum.COMMON_NAME.name()));
        theProject.setLatest(true);
        theProject.setSolutionDescription(request.getParameter(ProjectCollectionEnum.SOLUTION_DESCRIPTION.name()));
        theProject.setPids(asList(request, ProjectCollectionEnum.PIDS.name()));
        theProject.setClients(asList(request, ProjectCollectionEnum.CLIENTS.name()));
        theProject.setIndustries(asList(request, ProjectCollectionEnum.INDUSTRIES.name()));
        theProject.setMarkets(asList(request, ProjectCollectionEnum.MARKETS.name()));

        // iterate through permissible tag categories and build tag list
        List<Tag> aggregatedTags = new ArrayList<>();
        for (CategoryEnum t : CategoryEnum.values()) {
            List<Tag> asTags = asTags(request, t.name());
            if (asTags != null && asTags.size() > 0) {
                aggregatedTags.addAll(asTags);
            }
        }

        theProject.setTags(aggregatedTags);

        // validation checks
        // atleast one name is required and one tag is required
        if (commonName == null || commonName.length() == 0) {
            errors.add("atleast one name is required for the record to be persisted");
        }
        if (aggregatedTags.size() == 0) {
            errors.add("atleast one tag is required for the record to be persisted");
        }
        if (errors.size() > 0) {
            // we encountered a validation error
            Set<String> q = request.getParameterMap().keySet();
            Map<String, String> queryMap = new HashMap<>();
            for (String s : q) {
                queryMap.put(s, request.getParameter(s));
            }
            System.out.println(queryMap);

            model.put("projectParams", queryMap);
            model.put("errors", errors);
            return "pdb_create_project";
        }

        // invoke product service and create the product
        theProject = projectDao.createProject(theProject);
        LOGGER.debug("persisted project with id {}", theProject.getId());

        // now display the product
        model.put("project", theProject);
        return "pdb_display_project";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String tagSearch(@ModelAttribute("model") ModelMap model, @RequestParam(value = "tag") String tag, HttpServletRequest request) {
        String contextPath = request.getContextPath();
        List<Project> searchResults = null;
        TagStatistics tagStatistics = null;

        model.put("contextPath", contextPath);
        // check that a tag is present on the request
        Assert.hasText(tag);

        String[] tags = tag.split(",");

        // find tag statistics as well
        tagStatistics = this.tagDao.getTagStatisticsFor(tags);
        model.put("tagFrequency", tagStatistics.getTagFrequency().entrySet());
        model.put("querytag", tag);

        searchResults = projectDao.fetchProjectsByTags(tags);
        model.put("projects", searchResults);

        // finally render the template
        return "pdb_tag_search";

    }

    @RequestMapping(value = "project/{projectId}", method = RequestMethod.GET)
    public String displayProject(@ModelAttribute("model") ModelMap model, @PathVariable(value="projectId") String projectId, HttpServletRequest request) {
        String contextPath = request.getContextPath();
        Project theProject = null;

        model.put("contextPath", contextPath);
        theProject = new Project();
        theProject.setId(projectId);
        theProject = projectDao.fetchProjectById(theProject);

        model.put("project", theProject);
        
        return "pdb_display_project";
    }

    protected List<String> asList(HttpServletRequest request, String paramName) {
        List<String> stringList = null;

        if (request.getParameter(paramName) == null || request.getParameter(paramName).length() == 0) {
            return stringList;
        }

        String input = request.getParameter(paramName);
        stringList = Arrays.asList(input.split(","));

        return stringList;
    }

    protected List<Tag> asTags(HttpServletRequest request, String paramName) {
        List<String> stringInput = null;
        List<Tag> tagList = null;

        stringInput = this.asList(request, paramName);
        if (stringInput == null || stringInput.size() == 0) {
            return tagList;
        }

        tagList = new ArrayList<>();
        for (String s : stringInput) {
            tagList.add(new Tag(CategoryEnum.valueOf(paramName), s));
        }

        return tagList;
    }
}
