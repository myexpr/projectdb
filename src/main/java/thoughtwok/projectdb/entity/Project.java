package thoughtwok.projectdb.entity;

import java.util.List;

public class Project {
    
    private String id;
    
    private boolean latest;
    
    private List<String> commonNames;
    
    private String solutionDescription;
    
    private List<String> pids;
    
    private List<String> clients;
    
    private List<String> industries;
    
    private List<String> markets;
    
    private List<Tag> tags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public List<String> getCommonNames() {
        return commonNames;
    }

    public void setCommonNames(List<String> commonNames) {
        this.commonNames = commonNames;
    }

    public String getSolutionDescription() {
        return solutionDescription;
    }

    public void setSolutionDescription(String solutionDescription) {
        this.solutionDescription = solutionDescription;
    }

    public List<String> getPids() {
        return pids;
    }

    public void setPids(List<String> pids) {
        this.pids = pids;
    }

    public List<String> getClients() {
        return clients;
    }

    public void setClients(List<String> clients) {
        this.clients = clients;
    }

    public List<String> getIndustries() {
        return industries;
    }

    public void setIndustries(List<String> industries) {
        this.industries = industries;
    }

    public List<String> getMarkets() {
        return markets;
    }

    public void setMarkets(List<String> markets) {
        this.markets = markets;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Project [id=" + id + ", latest=" + latest + ", commonNames=" + commonNames + ", solutionDescription="
                + solutionDescription + ", pids=" + pids + ", clients=" + clients + ", industries=" + industries
                + ", markets=" + markets + ", tags=" + tags + "]";
    }

}
