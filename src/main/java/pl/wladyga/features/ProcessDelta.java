package pl.wladyga.features;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import pl.wladyga.features.raports.components.StartingData;

@RequiredArgsConstructor
public class ProcessDelta {

    @NonNull
    private String accProcess;

    private String startProcess = StartingData.getInstance().getStratProcess();

    private long startAmount = StartingData.getInstance().getProcessAmount();

    private long startAmountSpecjal = StartingData.getInstance().getProcessAmount();

    public long diffProc(long acc){
        return Math.abs(acc - startAmount);
    }

    public long diffProcSpecial(long acc){
        var tmp = this.startAmountSpecjal;
        return Math.abs(acc - tmp);
    }

    public String delta(){
        return StringUtils.difference(startProcess, accProcess);
    }
}
