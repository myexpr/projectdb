package thoughtwok.projectdb.service;

import thoughtwok.projectdb.dao.ProjectDao;
import thoughtwok.projectdb.dao.TagDao;
import thoughtwok.projectdb.entity.MetaStatistics;
import thoughtwok.projectdb.entity.TagStatistics;

public class StatisticsService {
    
    public MetaStatistics getStatistics() {
        ProjectDao pDao = new ProjectDao();
        TagDao tDao = new TagDao();

        Long countActiveProjects = pDao.countActiveProjects();
        TagStatistics tagStatistics = tDao.getTagStatistics();
        
        MetaStatistics metaStatistics = new MetaStatistics();
        metaStatistics.setActiveProjectCount(countActiveProjects);
        metaStatistics.setTagStatistics(tagStatistics);

        return metaStatistics;
    }

}
