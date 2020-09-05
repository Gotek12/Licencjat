package pl.wladyga.features.basicInfo;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeInfo {
    public final String startingDate;

    public DateTimeInfo() {
        startingDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public String getFull(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    public String getDate(){
        return DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDateTime.now());
    }

    public String getTime(){
        return DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now());
    }

    public LocalDateTime bareDate(){
        return LocalDateTime.now();
    }

}
