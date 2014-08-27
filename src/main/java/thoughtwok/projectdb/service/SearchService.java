package thoughtwok.projectdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thoughtwok.projectdb.entity.Project;

@Service
public class SearchService {

    @Autowired
    DbService dbService;

    public List<Project> fetchProjectsByTag(String[] tags) {
        return null;
    }

}
