package pl.wladyga.features.raports;

import pl.wladyga.features.basicInfo.DateTimeInfo;
import pl.wladyga.features.raports.components.StartingData;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseRaport extends Raport{

    private final static Logger LOGGER = Logger.getLogger(BaseRaport.class.getName());
    private final StartingData st = StartingData.getInstance();
    private final DateTimeInfo dti = new DateTimeInfo();

    @Override
    public void dataToSave(){
        this.save(st.getBasicComputerData());
        this.save("Czas: ", dti.getFull());
        LOGGER.log(Level.INFO, "Create file with basic data");
    }
}
