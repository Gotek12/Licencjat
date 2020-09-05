package pl.wladyga.features.basicInfo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ComputerInfo {

    private Map<String, String> userInfo;

    private static class BasicInfoHelper{
        private static final ComputerInfo INSTANCE = new ComputerInfo();
    }

    public static ComputerInfo getInstance(){
        return BasicInfoHelper.INSTANCE;
    }

    public static String userName(){
        return System.getProperty("user.name");
    }

    public Map<String, String> getSysInfo() throws UnknownHostException {
        userInfo = new Hashtable<>();
        userInfo.put("ComputerName", InetAddress.getLocalHost().getHostName());
        userInfo.put("UserName", System.getProperty("user.name"));
        userInfo.put("os", detectOs());
        return userInfo;
    }

    private String detectOs(){
        String OS = System.getProperty("os.name").toLowerCase();
        if(OS.contains("win")){
            return "Windows";
        }
        if(OS.contains("mac")){
            return "OSX";
        }

        if(OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0 ){
            return "Linux";
        }

        return "Error";
    }

    @Override
    public String toString() {
        try {
            this.getSysInfo();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        StringBuilder dataStr = new StringBuilder();
        for(Map.Entry<String, String> data : userInfo.entrySet()){
            dataStr.append(data.getKey() + ": " + data.getValue() + "\n");
        }
        return dataStr.toString();
    }
}
