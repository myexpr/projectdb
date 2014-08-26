package thoughtwok.projectdb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import thoughtwok.projectdb.dao.ProjectCollectionEnum;
import thoughtwok.projectdb.dao.TagCollectionEnum;
import thoughtwok.projectdb.entity.CategoryEnum;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class TestDataUtil {

    public static void main(String[] args) {
        Map<CategoryEnum, String[]> tags = new HashMap<>();

        String[] COMMON_NAME = new String[] {"Sony Dealersource"};

        Boolean LATEST = Boolean.TRUE;
        String SOLUTION_DESCRIPTION = null;
        String[] PIDS = new String[] {};
        String[] CLIENTS = new String[] {};
        String[] INDUSTRIES = new String[] {};
        String[] MARKETS = new String[] {};


        tags.put(CategoryEnum.METHODOLOGY, new String[] {"Waterfall"});

        tags.put(CategoryEnum.COLLABORATION, new String[] {"Sharepoint"});

        tags.put(CategoryEnum.LANGUAGE, new String[] {"Java 1.4", " bash shell"});

        tags.put(CategoryEnum.IDE, new String[] {"Spring source tool suite"});

        tags.put(CategoryEnum.WEB_APPLICATION_SERVER, new String[] {"IBM HTTP Server", " WAS"});

        tags.put(CategoryEnum.PRODUCT, new String[] {"Solr", " omniture", " epsilon"});

        tags.put(CategoryEnum.FRAMEWORK, new String[] {"ibatis", " spring", " ehcache", " quartz", " jquery",
                " freemarker"});

        tags.put(CategoryEnum.PERSISTENT_STORAGE, new String[] {"Oracle"});

        tags.put(CategoryEnum.VERSION_CONTROL, new String[] {"svn"});

        tags.put(CategoryEnum.AUTOMATED_TESTING, new String[] {"junit"});

        tags.put(CategoryEnum.DESKTOP_TOOLS, new String[] {"Windows xp", " textpad", "notepad++", " putty", " winscp"});

        tags.put(CategoryEnum.BUILD_TOOLS, new String[] {"ant"});

        tags.put(CategoryEnum.HARDWARE, new String[] {"baremetal"});

        tags.put(CategoryEnum.MONITORING, new String[] {});

        tags.put(CategoryEnum.TRAFFIC_MANAGERS, new String[] {});


        DBObject document = new BasicDBObject();
        document.put(ProjectCollectionEnum.COMMON_NAME.name(), Arrays.asList(COMMON_NAME));
        document.put(ProjectCollectionEnum.LATEST.name(), Boolean.TRUE);

        List<DBObject> tagData = new ArrayList<>();
        for (Entry<CategoryEnum, String[]> tagsPerCategory : tags.entrySet()) {
            for (String i : tagsPerCategory.getValue()) {
                DBObject t = new BasicDBObject();
                t.put(TagCollectionEnum.CATEGORY.name(), tagsPerCategory.getKey().name());
                t.put(TagCollectionEnum.TAG.name(), i.trim());

                tagData.add(t);
            }
        }

        document.put(ProjectCollectionEnum.TAG_DATA.name(), tagData);

        System.out.println(document);
    }
}
