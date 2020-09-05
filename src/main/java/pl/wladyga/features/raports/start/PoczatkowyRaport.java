package pl.wladyga.features.raports.start;

import lombok.AllArgsConstructor;
import pl.wladyga.features.basicInfo.ReadProcess;
import pl.wladyga.features.raports.BaseRaport;
import pl.wladyga.features.raports.Raport;

import java.util.logging.Level;
import java.util.logging.Logger;

@AllArgsConstructor
public class PoczatkowyRaport extends BaseRaport {

    private final static Logger LOGGER = Logger.getLogger(PoczatkowyRaport.class.getName());

    Raport raport;

    private String readProcesses(){
        return ReadProcess.of().readProcess();
    }

    @Override
    public void dataToSave(){
        super.dataToSave();
        this.save("\n\nProesy:\n", this.readProcesses());
        LOGGER.log(Level.INFO, "Create start raport");
    }
}
