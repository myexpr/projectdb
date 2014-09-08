package thoughtwok.projectdb.web.spring;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import thoughtwok.projectdb.entity.MetaStatistics;
import thoughtwok.projectdb.service.StatisticsService;

@Controller
public class HomeController {
    
    @Autowired
    StatisticsService statisticsService;

    @RequestMapping(value = {"home","/"}, method = RequestMethod.GET)
    public String homePage(@ModelAttribute("model") ModelMap model, HttpServletRequest request) {
        MetaStatistics statistics = this.statisticsService.getStatistics();
        
        String contextPath = request.getContextPath();
        model.put("contextPath", contextPath);
        model.put("activeProjectCount", statistics.getActiveProjectCount());
        model.put("tagFrequency", statistics.getTagStatistics().getTagFrequency().entrySet());

        return "pdb_project_home";
    }
    
}
