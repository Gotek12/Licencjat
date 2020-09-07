package pl.wladyga.features.raports.natychmiastowy;

import lombok.AllArgsConstructor;
import pl.wladyga.Info;
import pl.wladyga.features.ProcessDelta;
import pl.wladyga.features.basicInfo.ReadProcess;
import pl.wladyga.features.raports.BaseRaport;
import pl.wladyga.features.raports.Raport;

import java.util.logging.Level;
import java.util.logging.Logger;

@AllArgsConstructor
public class NatychmiastowyRaport extends BaseRaport {

    private Raport raport;

    private final static Logger LOGGER = Logger.getLogger(NatychmiastowyRaport.class.getName());

    @Override
    public void dataToSave(){
        super.dataToSave();

        if(Info.diffProc){
            save("\nRóżnica w procesach to: " + Info.diffamount + "\n");
            Info.diffProc = false;
            LOGGER.log(Level.INFO, "Create natychmiastowy diff processes: ");
        }

        if(Info.checked && !Info.diffProc){
            save("\nNa podstawie analizy zdjec\n");
        }

        if(this.getImagesListName().size() != 0){
            save("\n\nZdjecia dolaczone do raportu:\n");
            for(String img: this.getImagesListName()){
                save(img.split("/")[2] + "\n");
            }
        }

        LOGGER.log(Level.INFO, "Create natychmiastowy raport o id: " + this.id);
    }

}
