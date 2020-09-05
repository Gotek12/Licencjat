package pl.wladyga.config;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

@ToString
public class LoadConfig {
    private final String resourceName = "config.yml";

    private final static Logger LOGGER = Logger.getLogger(LoadConfig.class.getName());

    private static final LoadConfig instance = new LoadConfig();

    private Config config;

    public Config getConfig(){
        return config;
    }

    @SneakyThrows
    private LoadConfig(){
        Yaml yaml = new Yaml(new Constructor(Config.class));
        InputStream inputStream;
        if (!new File(resourceName).exists()) {
            inputStream = this.getClass()
                    .getClassLoader()
                    .getResourceAsStream("config.yml");

            LOGGER.log(Level.INFO, "Load config from - config.yml");
        }else{
            inputStream = new FileInputStream(resourceName);
            LOGGER.log(Level.INFO, "Load custom config - config.yml");
        }

        config = (Config) yaml.load(inputStream);
    }

    public static LoadConfig getInstance(){
        return instance;
    }

}
