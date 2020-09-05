package pl.wladyga.features.raports.end;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.wladyga.features.basicInfo.DateTimeInfo;
import pl.wladyga.features.raports.BaseRaport;
import pl.wladyga.features.raports.Raport;
import pl.wladyga.features.raports.components.StartingData;
import pl.wladyga.features.raports.start.PoczatkowyRaport;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class KoncowyRaport extends BaseRaport {

    private final static Logger LOGGER = Logger.getLogger(PoczatkowyRaport.class.getName());

    @NonNull
    Raport raport;

    private LocalDateTime endBareDate;

    private String readEndDate(){
        endBareDate = new DateTimeInfo().bareDate();
        return new DateTimeInfo().getFull();
    }

    private String durationOfTest(){
        LocalDateTime start = StartingData.getInstance().getStartLocalTime();
        LocalDateTime end = endBareDate;
        LocalDateTime tempDateTime = LocalDateTime.from( start );

        long hours = tempDateTime.until( end, ChronoUnit.HOURS );
        tempDateTime = tempDateTime.plusHours( hours );

        long minutes = tempDateTime.until( end, ChronoUnit.MINUTES );
        tempDateTime = tempDateTime.plusMinutes( minutes );

        long seconds = tempDateTime.until( end, ChronoUnit.SECONDS );

        StringBuilder returnTime = new StringBuilder();
        returnTime.append(hours + " h ");
        returnTime.append(minutes + " m ");
        returnTime.append(seconds + " s ");
        return returnTime.toString();
    }

    @Override
    public void dataToSave(){
        super.dataToSave();
        save("\nCzas końcowy: ", readEndDate());
        save("\nLaczny czas zdawania", durationOfTest());
        LOGGER.log(Level.INFO, "Create stop raport");
    }

}
