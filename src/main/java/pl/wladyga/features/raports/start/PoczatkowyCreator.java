package pl.wladyga.features.raports.start;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import pl.wladyga.connection.Data;
import pl.wladyga.config.Config;
import pl.wladyga.config.LoadConfig;
import pl.wladyga.features.raports.BaseRaport;
import pl.wladyga.features.raports.Raport;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class PoczatkowyCreator {

    @NonNull
    public BlockingQueue<Data> data;

    private Config config;
    private final static Logger LOGGER = Logger.getLogger(PoczatkowyCreator.class.getName());

    @SneakyThrows
    public void create() throws IOException {
        config = LoadConfig.getInstance().getConfig();

        Raport startRaport = new PoczatkowyRaport(new BaseRaport());
        startRaport.setId(-1);
        startRaport.setName(
                config.getRaports().getPoczatkowy().getPrefix()
        );
        startRaport.dataToSave();

        var split = startRaport.getName().split("/");
        List<String> list = Arrays.asList(split[1]);
        Data d = new Data(list, split[0]);
        data.put(d);


        LOGGER.log(Level.INFO, "Send początkowy");
    }
}
