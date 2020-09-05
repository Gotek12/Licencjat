package pl.wladyga.features.raports.components;

import lombok.Getter;
import pl.wladyga.features.basicInfo.ComputerInfo;
import pl.wladyga.features.basicInfo.DateTimeInfo;
import pl.wladyga.features.basicInfo.ReadProcess;

import java.time.LocalDateTime;

public class StartingData {

    @Getter
    private final String basicComputerData;

    @Getter
    private final String startTime;

    @Getter
    private final String stratProcess;

    @Getter
    private final LocalDateTime startLocalTime;

    @Getter
    private final Long processAmount;

    private StartingData(){
        this.basicComputerData = ComputerInfo.getInstance().toString();
        this.startTime = new DateTimeInfo().getFull();
        this.stratProcess = ReadProcess.of().readProcess();
        this.startLocalTime = new DateTimeInfo().bareDate();
        this.processAmount = ReadProcess.of().countProcesses();
        System.out.println(this.processAmount);
    }

    private static class StartingDataHelper{
        private static final StartingData INSTANCE = new StartingData();
    }

    public static StartingData getInstance(){
        return StartingDataHelper.INSTANCE;
    }

}
