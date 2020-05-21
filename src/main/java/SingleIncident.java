import java.util.Date;

public class SingleIncident {
    private int Priority;
    private Date createdDateTime;
    private int ResolutionTimeInSec;

    public SingleIncident(int priority, Date createdDateTime, int resolutionTimeInSec) {
        Priority = priority;
        this.createdDateTime = createdDateTime;
        ResolutionTimeInSec = resolutionTimeInSec;
    }

    public int getPriority() {
        return Priority;
    }

    public void setPriority(int priority) {
        Priority = priority;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public int getResolutionTimeInSec() {
        return ResolutionTimeInSec;
    }

    public void setResolutionTimeInSec(int resolutionTimeInSec) {
        ResolutionTimeInSec = resolutionTimeInSec;
    }
}
