package pl.wladyga.features.raports.natychmiastowy;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import pl.wladyga.Info;
import pl.wladyga.config.Config;
import pl.wladyga.config.LoadConfig;
import pl.wladyga.connection.Data;
import pl.wladyga.features.photo.comparison.ComparisonImages;
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
public class NatychmiastowyCreator {

    @NonNull
    public BlockingQueue<Data> data;

    private long tmp = 0L;
    private long tmp2 = 0L;

    private boolean startDiffProc = false;

    private final static Logger LOGGER = Logger.getLogger(NatychmiastowyCreator.class.getName());

    private final List<BufferedImage> buffImages = new ArrayList<>();
    private final List<String> names = new ArrayList<>();
    private long id;
    Raport natychmiastowy;

    private Config config = LoadConfig.getInstance().getConfig();

    public NatychmiastowyCreator(BlockingQueue<Data> data, boolean startDiffProc) {
        this.data = data;
        this.startDiffProc = startDiffProc;
    }

    public void toCreate() {
        this.id = Info.natychmiastoweId;

        this.loadImages();

        double properPercentage = config.getImageAnalyse().getPercentage();
        double result = ComparisonImages.analiseImagesByColor(buffImages.get(0), buffImages.get(3)); // porownuje 2 srodkowe
        double result2 = ComparisonImages.analiseImagesByColor(buffImages.get(1), buffImages.get(2)); // 2 zewnętrzne
        double middle = (double) (result + result2) / 2.0;


        if (middle >= properPercentage && !startDiffProc) {
            if (Info.checked && !Info.nDiffLast) {

                tmp = Info.lastImageId;
                Info.nObId = Info.nObId++;
                Info.nObLast = true;

                composeRaport();
                Info.checked = false;
            } else {
                if (Info.lastImageId - tmp >= 8) {
                    System.out.println("I can but wait");
                    Info.checked = true;
                }

                if (Info.lastImageId - tmp2 >= 10) {
                    Info.nDiffLast = false;
                }
                Info.nObLast = false;
            }

        }else if (startDiffProc) {
            if (Info.checked2 && !Info.nObLast) {

                tmp2 = Info.lastImageId;
                Info.nDiffId++;
                Info.nDiffLast = true;

                composeRaport();
                Info.checked2 = false;
            } else {
                if (Info.lastImageId - tmp2 >= 10) {
                    Info.checked2 = true;
                }

                if (Info.lastImageId - tmp >= 8) {
                    Info.nObLast = false;
                }

                Info.nDiffLast = false;
            }
        }


        buffImages.clear();
        names.clear();
    }

    @SneakyThrows
    public void composeRaport() {

        Raport natychmiastowy = new NatychmiastowyRaport(new BaseRaport());
        natychmiastowy.setId(Info.natychmiastoweId);
        natychmiastowy.setName(
                config.getRaports().getNatychmiastowy().getFolderName() + "/" + config.getRaports().getNatychmiastowy().getPrefix()
        );

        this.prepareImages();
        natychmiastowy.setImagesListName(names);
        natychmiastowy.dataToSave();

        Info.natychmiastoweId++;
        buffImages.clear();

        //sending
        List<String> tmp = new ArrayList<>();
        for (String name : names) {
            tmp.add(name.split("/")[2]);
        }
        var split = natychmiastowy.getName().split("/");
        tmp.add(split[2]);
        Data d = new Data(tmp, split[0] + "/" + split[1]);
        data.put(d);
        names.clear();

        LOGGER.log(Level.INFO, "Create natychmiastowy with id: " + (this.id - 1));
        LOGGER.log(Level.INFO, "Send natychmiastowy o id: " + (this.id - 1));
    }

    public void loadImages() {
        for (int i = 1; i <= 4; i++) {
            try {
                BufferedImage tmp = ImageIO.read(new File(
                        config.getImageTaker().getPath() + "/" + (Info.lastImageId - 4 + i) + ".jpg")
                );
                buffImages.add(tmp);

                // names files
                names.add(
                        config.getRaports().getPath() + "/" + config.getRaports().getNatychmiastowy().getFolderName()
                                + "/" + config.getRaports().getNatychmiastowy().getPrefix() + "_"
                                + id + "_" + i + ".jpg"
                );

            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Error to read images");
            }
        }
    }

    public void prepareImages() {
        for (int i = 0; i < 4; i++) {
            try {
                ImageIO.write(buffImages.get(i), "jpg", new File(names.get(i)));
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Error create image for natychmiastowy\n" + e.getMessage());
            }
        }
    }
}
