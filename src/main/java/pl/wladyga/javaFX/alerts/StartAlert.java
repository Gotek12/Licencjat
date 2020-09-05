package pl.wladyga.javaFX.alerts;

import javafx.scene.control.Alert;
import lombok.NoArgsConstructor;
import pl.wladyga.javaFX.components.ButtonTypes;

@NoArgsConstructor
public class StartAlert {
    public Alert showAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonTypes.YES, ButtonTypes.NO);
        alert.setTitle("Uwaga!");
        alert.setHeaderText("Uwaga!!");
        alert.setContentText("Czy chcesz rozpocząć test");
        alert.getDialogPane().setPrefSize(200, 100);

        return alert;
    }
}
