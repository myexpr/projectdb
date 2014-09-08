package thoughtwok.projectdb.web.spring;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import thoughtwok.projectdb.dao.ProjectCollectionEnum;
import thoughtwok.projectdb.dao.ProjectDao;
import thoughtwok.projectdb.entity.CategoryEnum;
import thoughtwok.projectdb.entity.Project;
import thoughtwok.projectdb.entity.Tag;

@Controller
public class EditProjectController {

    @Autowired
    ProjectDao projectDao;

    @RequestMapping(value = "edit/{projectId}", method = RequestMethod.GET)
    public String editProject(@ModelAttribute("model") ModelMap model,
            @PathVariable(value = "projectId") String projectId) {
        Project query = new Project();
        query.setId(projectId);

        Project result = this.projectDao.fetchProjectById(query);
        Assert.notNull(result.getId(), "project id cant be null");
        Assert.notEmpty(result.getCommonNames(), "project should have atleast one common name");
        Assert.notEmpty(result.getTags(), "a persisted project should have atleast one tag");

        Map<String, String> projectMap = new HashMap();

        projectMap.put(ProjectCollectionEnum._ID.name(), getNullSafeString(result.getId()));
        projectMap.put(ProjectCollectionEnum.COMMON_NAME.name(), flattenList(result.getCommonNames()));
        projectMap.put(ProjectCollectionEnum.LATEST.name(), result.isLatest() ? "true" : "false");
        projectMap.put(ProjectCollectionEnum.SOLUTION_DESCRIPTION.name(),
                getNullSafeString(result.getSolutionDescription()));
        projectMap.put(ProjectCollectionEnum.PIDS.name(), flattenList(result.getPids()));
        projectMap.put(ProjectCollectionEnum.CLIENTS.name(), flattenList(result.getClients()));
        projectMap.put(ProjectCollectionEnum.INDUSTRIES.name(), flattenList(result.getIndustries()));
        projectMap.put(ProjectCollectionEnum.MARKETS.name(), flattenList(result.getMarkets()));

        Map<CategoryEnum, String> flattenedTags = this.flattenTags(result.getTags());

        // iterate and set it on the model
        for (Map.Entry<CategoryEnum, String> key : flattenedTags.entrySet()) {
            projectMap.put(key.getKey().name(), key.getValue());
        }

        model.put("projectParams", projectMap);


        return "pdb_create_project";
    }

    protected String getNullSafeString(String s) {
        if (s == null) {
            return "";
        } else {
            return s;
        }
    }

    protected String flattenList(List<String> values) {
        StringBuilder builder = new StringBuilder();

        if (values == null || values.size() == 0) {
            return builder.toString();
        }

        Iterator<String> i = values.iterator();
        while (i.hasNext()) {
            builder.append(i.next());
            if (i.hasNext()) {
                builder.append(", ");
            }
        }

        return builder.toString();
    }

    protected Map<CategoryEnum, String> flattenTags(List<Tag> tags) {
        Map<CategoryEnum, String> tagMap = new HashMap<CategoryEnum, String>();
        String valuesSoFar = null;

        for (Tag t : tags) {

            if (tagMap.containsKey(t.getCategory())) {
                valuesSoFar = tagMap.get(t.getCategory());
                if (valuesSoFar != null) {
                    valuesSoFar = valuesSoFar + ", " + t.getName();
                }
                tagMap.put(t.getCategory(), valuesSoFar);
            } else {
                tagMap.put(t.getCategory(), t.getName());
            }
        }

        return tagMap;

    }



}
