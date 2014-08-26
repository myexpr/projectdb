package thoughtwok.projectdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import thoughtwok.projectdb.dao.ProjectDao;
import thoughtwok.projectdb.dao.TagDao;
import thoughtwok.projectdb.entity.MetaStatistics;
import thoughtwok.projectdb.entity.TagStatistics;

@Component
public class StatisticsService {
    
    @Autowired
    ProjectDao projectDao;
    
    @Autowired
    TagDao tagDao;
    
    
    public MetaStatistics getStatistics() {
    
        Long countActiveProjects = projectDao.countActiveProjects();
        TagStatistics tagStatistics = tagDao.getTagStatistics();
        
        MetaStatistics metaStatistics = new MetaStatistics();
        metaStatistics.setActiveProjectCount(countActiveProjects);
        metaStatistics.setTagStatistics(tagStatistics);

        return metaStatistics;
    }

}
