package pl.wladyga.connectionToServer;

import lombok.SneakyThrows;
import pl.wladyga.config.Config;
import pl.wladyga.config.LoadConfig;
import pl.wladyga.features.basicInfo.ComputerInfo;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class Connection {

    private String path;
    private List<String> files;
    private Socket socket;
    private Config config;

    @SneakyThrows
    public Connection(String path, List<String> files){
        this.config = LoadConfig.getInstance().getConfig();;
        this.path = path;
        this.files = files;
        socket = new Socket(
                config.getServer().getUrl(),
                config.getServer().getPort()
        );
    }

    public void send() {

        BufferedOutputStream bos = null;
        DataOutputStream dos = null;
        try {

            bos = new BufferedOutputStream(socket.getOutputStream());
            dos = new DataOutputStream(bos);

            List<File> filesList = files.stream().map(e -> new File(path + "/"+ e)).collect(Collectors.toList());
            dos.writeUTF(ComputerInfo.userName());
            dos.writeInt(filesList.size());

            for(File f: filesList){
                long length = f.length();
                dos.writeLong(length);

                String name = f.getName();
                dos.writeUTF(name);

                FileInputStream fis = new FileInputStream(f);
                BufferedInputStream bis = new BufferedInputStream(fis);

                int theByte = 0;
                while((theByte = bis.read()) != -1) bos.write(theByte);

                bis.close();
            }
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
