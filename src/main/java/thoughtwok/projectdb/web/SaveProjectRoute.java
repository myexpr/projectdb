package thoughtwok.projectdb.web;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import thoughtwok.projectdb.dao.ProjectCollectionEnum;
import thoughtwok.projectdb.dao.ProjectDao;
import thoughtwok.projectdb.entity.CategoryEnum;
import thoughtwok.projectdb.entity.Project;
import thoughtwok.projectdb.entity.Tag;
import thoughtwok.projectdb.web.route.FreemarkerBasedRoute;

public class SaveProjectRoute extends FreemarkerBasedRoute {

    public SaveProjectRoute(String path, String templateName) throws IOException {
        super(path, templateName);
    }

    @Override
    protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {

        ProjectDao projectDao = null;
        Project theProject = null;
        String commonName = null;
        SimpleHash root = null;
        Set<String> errors = null;

        projectDao = new ProjectDao();
        commonName = request.queryParams(ProjectCollectionEnum.COMMON_NAME.name());
        root = new SimpleHash();
        errors = new HashSet<>();

        theProject = new Project();
        theProject.setCommonNames(asList(request, ProjectCollectionEnum.COMMON_NAME.name()));
        theProject.setLatest(true);
        theProject.setSolutionDescription(request.queryParams(ProjectCollectionEnum.SOLUTION_DESCRIPTION.name()));
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
            Set<String> q = request.queryMap().toMap().keySet();
            Map<String, String> queryMap = new HashMap<>();
            for (String s : q) {
                queryMap.put(s, request.queryParams(s));
            }
            System.out.println(queryMap);

            root.put("projectParams", queryMap);
            root.put("errors", errors);
            this.getTemplate("pdb_create_project.ftl").process(root, writer);
            return;
        }

        // invoke product service and create the product
        theProject = projectDao.createProject(theProject);
        System.out.println("persisted with id " + theProject.getId());

        // now display the product
        // response.redirect("/project/" + theProject.getId());
    }

    protected List<String> asList(Request request, String paramName) {
        List<String> stringList = null;

        if (request.queryParams(paramName) == null || request.queryParams(paramName).length() == 0) {
            return stringList;
        }

        String input = request.queryParams(paramName);
        stringList = Arrays.asList(input.split(","));

        return stringList;
    }

    protected List<Tag> asTags(Request request, String paramName) {
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
