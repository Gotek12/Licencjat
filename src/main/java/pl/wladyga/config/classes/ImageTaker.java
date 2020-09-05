package pl.wladyga.config.classes;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ImageTaker {
    private Integer duration;
    private String path;
}
