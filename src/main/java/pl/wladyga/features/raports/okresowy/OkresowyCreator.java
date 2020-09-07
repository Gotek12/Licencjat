package pl.wladyga.features.raports.okresowy;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.wladyga.Info;
import pl.wladyga.config.Config;
import pl.wladyga.config.LoadConfig;
import pl.wladyga.connection.Data;
import pl.wladyga.features.raports.BaseRaport;
import pl.wladyga.features.raports.Raport;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class OkresowyCreator implements Runnable {

    private final static Logger LOGGER = Logger.getLogger(OkresowyRaport.class.getName());

    private final List<String> names = new ArrayList<>();

    private boolean stopForStart = true;

    private volatile boolean running = true;

    private long id;

    private Config config = LoadConfig.getInstance().getConfig();

    private long numImg;

    @NonNull
    public BlockingQueue<Data> data;


    public void setStop() {
        running = false;
    }

    @Override
    public void run() {
        if (stopForStart) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, "stopForStart error - " + e.getMessage());
            }
            stopForStart = false;
        }

        try {
            while (true && running) {
                this.numImg = this.numImg >= Info.lastImageId - 1 ? 1 : config.getRaports().getOkresowy().getNumOfImages();
                this.loadImages();

                //create okresowy raport
                Raport okresowyRaport = new OkresowyRaport(new BaseRaport());
                okresowyRaport.setId(Info.okresoweId);
                okresowyRaport.setName(
                        config.getRaports().getOkresowy().getFolderName() + "/" + config.getRaports().getOkresowy().getPrefix()
                );

                okresowyRaport.setImagesListName(names);

                okresowyRaport.dataToSave();
                this.id = Info.okresoweId;
                Info.okresoweId++;

                List<String> tmp = new ArrayList<>();
                for (String name : names) {
                    tmp.add(name.split("/")[2]);
                }
                var split = okresowyRaport.getName().split("/");
                tmp.add(split[2]);

                Data d = new Data(tmp, split[0] + "/" + split[1]);
                data.add(d);
                LOGGER.log(Level.INFO, "Send okresowy");

                names.clear();
                Thread.sleep((config.getRaports().getOkresowy().getDuration()) * 1000);

            }
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, "Interrupted - " + e.getMessage());
        }
    }

    public void loadImages() {
        for (int i = 1; i <= this.numImg; i++) {
            try {
                //save image
                BufferedImage tmp = ImageIO.read(new File(
                        config.getImageTaker().getPath() + "/" + (Info.lastImageId - this.numImg + i) + ".jpg")
                );

                String nameTmp = config.getRaports().getPath() + "/" + config.getRaports().getOkresowy().getFolderName()
                        + "/" + config.getRaports().getOkresowy().getPrefix() + "_"
                        + Info.okresoweId + "_" + i + ".jpg";

                // names files
                names.add(
                        nameTmp
                );
                ImageIO.write(tmp, "jpg", new File(nameTmp));

            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Error to read images");
            }
        }
    }

}
