package thoughtwok.projectdb.web.spring;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.filter.OncePerRequestFilter;

import static org.mockito.Mockito.*;

import thoughtwok.projectdb.entity.MetaStatistics;
import thoughtwok.projectdb.entity.TagStatistics;
import thoughtwok.projectdb.service.StatisticsService;

@RunWith(MockitoJUnitRunner.class)
public class HomeContollerTest {

    @Mock
    StatisticsService service;

    @InjectMocks
    HomeController homeController;

    @Mock
    ModelMap model;

    @Mock
    HttpServletRequest request;

    @Test
    public void shouldFetchStatsFromStatisticsService() {
        MetaStatistics stats = new MetaStatistics();
        TagStatistics tagStats = new TagStatistics();
        stats.setTagStatistics(tagStats);
        
        when(service.getStatistics()).thenReturn(stats);
        when(request.getContextPath()).thenReturn("/");
        homeController.homePage(model, request);
        
        //only one call to the stats service
        verify(service, only()).getStatistics();
        //should set the project count and tagstats object
        verify(model).put("contextPath", "/");
        verify(model).put("activeProjectCount", null);
        verify(model).put("tagFrequency", tagStats.getTagFrequency().entrySet());
    }
}
