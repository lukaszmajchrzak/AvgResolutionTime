import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.checkerframework.checker.units.qual.C;

public class ReadFile {
    private HashMap<Integer,CalculatedIncidents> incidentsByPriority = new HashMap();

    public ReadFile() {
        incidentsByPriority.put(1,new CalculatedIncidents(new ArrayList<SingleIncident>(),1));
        incidentsByPriority.put(2,new CalculatedIncidents(new ArrayList<SingleIncident>(),2));
        incidentsByPriority.put(3,new CalculatedIncidents(new ArrayList<SingleIncident>(),3));
        incidentsByPriority.put(4,new CalculatedIncidents(new ArrayList<SingleIncident>(),4));
        incidentsByPriority.put(5,new CalculatedIncidents(new ArrayList<SingleIncident>(),5));
    }

    public void readFile(File myFile){

        try {
            FileInputStream fis = new FileInputStream(myFile);
            Workbook workbook = new XSSFWorkbook(fis);

            Sheet dataSheet = workbook.getSheet("Data");

            calculateStatistics(dataSheet);


        } catch(IOException e){
            e.printStackTrace();
        }

    }

    private void calculateStatistics(Sheet dataSheet){
        Row columnRow = dataSheet.getRow(0);
        int priorityColumn =0, resolutionTimeColumn =0, createdOnColumn =0,resolvedOnColumn=0, counter;
        counter = 0;
        while(columnRow.getCell(counter) != null){
            if(columnRow.getCell(counter).getStringCellValue().equals("Priority"))
                priorityColumn = counter;
            if(columnRow.getCell(counter).getStringCellValue().equals("Resolution Time in sec"))
                resolutionTimeColumn = counter;
            if(columnRow.getCell(counter).getStringCellValue().equals("Created On"))
                createdOnColumn = counter;
            if(columnRow.getCell(counter).getStringCellValue().equals("ResolvedDateTime"))
                resolvedOnColumn = counter;
            counter++;
        }
        counter = 1;
        System.out.println("PriorityColumn: " + priorityColumn);
        System.out.println("resolutionTimeCol: " + resolutionTimeColumn);
        System.out.println("createdOnCol: " + createdOnColumn);
        System.out.println("resolvedONCOl: " + resolvedOnColumn);
        while(dataSheet.getRow(counter) != null){
//            System.out.println(dataSheet.getRow(counter).getCell(priorityColumn).getStringCellValue());
            if(dataSheet.getRow(counter).getCell(priorityColumn).getNumericCellValue() > 0){
//                System.out.println((int)dataSheet.getRow(counter).getCell(priorityColumn).getNumericCellValue());
                this.incidentsByPriority.get((int)dataSheet.getRow(counter).getCell(priorityColumn).getNumericCellValue()).addIncident(
                        (int)dataSheet.getRow(counter).getCell(priorityColumn).getNumericCellValue(),
                        dataSheet.getRow(counter).getCell(createdOnColumn).getDateCellValue(),
                        (int)dataSheet.getRow(counter).getCell(resolutionTimeColumn).getNumericCellValue()
                );
                System.out.println((int)dataSheet.getRow(counter).getCell(priorityColumn).getNumericCellValue() + " " +
                        dataSheet.getRow(counter).getCell(createdOnColumn).getDateCellValue()+ " " +
                        (int)dataSheet.getRow(counter).getCell(resolutionTimeColumn).getNumericCellValue());
            } else{
                Calendar cal,cal1 = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
                long resolutionTime = (dataSheet.getRow(counter).getCell(resolvedOnColumn).getDateCellValue().getTime() - dataSheet.getRow(counter).getCell(createdOnColumn).getDateCellValue().getTime()) / 1000;

                this.incidentsByPriority.get((int)dataSheet.getRow(counter).getCell(priorityColumn).getNumericCellValue()).addIncident(
                        (int)dataSheet.getRow(counter).getCell(priorityColumn).getNumericCellValue(),
                        dataSheet.getRow(counter).getCell(createdOnColumn).getDateCellValue(),
                        (int)resolutionTime
                );

                System.out.println((int)dataSheet.getRow(counter).getCell(priorityColumn).getNumericCellValue() + " " +
                        dataSheet.getRow(counter).getCell(createdOnColumn).getDateCellValue()+ " " +
                        (int)resolutionTime);
            }
            counter++;
        }

    }
    public void getResolutionsTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date limitValue = new Date();
        try {
            limitValue = sdf.parse("01/05/2019");
        } catch(ParseException e){
            e.printStackTrace();
        }
        System.out.println("===== FY: 2020 ==== ");
        for(HashMap.Entry<Integer,CalculatedIncidents> calc : this.incidentsByPriority.entrySet()){
            System.out.println("Priority: " + calc.getKey() + " AvgResolutionTime in Sec: " + calc.getValue().calculateAverageResolutionTimeInSec(limitValue,false) +" \t\t AvgRes in HRS:MM " + calc.getValue().calculateAverageResolutionTimeInSec(limitValue,false)/(60*60) + ":" + (calc.getValue().calculateAverageResolutionTimeInSec(limitValue,false)/(60)%60));
        }
        System.out.println("===== FY: 2019 ==== ");
        for(HashMap.Entry<Integer,CalculatedIncidents> calc : this.incidentsByPriority.entrySet()){
            System.out.println("Priority: " + calc.getKey() + " AvgResolutionTime in Sec: " + calc.getValue().calculateAverageResolutionTimeInSec(limitValue,true) +" \t\t AvgRes in HRS:MM " + calc.getValue().calculateAverageResolutionTimeInSec(limitValue,true)/(60*60) + ":" + (calc.getValue().calculateAverageResolutionTimeInSec(limitValue,true)/(60)%60));
        }
    }
}
