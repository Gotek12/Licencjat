package pl.wladyga.features.raports.end;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import pl.wladyga.config.Config;
import pl.wladyga.config.LoadConfig;
import pl.wladyga.connectionTest.Data;
import pl.wladyga.features.raports.BaseRaport;
import pl.wladyga.features.raports.Raport;
import pl.wladyga.features.raports.start.PoczatkowyCreator;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class KoncowyCreator {

    @NonNull
    public BlockingQueue<Data> data;

    private Config config;
    private final static Logger LOGGER = Logger.getLogger(PoczatkowyCreator.class.getName());

    @SneakyThrows
    public void create() {
        config = LoadConfig.getInstance().getConfig();

        Raport stopRaport = new KoncowyRaport(new BaseRaport());
        stopRaport.setId(-1);
        stopRaport.setName(
                config.getRaports().getKoncowy().getPrefix()
        );
        stopRaport.dataToSave();

        var split = stopRaport.getName().split("/");
        List<String> list = Arrays.asList(split[1]);
        Data d = new Data(list, split[0]);
        data.put(d);

        LOGGER.log(Level.INFO, "Send koncowy");
    }
}
