package pl.wladyga.connectionTest;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Data {
    private List<String> list;
    private String path;
}
