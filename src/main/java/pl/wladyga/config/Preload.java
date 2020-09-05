package pl.wladyga.config;

public class Preload {
    public static void load(){
        LoadConfig.getInstance();
        InitDir.getInstance();
    }
}
