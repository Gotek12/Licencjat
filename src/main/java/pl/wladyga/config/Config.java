package pl.wladyga.config;

import lombok.Data;
import lombok.ToString;
import pl.wladyga.config.classes.*;

@Data
@ToString
public class Config {
    private String confName;
    private Server server;
    private ImageTaker imageTaker;
    private Raports raports;
    private ClearData clearData;
    private ImageAnalyse imageAnalyse;
}
