import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CalculatedIncidents {
    private ArrayList<SingleIncident> singlePriorityIncidents;
    private final int priority;

    public CalculatedIncidents(ArrayList<SingleIncident> myIncidents, int priority) {
        this.singlePriorityIncidents = myIncidents;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public ArrayList<SingleIncident> getMyIncidents() {
        return singlePriorityIncidents;
    }

    public void addIncident(int priority, Date createdDateTime, int resolutionTimeInSec) {
        singlePriorityIncidents.add(new SingleIncident(priority, createdDateTime, resolutionTimeInSec));
    }

    public int calculateAverageResolutionTimeInSec(Date date, boolean lastYear) {
        int resolutionTimeInSec = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        for (int i = 0; i < singlePriorityIncidents.size(); i++) {
            if(lastYear){
                if(singlePriorityIncidents.get(i).getCreatedDateTime().compareTo(date) <= 0) {
//                    System.out.println("Date: " + sdf.format(date) + " vs: " + sdf.format(singlePriorityIncidents.get(i).getCreatedDateTime()) + " returns: " + singlePriorityIncidents.get(i).getCreatedDateTime().compareTo(date));
                    resolutionTimeInSec += singlePriorityIncidents.get(i).getResolutionTimeInSec();
                }
            } else {
                if(singlePriorityIncidents.get(i).getCreatedDateTime().compareTo(date) > 0) {
//                    System.out.println("Date: " + sdf.format(date) + " vs: " + sdf.format(singlePriorityIncidents.get(i).getCreatedDateTime()) + " returns: " + singlePriorityIncidents.get(i).getCreatedDateTime().compareTo(date));
                    resolutionTimeInSec += singlePriorityIncidents.get(i).getResolutionTimeInSec();
                }
            }
        }
            return resolutionTimeInSec / singlePriorityIncidents.size();
        }
    }

