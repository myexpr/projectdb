package thoughtwok.projectdb.entity;

public class MetaStatistics {
    
    private Long activeProjectCount;
    
    private TagStatistics tagStatistics;

    public Long getActiveProjectCount() {
        return activeProjectCount;
    }

    public void setActiveProjectCount(Long activeProjectCount) {
        this.activeProjectCount = activeProjectCount;
    }

    public TagStatistics getTagStatistics() {
        return tagStatistics;
    }

    public void setTagStatistics(TagStatistics tagStatistics) {
        this.tagStatistics = tagStatistics;
    }

    @Override
    public String toString() {
        return "MetaStatistics [activeProjectCount=" + activeProjectCount + ", tagStatistics=" + tagStatistics + "]";
    }
    
    

}
