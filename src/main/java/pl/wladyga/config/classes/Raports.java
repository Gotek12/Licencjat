package pl.wladyga.config.classes;

import lombok.Data;
import lombok.ToString;
import pl.wladyga.config.classes.raports.Koncowy;
import pl.wladyga.config.classes.raports.Natychmiastowy;
import pl.wladyga.config.classes.raports.Okresowy;
import pl.wladyga.config.classes.raports.Poczatkowy;


@Data
@ToString
public class Raports {
    private String path;
    private Okresowy okresowy;
    private Natychmiastowy natychmiastowy;
    private Poczatkowy poczatkowy;
    private Koncowy koncowy;
}
