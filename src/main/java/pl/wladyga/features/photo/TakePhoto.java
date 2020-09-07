package pl.wladyga.features.photo;

import pl.wladyga.Info;
import pl.wladyga.config.Config;
import pl.wladyga.config.LoadConfig;
import pl.wladyga.connectionTest.Data;
import pl.wladyga.features.raports.natychmiastowy.NatychmiastowyCreator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TakePhoto implements Runnable {

    public BlockingQueue<Data> data;

    private final static Logger LOGGER = Logger.getLogger(TakePhoto.class.getName());

    private long id = 1;

    private volatile boolean running = true;

    private final String path;

    private final int duration;

    private final Config config;


    public TakePhoto(BlockingQueue<Data> data) {
        this.data = data;
        this.config = LoadConfig.getInstance().getConfig();
        this.duration = this.config.getImageTaker().getDuration();
        this.path = this.config.getImageTaker().getPath();
    }

    public void setStop() {
        running = false;
    }

    @Override
    public void run() {
        try {
            while (true && running) {

                try {
                    Robot robot = new Robot();
                    String format = "jpg";
                    String fileName = id + "." + format;

                    Info.lastImageId = id;
                    id++;

                    Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                    BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
                    ImageIO.write(screenFullImage, format, new File(path + "/" + fileName));

                    LOGGER.log(Level.INFO, "Screenshot with id: " + (id - 1) + " saved");
                } catch (AWTException | IOException ex) {
                    System.err.println(ex);
                }

                // natychmiastowy
                if (this.id >= 5) {
                    NatychmiastowyCreator natychmiastowyCreator = new NatychmiastowyCreator(data);
                    natychmiastowyCreator.toCreate();
                }

                Thread.sleep(this.duration * 1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }
}
