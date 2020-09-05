package pl.wladyga.config;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InitDir {
    private final static Logger LOGGER = Logger.getLogger(LoadConfig.class.getName());

    private static final InitDir instance = new InitDir();

    private Config config;

    private InitDir(){
        config = LoadConfig.getInstance().getConfig();
        createDirectories();

        LOGGER.log(Level.INFO, "Create directories");
    }

    public static InitDir getInstance(){
        return instance;
    }

    private void createDirectories(){
        System.out.println("Set directories");

        String current = null;
        try {
            current = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //for images
        String imagePath = config.getImageTaker().getPath();

        if (!new File(current + "/" + imagePath).exists()) {
            new File( imagePath).mkdir();
        }

        //for raports
        String raportsPath = config.getRaports().getPath();
        if (!new File(current + "/" + raportsPath).exists()) {
            new File( raportsPath).mkdir();
        }

        String []allReportsDir = {
                config.getRaports().getOkresowy().getFolderName(),
                config.getRaports().getNatychmiastowy().getFolderName()
        };

        for (String s : allReportsDir) {
            if (!new File(raportsPath + "/" + s).exists()) {
                new File(raportsPath + "/" + s).mkdir();
            }
        }
    }
}
