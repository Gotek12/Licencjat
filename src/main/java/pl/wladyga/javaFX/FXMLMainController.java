package pl.wladyga.javaFX;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import lombok.SneakyThrows;
import pl.wladyga.connectionTest.Connection;
import pl.wladyga.connectionTest.Data;
import pl.wladyga.connectionTest.Test;
import pl.wladyga.config.Config;
import pl.wladyga.config.LoadConfig;
import pl.wladyga.features.photo.TakePhoto;
import pl.wladyga.features.processesDiff.ProcessDiff;
import pl.wladyga.features.raports.end.KoncowyCreator;
import pl.wladyga.features.raports.okresowy.OkresowyCreator;
import pl.wladyga.features.raports.start.PoczatkowyCreator;
import pl.wladyga.javaFX.alerts.StartAlert;
import pl.wladyga.javaFX.alerts.StopAlert;
import pl.wladyga.javaFX.components.ButtonTypes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLMainController implements Initializable {

    private final static Logger LOGGER = Logger.getLogger(LoadConfig.class.getName());

    @FXML
    private Text welcomeText;

    @FXML
    private Text infoText;

    @FXML
    private Button startBtn;

    @FXML
    private Button stopBtn;

    private TakePhoto photoThread;

    private final String basicText = "Rozpocznij zdawanie testu";

    private Config config;

    private OkresowyCreator okresowyCreator;

    private ProcessDiff processDiff;

    public BlockingQueue<Data> data = new LinkedBlockingDeque<>();

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        stopBtn.setVisible(false);
        welcomeText.setText(basicText);
        infoText.setText("Proszę wyłączyć programy/strony aby uniknąć udostępnienia wrażliwych danych");

        config = LoadConfig.getInstance().getConfig();
    }

    @SneakyThrows
    @FXML
    public void startApp() throws IOException {

        Connection con = new Connection(data);
        new Thread(con).start();

        Alert alert = new StartAlert().showAlert();
        if (alert.showAndWait().get() == ButtonTypes.YES) {
            welcomeText.setText("Jesteś pod obserwacją");
            startBtn.setVisible(false);
            stopBtn.setVisible(true);

            //start photos
            photoThread = new TakePhoto(data);
            new Thread(photoThread).start();

            //generate start raport
            new PoczatkowyCreator(data).create();

            //start okresowy
            okresowyCreator = new OkresowyCreator(data);
            new Thread(okresowyCreator).start();

            //start diff processes
            processDiff = new ProcessDiff(data);
            new Thread(processDiff).start();
        }
    }

    @FXML
    public void stopApp() throws InterruptedException {

        Alert alert = new StopAlert().showAlert();
        if (alert.showAndWait().get() == ButtonTypes.YES) {
            welcomeText.setText(basicText);
            stopBtn.setVisible(false);
            startBtn.setVisible(true);

            //stop okresowyCreator
            okresowyCreator.setStop();

            //stop photoTaker
            photoThread.setStop();

            //stop process diff
            processDiff.setStop();

            LOGGER.log(Level.INFO, "End photoTaker");

            //generate koncowy raport
            new KoncowyCreator(data).create();
        }
    }
}
