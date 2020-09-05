package pl.wladyga.features.raports;


import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Raport {

    private final static Logger LOGGER = Logger.getLogger(Raport.class.getName());

    @Setter
    @Getter
    protected long id = 1L;

    @Setter
    @Getter
    private String prefixName;

    @Setter
    private String fileName;

    @Getter
    @Setter
    private String filePath;

    @Setter
    @Getter
    private List<String> imagesListName;

    public abstract void dataToSave();

    protected String setTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH_mm_ss");
        return dateFormat.format(new Date());
    }

    public String getName(){
        return fileName;
    }

    public void setName(String prefix){
        this.setTime();
        String tmp = id == -1 ? (prefix) : ((id < 10) ? (prefix + "_0" + id) : (prefix + "_" + id));
        this.fileName =  "raports/" + tmp + "_" + this.setTime() + ".txt";
    }

    private void createFile(){
        if(!Files.exists(Paths.get(this.fileName))){
            try {
                File file = new File(this.fileName);
                file.createNewFile();
                LOGGER.log(Level.INFO, "Create empty file: " + this.fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //add bollean inform that save all ok
    protected void save(String key){
        createFile();
        try {
            Files.writeString(Paths.get(this.fileName), key, StandardOpenOption.APPEND);
            LOGGER.log(Level.INFO, "Add data to file");
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Add data to file\n" + e.getMessage());
        }
    }

    protected void save(String key, String value){
        createFile();
        try {
            Files.writeString(Paths.get(this.fileName), key + " " + value, StandardOpenOption.APPEND);
            LOGGER.log(Level.INFO, "Add data to file with key");
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Add data to file with key\n" + e.getMessage());
        }
    }

}