package pl.wladyga.config.classes;


import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Server {
    private String url;
    private Integer port;
}
