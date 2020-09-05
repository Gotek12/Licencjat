package pl.wladyga.features.processesDiff;

import pl.wladyga.Info;
import pl.wladyga.features.ProcessDelta;
import pl.wladyga.features.basicInfo.ReadProcess;
import pl.wladyga.features.photo.TakePhoto;
import pl.wladyga.features.raports.natychmiastowy.NatychmiastowyCreator;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessDiff implements Runnable {

    private final static Logger LOGGER = Logger.getLogger(TakePhoto.class.getName());

    private volatile boolean running = true;


    public ProcessDiff() {
    }

    public void setStop() {
        running = false;
    }

    @Override
    public void run() {
        try {
            while (true && running) {

                long diff = this.processDiffAmount();
                System.out.println(diff);
                if (Info.lastImageId >= 5 && diff > 0) {
                    Info.diffamount = diff;
                    Info.diffProc = true;
//                    NatychmiastowyCreator natychmiastowyCreator = new NatychmiastowyCreator();
//                    natychmiastowyCreator.toCreate();
                }

                //LOGGER.log(Level.INFO, "DIFF");
                System.out.println("diff");
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, "Interrupted");
        }
    }

    private long processDiffAmount() {
        return new ProcessDelta(ReadProcess.of().readProcess()).diffProcSpecial(ReadProcess.of().countProcesses());
    }
}
