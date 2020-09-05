package pl.wladyga.config.classes.raports;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Okresowy {
    private Integer duration;
    private String folderName;
    private String prefix;
    private Integer numOfImages;
}
