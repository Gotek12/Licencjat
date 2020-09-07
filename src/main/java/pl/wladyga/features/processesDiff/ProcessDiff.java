package pl.wladyga.features.processesDiff;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.wladyga.Info;
import pl.wladyga.connection.Data;
import pl.wladyga.features.basicInfo.ReadProcess;
import pl.wladyga.features.photo.TakePhoto;
import pl.wladyga.features.raports.natychmiastowy.NatychmiastowyCreator;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class ProcessDiff implements Runnable {

    @NonNull
    public BlockingQueue<Data> data;

    private final static Logger LOGGER = Logger.getLogger(TakePhoto.class.getName());

    private volatile boolean running = true;

    private boolean runAtStart = true;

    private long startProcAmount;

    public void setStop() {
        running = false;
    }

    @Override
    public void run() {
        try {
            while (true && running) {

                if(runAtStart){
                    startProcAmount = ReadProcess.of().countProcesses();
                    System.out.println("Start proc: " + startProcAmount);
                    runAtStart = false;
                }

                long acc = this.accProc();
                long diff = this.processDiffAmount(acc);

                if (Info.lastImageId >= 5 && diff > 0) {
                    System.out.println("Diff: " + diff);
                    Info.diffamount = diff;
                    Info.diffProc = true;
                    NatychmiastowyCreator natychmiastowyCreator = new NatychmiastowyCreator(data, true);
                    natychmiastowyCreator.toCreate();
                    this.startProcAmount = acc;
                }

                //LOGGER.log(Level.INFO, "DIFF");
                Thread.sleep(6000);
            }
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, "Interrupted");
        }
    }

    private long processDiffAmount(Long acc) {
        return Math.abs(acc - this.startProcAmount);
    }

    private long accProc(){
        return ReadProcess.of().countProcesses();
    }
}
