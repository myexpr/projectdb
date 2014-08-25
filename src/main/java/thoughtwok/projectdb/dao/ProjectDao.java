package thoughtwok.projectdb.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import thoughtwok.projectdb.entity.CategoryEnum;
import thoughtwok.projectdb.entity.Project;
import thoughtwok.projectdb.entity.Tag;
import thoughtwok.projectdb.service.DBService;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

public class ProjectDao {

    /**
     * Persists a {@code Project}.
     * 
     * @param project
     * @return the persisted projected updated with the _id
     */
    public Project createProject(Project project) {

        BasicDBObject dbObject = new BasicDBObject();

        // add common names
        this.appendToDbObject(dbObject, ProjectCollectionEnum.COMMON_NAME.name(), project.getCommonNames());
        this.appendToDbObject(dbObject, ProjectCollectionEnum.LATEST.name(), project.isLatest());
        this.appendToDbObject(dbObject, ProjectCollectionEnum.SOLUTION_DESCRIPTION.name(),
                project.getSolutionDescription());
        this.appendToDbObject(dbObject, ProjectCollectionEnum.PIDS.name(), project.getPids());
        this.appendToDbObject(dbObject, ProjectCollectionEnum.CLIENTS.name(), project.getClients());
        this.appendToDbObject(dbObject, ProjectCollectionEnum.INDUSTRIES.name(), project.getIndustries());
        this.appendToDbObject(dbObject, ProjectCollectionEnum.MARKETS.name(), project.getMarkets());

        // iterate and add tags
        List<DBObject> dbTags = new ArrayList<DBObject>();
        if (project.getTags() != null) {
            for (Tag t : project.getTags()) {
                BasicDBObject dbTag = new BasicDBObject();
                dbTag.append(TagCollectionEnum.CATEGORY.name(), t.getCategory().name());
                dbTag.append(TagCollectionEnum.TAG.name(), t.getName());
                dbTags.add(dbTag);
            }
        }
        this.appendToDbObject(dbObject, ProjectCollectionEnum.TAG_DATA.name(), dbTags);

        DBCollection collection = DBService.getInstance().getCollection("projectdata");
        collection.insert(dbObject);

        ObjectId id = (ObjectId) dbObject.get(ProjectCollectionEnum._ID.name().toLowerCase());

        project.setId(id.toString());

        return project;
    }

    public Project deprecateProjectById(Project queryProject) {
        ObjectId id = null;
        Project result = null;
        DBObject update =
                new BasicDBObject("$set", new BasicDBObject(ProjectCollectionEnum.LATEST.name(), Boolean.FALSE));

        if (queryProject.getId() == null) {
            return result;
        }

        id = new ObjectId(queryProject.getId());

        DBObject query = QueryBuilder.start(ProjectCollectionEnum._ID.name().toLowerCase()).is(id).get();
        DBCollection collection = DBService.getInstance().getCollection("projectdata");

        DBObject updatedDocument = collection.findAndModify(query, null, null, false, update, true, false);
        
        if (updatedDocument != null ) {
            queryProject.setLatest((boolean) updatedDocument.get(ProjectCollectionEnum.LATEST.name()));
        }
        
        return queryProject;
    }

    /**
     * given an id fetch {@code Project}
     * 
     * @param queryProject
     * @return
     */
    public Project fetchProjectById(Project queryProject) {
        ObjectId id = null;
        Project result = null;

        if (queryProject.getId() == null) {
            return result;
        }

        id = new ObjectId(queryProject.getId());

        DBObject query = QueryBuilder.start(ProjectCollectionEnum._ID.name().toLowerCase()).is(id).get();
        DBCollection collection = DBService.getInstance().getCollection("projectdata");
        DBCursor cursor = collection.find(query);

        if (cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            result = this.buildProjectFromDbObject(dbObject);
        }

        return result;
    }
    
    public Long countActiveProjects() {
        
        DBCollection collection = DBService.getInstance().getCollection("projectdata");
        DBObject query = QueryBuilder.start(ProjectCollectionEnum.LATEST.name()).is(Boolean.TRUE).get();
        
        long countOfActionProjects = collection.count(query);
        
        return new Long(countOfActionProjects);
    }

    /**
     * helper to really set dbobject attribute names after a quick null check
     * 
     * @param dbObject
     * @param attributeName
     * @param value
     */
    protected void appendToDbObject(BasicDBObject dbObject, String attributeName, Object value) {
        if (value != null) {
            dbObject.append(attributeName, value);
        }
    }

    /**
     * builds the entity from the {@code DBObject}
     * 
     * @param dbObject
     * @return the hydrated project
     */
    protected Project buildProjectFromDbObject(DBObject dbObject) {
        Project project = new Project();
        ObjectId id = (ObjectId) dbObject.get(ProjectCollectionEnum._ID.name().toLowerCase());

        project.setId(id.toString());
        project.setCommonNames(this.buildEntityAttributesFromDbObject(ProjectCollectionEnum.COMMON_NAME.name(),
                dbObject)); // commonnames
        project.setClients(this.buildEntityAttributesFromDbObject(ProjectCollectionEnum.CLIENTS.name(), dbObject)); // clients
        project.setPids(this.buildEntityAttributesFromDbObject(ProjectCollectionEnum.PIDS.name(), dbObject)); // PIDS
        project.setIndustries(this.buildEntityAttributesFromDbObject(ProjectCollectionEnum.INDUSTRIES.name(), dbObject)); // industries
        project.setMarkets(this.buildEntityAttributesFromDbObject(ProjectCollectionEnum.MARKETS.name(), dbObject)); // markets

        // solution description
        if (dbObject.get(ProjectCollectionEnum.SOLUTION_DESCRIPTION.name()) != null) {
            project.setSolutionDescription((String) dbObject.get(ProjectCollectionEnum.SOLUTION_DESCRIPTION.name()));
        }

        // latest
        if (dbObject.get(ProjectCollectionEnum.LATEST.name()) != null) {
            project.setLatest(((Boolean) dbObject.get(ProjectCollectionEnum.LATEST.name())).booleanValue());
        }

        // tags
        project.setTags(this.buildTagsFromDbObject(ProjectCollectionEnum.TAG_DATA.name(), dbObject));

        return project;
    }

    @SuppressWarnings("unchecked")
    protected List<String> buildEntityAttributesFromDbObject(String attributeName, DBObject dbObject) {
        List<String> dbList = null;
        List<String> stringList = null;

        dbList = (List<String>) dbObject.get(attributeName);

        if (dbList == null) {
            return null;
        }

        stringList = new ArrayList<>();
        for (String i : dbList) {
            stringList.add(i);
        }

        return stringList;
    }

    @SuppressWarnings("unchecked")
    protected List<Tag> buildTagsFromDbObject(String attributeName, DBObject dbObject) {
        List<Tag> tags = null;
        List<DBObject> dbList = null;
        String category, tag = null;

        if (dbObject.get(attributeName) == null) {
            return null;
        }

        tags = new ArrayList<>();
        dbList = (List<DBObject>) dbObject.get(attributeName);

        for (DBObject item : dbList) {
            category = (String) item.get(TagCollectionEnum.CATEGORY.name());
            tag = (String) item.get(TagCollectionEnum.TAG.name());

            Tag t = new Tag(CategoryEnum.valueOf(category), tag);
            tags.add(t);
        }

        return tags;
    }

}
