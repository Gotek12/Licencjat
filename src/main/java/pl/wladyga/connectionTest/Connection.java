package pl.wladyga.connectionTest;

import lombok.SneakyThrows;
import pl.wladyga.config.Config;
import pl.wladyga.config.LoadConfig;
import pl.wladyga.features.basicInfo.ComputerInfo;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

public class Connection implements Runnable {

    private Socket socket;
    private Config config;
    protected BlockingQueue<Data> queue;
    private BufferedOutputStream bos;
    private DataOutputStream dos;

    @SneakyThrows
    public Connection(BlockingQueue<Data> queue) {
        this.config = LoadConfig.getInstance().getConfig();

        socket = new Socket(
                config.getServer().getUrl(),
                config.getServer().getPort()
        );
        this.queue = queue;
        bos = new BufferedOutputStream(socket.getOutputStream());
    }

    @SneakyThrows
    public void run() {
        dos = new DataOutputStream(bos);

        while (true) {

            if (queue.isEmpty()) {
                continue;
            } else {
                this.send();
            }

        }
    }

    @SneakyThrows
    void send() {
        Data loadData = queue.take();

        List<File> filesList = loadData.getList().stream().map(e -> new File(loadData.getPath() + "/" + e)).collect(Collectors.toList());
        dos.writeUTF(ComputerInfo.userName());
        dos.writeInt(filesList.size());

        for (File f : filesList) {
            long length = f.length();
            dos.writeLong(length);

            String name = f.getName();
            dos.writeUTF(name);

            FileInputStream fis = new FileInputStream(f);
            BufferedInputStream bis = new BufferedInputStream(fis);

            int theByte = 0;
            while ((theByte = bis.read()) != -1) bos.write(theByte);
            //dos.writeUTF("\n");

            bis.close();
            fis.close();

        }
        dos.flush();

        socket.setKeepAlive(true);
    }
}
