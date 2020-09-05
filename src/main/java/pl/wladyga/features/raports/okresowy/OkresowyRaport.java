package pl.wladyga.features.raports.okresowy;

import lombok.AllArgsConstructor;
import pl.wladyga.features.ProcessDelta;
import pl.wladyga.features.basicInfo.ReadProcess;
import pl.wladyga.features.raports.BaseRaport;
import pl.wladyga.features.raports.Raport;

import java.util.logging.Level;
import java.util.logging.Logger;

@AllArgsConstructor
public class OkresowyRaport extends BaseRaport {

    private Raport raport;

    private final static Logger LOGGER = Logger.getLogger(OkresowyRaport.class.getName());

    private String deltaProc() {
        return new ProcessDelta(ReadProcess.of().readProcess()).delta();
    }

    @Override
    public void dataToSave(){
        super.dataToSave();

        if(this.getImagesListName().size() != 0){
            save("\n\nZdjecia dolaczone do raportu:\n");
            for(String img: this.getImagesListName()){
                save(img.split("/")[2] + "\n");
            }
        }

        if(this.deltaProc().length() != 0){
            this.save("\n\nDelta procesów:\n", this.deltaProc());
        }

        LOGGER.log(Level.INFO, "Create okresowy raport o id: " + this.id);
    }

}
